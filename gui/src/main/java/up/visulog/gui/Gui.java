package up.visulog.gui;
import java.awt.FontFormatException;
import java.io.IOException;

public class Gui {

    public static void runGui() throws FontFormatException, IOException {
        // new HomeScreen();
        // new ResultsScreen(null, null);
        // new GraphScreen();
        // new StatScreen();
    }

    public static void runDemo() throws FontFormatException, IOException {
        // new HomeScreen();
    	new Window("DinoLog", 600, 600);
    }
}
