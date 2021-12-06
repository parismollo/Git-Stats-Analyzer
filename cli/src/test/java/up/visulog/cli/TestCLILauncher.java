package up.visulog.cli;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.FontFormatException;
import java.io.IOException;

public class TestCLILauncher {
    /*
    TODO: one can also add integration tests here:
    - run the whole program with some valid options and look whether the output has a valid format
    - run the whole program with bad command and see whether something that looks like help is printed
     */
    @Test
    public void testArgumentParser() throws FontFormatException, IOException {
        var config1 = CLILauncher.makeConfigFromCommandLineArgs(new String[]{".", "--addPlugin=countCommits"});
        assertTrue(config1.isPresent());
        var config2 = CLILauncher.makeConfigFromCommandLineArgs(new String[] {
            "--nonExistingOption"
        });
        assertFalse(config2.isPresent());
    }

    /* @Test
    public void testRightDateFormat() {
        var date1 = "2021-11-29";
        var date2 = "2021-11-30";
        assertTrue(CLILauncher.rightDateFormat(date1, date2));
        assertFalse(CLILauncher.rightDateFormat(date2, date1));
        assertFalse(CLILauncher.rightDateFormat(date2, date2));
    } */
}
