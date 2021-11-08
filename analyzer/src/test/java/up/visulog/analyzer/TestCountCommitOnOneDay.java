package up.visulog.analyzer;

import org.junit.Test;
import org.junit.Assert;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.util.ArrayList;

public class TestCountCommitOnOneDay {
    
    @Test
    public void checkCommitSum() {
        var log = new ArrayList<Commit>();
        String date = ;
        log.add(new CommitBuilder("").setDate().createCommit());
        var res = CountCommitsOnOneDay.processLog(log);
        var sum = res.getCommitsPerDate().values().stream().reduce(0, Integer::sum);
        assertEquals(entries, sum.longValue());
        assertThat()
    }

}
