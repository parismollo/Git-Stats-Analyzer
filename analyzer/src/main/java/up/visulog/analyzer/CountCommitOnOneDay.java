package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitOnOneDay implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitOnOneDay(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            String day = commit.date.substring(0,10) + commit.date.substring(20,24);//Pour avoir le jour le mois et l'année
            var nb = result.commitOnOneDay.getOrDefault(day, 0);
            result.commitOnOneDay.put(day, nb + 1);
        }
        return result;
    }


    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitOnOneDay = new HashMap<>();

        Map<String, Integer> getCommitsPerDate() {
            return commitOnOneDay;
        }

        @Override
        public String getResultAsString() {
            return commitOnOneDay.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits on one day: <ul>");
            for (var item : commitOnOneDay.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }


}