package up.visulog.analyzer;

import java.util.*;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.*;


public class CountCommitLinesChanged implements AnalyzerPlugin{

    private final Configuration configuration;
    private Result result;

    public CountCommitLinesChanged(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<CommitLinesChanged> gitLog) {
        var result = new Result();
        for (var CommitLinesChanged : gitLog) {
            String[] name_date = {CommitLinesChanged.getAuthor(),CommitLinesChanged.getDate()};
            int[] add_delLines = {countTotalAddLines(CommitLinesChanged),countTotalDelLines(CommitLinesChanged)};
            result.commitchanged.put(name_date,add_delLines);
        }
        return result;
    }
    public static int countTotalAddLines(CommitLinesChanged commitLinesChanged){
        int res = 0;
        for(int i = 0 ;i < commitLinesChanged.getList().size();i++){
            res += commitLinesChanged.getList().get(i).getAddedLines();
        }
        return res;
    }

    public static int countTotalDelLines(CommitLinesChanged commitLinesChanged){
        int res = 0;
        for(int i = 0 ;i < commitLinesChanged.getList().size();i++){
            res += commitLinesChanged.getList().get(i).getDeletedLines();
        }
        return res;
    }
    
    @Override
    public void run() {
        result = processLog(CommitLinesChanged.parseLogFromCommandLinesChanged(configuration.getGitPath()));
        
    }

    @Override
    public Result getResult() {
        if(result == null) run();
        return result;
    }

    public static class Result implements AnalyzerPlugin.Result {
        private final Map<String[], int[]> commitchanged = new HashMap<>();

        public Map<String[], int[]> getLinesChangedPerFile() {
            return commitchanged;
        }

        @Override
        public String getResultAsString() {
            return commitchanged.toString();
        }

        @Override
        public String getResultAsHtmlDiv() { // A Faire 
            StringBuilder html = new StringBuilder("<div>Lines changed in files: <ul>");
            for (var item : commitchanged.entrySet()) {
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
