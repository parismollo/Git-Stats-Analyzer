package up.visulog.gui;
import java.awt.FontFormatException;
import java.io.IOException;

import up.visulog.config.Configuration;

public class Gui {

    public static void runGui() throws FontFormatException, IOException {
    	new Window("DinoLog", 600, 600);
    }

}
