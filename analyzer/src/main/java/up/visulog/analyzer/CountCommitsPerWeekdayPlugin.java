package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerWeekdayPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerWeekdayPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            String week_day = commit.date.substring(0, 3); // Get weekday in date string. Ex: Wed Sep 2 2015 -> Wed
            var nb = result.commitsPerWeekday.getOrDefault(week_day, 0);
            result.commitsPerWeekday.put(week_day, nb + 1);
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

    public static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitsPerWeekday = new HashMap<>();

        public Map<String, Integer> getCommitsPerDate() {
            return commitsPerWeekday;
        }

        @Override
        public String getResultAsString() {
            return commitsPerWeekday.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per weekday: <ul>");
            for (var item : commitsPerWeekday.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
