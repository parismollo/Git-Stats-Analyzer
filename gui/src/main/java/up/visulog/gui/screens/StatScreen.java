package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;

import up.visulog.config.Configuration;
import up.visulog.gui.Window;
import up.visulog.gui.components.StatComponents;
public class StatScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Window window;
	
    public StatScreen(Window window, Configuration config) throws FontFormatException, IOException {
    	this.window = window;
        StatComponents.setGridBagLayout(window, this, window.getProjectName()+" - Stats Generator", config);
    }
}