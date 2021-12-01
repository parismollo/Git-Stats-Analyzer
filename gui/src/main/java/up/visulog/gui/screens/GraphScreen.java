package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;

import up.visulog.config.Configuration;
import up.visulog.gui.Window;
import up.visulog.gui.components.GraphComponents;
public class GraphScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Window window;
	
    public GraphScreen(Window window, Configuration config) throws FontFormatException, IOException {
    	this.window = window;
        GraphComponents.setGridBagLayout(window, this, window.getProjectName()+" - Graphs", config);
    }
}