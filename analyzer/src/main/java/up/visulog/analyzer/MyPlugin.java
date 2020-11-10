package up.visulog.analyzer;

import java.util.HashSet;
import java.util.List;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

class MyPlugin implements AnalyzerPlugin {
    MyResult result; 
    Configuration configuration;
    
    public MyPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    MyResult countAuthors(List<Commit> log) {
        var result = new MyResult();

        for (var commit : log) {
            result.authorSet.add(commit.author);
        }
        return result;
    }

    @Override
    public void run() {
        result = countAuthors(Commit.parseLogFromCommand(configuration.getGitPath()));
        // TODO Auto-generated method stub

    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class MyResult implements AnalyzerPlugin.Result {
        HashSet<String> authorSet;

        public MyResult() {
            authorSet = new HashSet();
        }
        @Override
        public String getResultAsString() {
            // TODO Auto-generated method stub
            return authorSet.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            String html = "<div><ul>";

            for (var author : authorSet) {
                html += "<li>" + author + "</li>";
            }

            html += "</ul></div>";
            return html;
        }

    }
}
