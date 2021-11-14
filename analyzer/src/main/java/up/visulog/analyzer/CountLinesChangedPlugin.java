package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.LinesChanged;

public class CountLinesChangedPlugin implements AnalyzerPlugin {

    private final Configuration configuration;
    private Result result;

    public CountLinesChangedPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<LinesChanged> gitLog) {
        var result = new Result();
        for (var linesChanged : gitLog) {
        	String path = linesChanged.getPath().getFileName()+"";
            result.linesChanged.put(path, new int[] {linesChanged.getAddedLines(),
            										 linesChanged.getDeletedLines()});
        }
        return result;
    }

    @Override
    public void run() {
        result = processLog(LinesChanged.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    public static class Result implements AnalyzerPlugin.Result {
        private final Map<String, int[]> linesChanged = new HashMap<>();

        public Map<String, int[]> getLinesChangedPerFile() {
            return linesChanged;
        }

        @Override
        public String getResultAsString() {
            return linesChanged.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Lines changed in files: <ul>");
            for (var item : linesChanged.entrySet()) {
            	int[] chars = item.getValue();
                html.append("<li>").append(item.getKey()).append(": <ul>");
                if(chars[0] != 0)
                	html.append("<li>").append(chars[0]).append(" lines added</li>");
                if(chars[1] != 0)
                	html.append("<li>").append(chars[1]).append(" lines deleted</li>");
                html.append("</ul></li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
	
}
