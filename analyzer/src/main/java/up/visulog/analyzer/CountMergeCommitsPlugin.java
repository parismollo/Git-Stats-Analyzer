package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.List;


public class CountMergeCommitsPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountMergeCommitsPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        int mergeComitCounter = 0;
        for (var commit : gitLog) {
            if (commit.mergedFrom != null) {
                mergeComitCounter++; // If the information exists, is because there was a merge.
            }
        }
        result.totalMergeComits = mergeComitCounter;
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
        // private final Map<String, Integer> mergeCommits = new HashMap<>();
        private int totalMergeComits = 0;

        public int getTotalMergeCommits() {
            return totalMergeComits;
        }

        @Override
        public String getResultAsString() {
            return String.valueOf(totalMergeComits);
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("Number of merge commits:");
            html.append(this.totalMergeComits);
            return html.toString();
        }
    }
}
