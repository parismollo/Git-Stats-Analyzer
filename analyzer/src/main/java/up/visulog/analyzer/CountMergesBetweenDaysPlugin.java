package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountMergesBetweenDaysPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;
    protected String startDate, endDate;
    
    public CountMergesBetweenDaysPlugin(Configuration generalConfiguration, String startDate, String endDate) {
        this.configuration = generalConfiguration;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            if (commit.mergedFrom != null) {
                var nb = result.mergesPerAuthor.getOrDefault(commit.author, 0);
                result.mergesPerAuthor.put(commit.author, nb + 1);
            }
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
        private final Map<String, Integer> mergesPerAuthor = new HashMap<>();

        public Map<String, Integer> getmergesPerAuthor() {
            return mergesPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return mergesPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Merges between "+startDate+" and "+endDate+" : <ul>");
            for (var item : mergesPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
}
