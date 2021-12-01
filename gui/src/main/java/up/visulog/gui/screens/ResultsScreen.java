package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.swing.JPanel;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gui.Window;
import up.visulog.gui.components.ResultsComponents;
import up.visulog.gui.validators.ResultsValidators;

public class ResultsScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Window window;
	
    public ResultsScreen(Window window, List<Commit> gitlog, Configuration config) throws FontFormatException, IOException {
    	this.window = window;
        HashSet<String> authors = ResultsValidators.getAuthors(gitlog);
        ResultsComponents.setGridBagLayout(window, this, window.getProjectName()+" - Results", authors, config);
    }
}