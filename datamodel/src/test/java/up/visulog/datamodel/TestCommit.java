package up.visulog.datamodel;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TestCommit {
    @Test
    public void testParse() throws IOException, URISyntaxException {
        var expected = "Commit{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='  Tue Sep 1 12:30:53 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>', description='More gradle configuration (with subprojects)'}";
        var uri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var commit = Commit.parse(reader);
            assertEquals(expected, commit.toString());
        }
    }
}
