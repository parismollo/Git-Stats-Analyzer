package up.visulog.gui.validators;
import java.util.HashSet;
import java.util.List;

import up.visulog.gitrawdata.Commit;

public class ResultsValidators {
    public static HashSet<String> getAuthors(List<Commit> gitLog) {
        HashSet<String> authors = new HashSet<String>();
        for (var commit : gitLog) {
            var author = commit.author;
            authors.add(author);
        }
        return authors;
    }
}
