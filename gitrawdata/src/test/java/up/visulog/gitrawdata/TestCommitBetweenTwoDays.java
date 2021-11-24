package up.visulog.gitrawdata;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TestCommitBetweenTwoDays {
    @Test
    public void testParseLog() throws IOException, URISyntaxException{
        var expected = "[Commit{id='503cfa8f5504f4a621c60e4aee697e3fce1326f7', date='Tue Nov 16 00:25:55 2021 +0100', author='EL ALLAM hafsa <hafsabondy@gmail.com>', description='Ajout de ChartCountLinesDeleted'}, Commit{id='293d5f161b8fd9e1b48e5898426d8ac33e7993ea', date='Mon Nov 15 00:12:43 2021 +0100', author='Victoria Batantou <victoria.batantou@gmail.com>', description='Ajout de ChartCountCommitsPerWeekday'}, Commit{id='96fb0cc5c99c9a92b3fa73aef5f370a3eceef563', date='Sun Nov 14 01:03:14 2021 +0100', author='Leopold Abignoli <leopold.abignoli@gmail.com>', description='Ajout de ChartCountLinesAdded. Modification de ChartCountCommitsPerAuthor.'}]";
        var uri = getClass().getClassLoader().getResource("git.log.betweenTwoDays" ).toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var log = Commit.parseLog(reader);
            System.out.println(log);
            assertEquals(expected, log.toString());
        }

    }

}
