package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsBetweenDays implements AnalyzerPlugin {
    protected final Configuration configuration;
    protected Result result;
    protected String startDate, endDate;
    
    public CountCommitsBetweenDays(Configuration generalConfiguration, String startDate, String endDate) {
        this.configuration = generalConfiguration;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitBetweenDays.getOrDefault(commit.author, 0);
            result.commitBetweenDays.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    public void run() {
    	if(startDate.equals(endDate)) {
    		result = processLog(Commit.parseLogFromCommand(configuration.getGitPath(), startDate));
    	}
    	else {
    		result = processLog(Commit.parseLogFromCommand(configuration.getGitPath(), startDate, endDate));
    	}
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }
    
    public class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitBetweenDays = new HashMap<>();

        public Map<String, Integer> getCommitsPerDays() {
            return commitBetweenDays;
        }

        @Override
        public String getResultAsString() {
            return commitBetweenDays.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits between "+startDate+" and "+endDate+" "+": <ul>");
            for (var item : commitBetweenDays.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }


}