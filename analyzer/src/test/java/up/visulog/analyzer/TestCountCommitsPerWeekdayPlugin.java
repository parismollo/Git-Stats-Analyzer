package up.visulog.analyzer;

import org.junit.Test;

import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitBuilder;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCountCommitsPerWeekdayPlugin {
    /* Let's check whetherthe sum of the commits of each weekday is equal to the total number of commits */
    @Test
    public void checkCommitSum() {
        var log = new ArrayList<Commit>();
        String[] dates = {"Monday", "Saturday", "Friday", "Thursday"};
        var entries = 20;
        for (int i = 0; i < entries; i++) {
            log.add(new CommitBuilder("").setDate(dates[i % 3]).createCommit());
        }
        var res = CountCommitsPerWeekdayPlugin.processLog(log);
        var sum = res.getCommitsPerDate().values().stream().reduce(0, Integer::sum);
        assertEquals(entries, sum.longValue());
    }
}
