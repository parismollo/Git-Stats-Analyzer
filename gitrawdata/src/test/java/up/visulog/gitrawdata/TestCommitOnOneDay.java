package up.visulog.gitrawdata;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static org.junit.Assert.assertEquals;

public class TestCommitOnOneDay {

    @Test
    public void testParseLog() throws IOException, URISyntaxException{
        var expected = "[Commit{id='96fb0cc5c99c9a92b3fa73aef5f370a3eceef563', date='Sun Nov 14 01:03:14 2021 +0100', author='Leopold Abignoli <leopold.abignoli@gmail.com>', description='Ajout de ChartCountLinesAdded. Modification de ChartCountCommitsPerAuthor.'}]";
        var uri = getClass().getClassLoader().getResource("git.log.onOneDay" ).toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var log = CommitOnOneDay.parseLog(reader);
            System.out.println(log);
            assertEquals(expected, log.toString());
        }

    }

    
}
