package up.visulog.gitrawdata;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCommit {
    @Test
    public void testParseCommit() throws IOException, URISyntaxException {
        var expected = "Commit{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='Tue Sep 1 12:30:53 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>', description='More gradle configuration (with subprojects)'}";
        var uri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var commit = Commit.parseCommit(reader);
            assertTrue(commit.isPresent());
            assertEquals(expected, commit.get().toString());
        }
    }

    @Test
    public void testParseLog() throws IOException, URISyntaxException {
        var expectedUri = getClass().getClassLoader().getResource("expectedToString").toURI();
        var logUri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var expectedReader =  Files.newBufferedReader(Paths.get(expectedUri))) {
            try (var logReader = Files.newBufferedReader(Paths.get(logUri))) {
                var log = Commit.parseLog(logReader);
                var expected = expectedReader.lines().reduce("", (cur, acc) -> cur + acc);
                assertEquals(expected, log.toString());
            }
        }
    }

}
