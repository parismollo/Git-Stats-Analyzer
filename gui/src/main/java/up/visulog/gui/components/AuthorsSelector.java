package up.visulog.gui.components;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import up.visulog.config.Configuration;
import up.visulog.graphs.ChartCountCommitsPerAuthor;

public class AuthorsSelector extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<String> authors;
	private List<JCheckBox> authorsBoxes;
	
	public AuthorsSelector(List<String> authors) {
		this.authors = authors;
		setup();
	}
	
	public AuthorsSelector(Configuration config) {
		var chart = new ChartCountCommitsPerAuthor(config);
		var authors = chart.getAuthors(false);
		this.authors = authors;
		setup();
	}
	
	public void setup() {
		this.setOpaque(false);
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		authorsBoxes = new ArrayList<JCheckBox>();
		JCheckBox checkBox;
		for(String s : authors) {
			checkBox = new JCheckBox(s);
			checkBox.setOpaque(false);
			
			authorsBoxes.add(checkBox);
			pan.add(checkBox);
		}
		pan.setPreferredSize(new Dimension((int)pan.getPreferredSize().getWidth()+30, 50));
		this.add(pan);
	}
	
	public List<String> getAuthorsList() {
		List<String> list = new ArrayList<String>();
		for(int i=0;i<authorsBoxes.size();i++) {
			if(authorsBoxes.get(i).isSelected())
				list.add(authors.get(i));
		}
		return list;
	}
	
}
