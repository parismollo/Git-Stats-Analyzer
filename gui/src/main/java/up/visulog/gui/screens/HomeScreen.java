package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JPanel;

import up.visulog.gui.Window;
import up.visulog.gui.components.HomeComponents;
public class HomeScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Window window;
	
    public HomeScreen(Window window) throws FontFormatException, IOException {
    	this.window = window;
        HomeComponents.setGridBagLayout(window, this, window.getProjectName()+" - Home");
    }
    
}
