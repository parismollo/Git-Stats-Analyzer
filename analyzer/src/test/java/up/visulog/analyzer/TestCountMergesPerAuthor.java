package up.visulog.analyzer;

import org.junit.Test;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCountMergesPerAuthor {
    @Test
    public void checkMergeSum() {
        var log = new ArrayList<Commit>();
        String mergeRef = "9e74f15 8f836af";
        var entries = 20;
        CommitBuilder c;
        for (int i = 0; i < entries; i++) {
            if(i == 1 || i == 2) {
                c = new CommitBuilder("");
                if (i == 2) {
                    c.setAuthor("Rick");
                } else {
                    c.setAuthor("Morty");
                }
                c.setMergedFrom(mergeRef);
            }else {
                c = new CommitBuilder("");
            }
            log.add(c.createCommit());
        }
        var total = CountMergeCommitsPlugin.processLog(log);
        var res = CountMergePerAuthorPlugin.processLog(log);
        var totalSum = total.getTotalMergeCommits();
        var resSum = res.getmergesPerAuthor().values().stream().reduce(0, Integer::sum);
        assertEquals(totalSum, resSum.longValue());
    }
}
