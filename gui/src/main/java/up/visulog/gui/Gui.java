package up.visulog.gui;
import java.awt.FontFormatException;
import java.io.IOException;

import up.visulog.gui.screens.HomeScreen;
public class Gui {

    public static void runGui() throws FontFormatException, IOException {
        new HomeScreen();
    }
}
