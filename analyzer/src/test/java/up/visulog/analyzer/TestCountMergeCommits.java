package up.visulog.analyzer;

import org.junit.Test;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCountMergeCommits {
    @Test
    public void checkBranchSum() {
        int expectedResult = 3;
        String[] randomStrings = {"9e74f15 8f836af", "09f9cbd 1329241"};
        var log = new ArrayList<Commit>();
        var entries = 20;
        for (int i = 0; i < entries; i++) {
            // Create in total 3 merges only
            if (i == 1 || i == 2) { 
                log.add(new CommitBuilder("").setMergedFrom(randomStrings[0]).createCommit());
            }
            if (i == 3) {
                log.add(new CommitBuilder("").setMergedFrom(randomStrings[1]).createCommit());
            }
            log.add(new CommitBuilder("").createCommit());
        }
        var res = CountMergeCommitsPlugin.processLog(log);
        var sum = res.getTotalMergeCommits();
        assertEquals(expectedResult, sum);
    }
}
