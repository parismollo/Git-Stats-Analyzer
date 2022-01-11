package up.visulog.gui.components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
// import java.io.File;
import java.io.IOException;
// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
// import javax.swing.DefaultListModel;
// import javax.swing.Icon;
// import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;

import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.CountCommitLinesChanged;
import up.visulog.analyzer.CountCommitLinesChangedBetweenDays;
import up.visulog.analyzer.CountCommitsBetweenDays;
import up.visulog.analyzer.CountCommitsPerAuthorPlugin;
import up.visulog.analyzer.CountMergePerAuthorPlugin;
import up.visulog.analyzer.CountMergesBetweenDaysPlugin;
import up.visulog.config.Configuration;
import up.visulog.graphs.ChartAnalysis;
import up.visulog.graphs.ChartCountCommitsLinesAdded;
import up.visulog.graphs.ChartCountCommitsLinesDeleted;
import up.visulog.graphs.ChartCountCommitsPerAuthor;
import up.visulog.graphs.ChartCountMergeCommitsPerAuthor;
import up.visulog.gui.Window;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
public class GraphComponents {

    public static void setGridBagLayout(Window window, JPanel panel, String screenTitle, Configuration config) throws FontFormatException, IOException {
    	window.setTitle(screenTitle);
    	
        panel.setBackground(new Color(88,205,113));

        // Create elements/buttons
        // SetScreen
        JButton returnButton = ResultsComponents.createMenuButton("src/main/resources/return.png", "src/main/resources/return-white.png", "Go back");
        
        returnButton.addActionListener((event) -> {
        	try {
				window.backToResultsScreen();
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
        });
        
        JLabel projectTitle = ResultsComponents.createProjectTitle(ResultsComponents.getProjectTitle(config));
        JButton downloadButton = ResultsComponents.createMenuButton("src/main/resources/download-circular-button.png", "src/main/resources/download-circular-button-white.png", "Download your results");
        
        ButtonGroup dataTypesGroup = new ButtonGroup();
        List<JRadioButton> dataType = createRadioButton(dataTypesGroup, getDataTypes());
        dataType.get(dataType.size()-1).setSelected(true);
        
        JComboBox<String> datesSpinner = new JComboBox<String>(getDatesIntervals());
        
        DatePicker d1 = new DatePicker(),
        		   d2 = new DatePicker();
        
        JPanel datesPanel = new JPanel();
        JPanel customDatesPanel = new JPanel();
        customDatesPanel.setOpaque(false);
        customDatesPanel.setVisible(false);
        customDatesPanel.add(d1);
        customDatesPanel.add(new JLabel(" >>> "));
        customDatesPanel.add(d2);
        
        datesPanel.setOpaque(false);
        datesPanel.add(new JLabel("Dates : "));
        datesPanel.add(datesSpinner);
        datesPanel.add(customDatesPanel);

        
        datesSpinner.addActionListener((event) -> {
        	if(datesSpinner.getSelectedIndex() == 4)
        		customDatesPanel.setVisible(true);
        	else
        		customDatesPanel.setVisible(false);
        });
        
        AuthorsSelector authorsSelector = new AuthorsSelector(config);
        
        JPanel authorsPan = new JPanel();
        authorsPan.setLayout(new BoxLayout(authorsPan, BoxLayout.LINE_AXIS));
        authorsPan.setOpaque(false);
        authorsPan.add(new JLabel("Authors : "));
        JScrollPane scrollPane = new JScrollPane(authorsSelector,
        		JScrollPane.VERTICAL_SCROLLBAR_NEVER,
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        authorsPan.add(scrollPane);
        
        //List<JRadioButton> graphTypes = createRadioButton(getGraphTypes());
        JComboBox<String> graphTypesCB = new JComboBox<String>(getGraphTypes());
        JPanel chartPanel = new JPanel();
        chartPanel.setOpaque(false);
        
        JButton runGraph = ResultsComponents.createAnyButton("Run graph", "src/main/resources/stats.png");
        JButton runStats = ResultsComponents.createAnyButton("Run stats", "src/main/resources/stats.png");
        
        setRunGraphActionListener(config, dataType, authorsSelector, graphTypesCB, runStats, runGraph, chartPanel, datesSpinner, d1, d2);
        setResultsInScreen(panel, projectTitle, authorsPan, datesPanel, graphTypesCB, dataType, chartPanel,
        				   downloadButton, returnButton, runGraph, runStats);
    }
    
    private static void setResultsInScreen(JPanel panel, JLabel projectTitle, JPanel authorsPan, JPanel datesPanel,
    		JComboBox<String> graphTypesCB, List<JRadioButton> dataType, JPanel chartPanel, JButton downloadButton,
    		JButton returnButton, JButton runGraph, JButton runStats) {
        
    	
    	panel.setLayout(new BorderLayout());
    	
    	JPanel pan = new JPanel();
    	pan.setOpaque(false);
    	pan.setLayout(new BorderLayout());
    	pan.add(projectTitle, BorderLayout.EAST);
        pan.add(returnButton, BorderLayout.WEST);
        
        panel.add(pan, BorderLayout.NORTH);
        
        pan = new JPanel();
        pan.setOpaque(false);
        pan.setLayout(new BorderLayout());
        
        JPanel childPan = new JPanel();
        childPan.setLayout(new BorderLayout());
        childPan.setOpaque(false);
        
        JPanel childPan2 = new JPanel();
        childPan2.setOpaque(false);
        
        for (JRadioButton radio: dataType)
            childPan2.add(radio);

        childPan.add(childPan2, BorderLayout.NORTH);
        childPan.add(datesPanel, BorderLayout.CENTER);
        childPan.add(authorsPan, BorderLayout.SOUTH);
        
        pan.add(childPan, BorderLayout.NORTH);
        
        childPan = new JPanel();
        childPan.setLayout(new BorderLayout());
        childPan.setOpaque(false);
        
        childPan.add(graphTypesCB, BorderLayout.NORTH);
        childPan.add(chartPanel, BorderLayout.CENTER);
        /*for (JRadioButton radio: graphTypes)
            childPan.add(radio);*/
        
        pan.add(childPan, BorderLayout.CENTER);
        
        panel.add(pan, BorderLayout.CENTER);
        
        pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.setOpaque(false);
        
        JPanel tmpPan = new JPanel();
        tmpPan.setOpaque(false);
        tmpPan.add(runStats);
        tmpPan.add(runGraph);
        pan.add(tmpPan, BorderLayout.CENTER);
        pan.add(downloadButton, BorderLayout.LINE_END);
        
        panel.add(pan, BorderLayout.SOUTH);
        
        /*
    	gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0;
        gbc.gridx = 5; //TODO dynamicaly
        gbc.gridy = 0;
        panel.add(returnButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,10,0,0);  //left padding
        panel.add(projectTitle, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        // gbc.anchor = GridBagConstraints.CENTER;
        int n = 0;
        for (JRadioButton radio: dataType) {
            gbc.gridx = n+1;
            panel.add(radio, gbc);
            n = n + 1;
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        // gbc.anchor = GridBagConstraints.CENTER;
        int i = 0;
        for (JRadioButton radio: graphTypes) {
            gbc.gridx = i+1;
            panel.add(radio, gbc);
            i = i + 1;
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = -1;
        gbc.gridy = 7;
        panel.add(runGraph, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = 5; //TODO dynamicaly
        gbc.gridy = 8;
        panel.add(downloadButton, gbc);
        */
    }

    // public static JList<String> createListElement() throws FontFormatException, IOException {
    //     DefaultListModel<String> l1 = new DefaultListModel<>();
    //     getAndAddElements(l1); 
    //     JList<String> list = new JList<>(l1);  
    //     list.setBounds(100,100, 75,75);
    //     list.setBackground(new Color(88,205,113));
    //     list.setForeground(Color.white);
    //     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    //     Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Bold.ttf")).deriveFont(15f);
    //     ge.registerFont(customFont);
    //     list.setFont(customFont);
    //     list.revalidate();
    //     list.setOpaque(false);
    //     return list;
    // }
    
    public static List<JRadioButton> createRadioButton(ButtonGroup bg, String[] dataTypes) throws FontFormatException, IOException {
        // String[] dataTypes = getDataTypes();
        List<JRadioButton> JRadiobuttons = new ArrayList<JRadioButton>();
        
        for(String type: dataTypes) {
            JRadioButton r=new JRadioButton(type);
            r.setFocusPainted(false);
            r.setBackground(new Color(88,205,113));
            r.setForeground(Color.white);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
            ge.registerFont(customFont);
            r.setFont(customFont);
            r.revalidate();
            r.setOpaque(false);
            JRadiobuttons.add(r);
            bg.add(r);
        }
        
        return JRadiobuttons;

    }
    
    private static String[] getDatesIntervals() {
    	return new String[] {"Last 15 days", "Last month", "Last 3 months", "All time", "Custom"};
    }
    
    private static String[] getDataTypes() {
        // TODO (code below is temporary)
        String[] s = {"LinesAdded", "LinesDeleted", "Merges", "Commits"};
        return s;
    }

    private static String[] getGraphTypes() {
        String[] tab = ChartAnalysis.getGraphTypes();
        for(int i=0;i<tab.length;i++)
        	tab[i] = Character.toUpperCase(tab[i].charAt(0))+tab[i].substring(1);
        return tab;
    }
    
    private static void setRunGraphActionListener(Configuration config, List<JRadioButton> buttons,
    		AuthorsSelector authorsSelector, JComboBox<String> cb, JButton runStats, JButton runGraph,
    		JPanel chartPanel, JComboBox<String> datesSpinner, DatePicker d1, DatePicker d2) {
    	
    	runStats.addActionListener(getRunListener(config, false, buttons, authorsSelector, cb, runGraph, 
				  chartPanel, datesSpinner, d1, d2));
    	
    	runGraph.addActionListener(getRunListener(config, true, buttons, authorsSelector, cb, runGraph, 
    											  chartPanel, datesSpinner, d1, d2));
    	
    }
    
    private static ActionListener getRunListener(Configuration config, boolean graph, List<JRadioButton> buttons,
    						      AuthorsSelector authorsSelector, JComboBox<String> cb, JButton runGraph, JPanel chartPanel,
    						      JComboBox<String> datesSpinner, DatePicker d1, DatePicker d2) {
    	return (event) -> {
    		try {
    			JRadioButton selectedBut = getSelectedButton(buttons);
    			if(selectedBut == null)
    				return;
    			String dataType = selectedBut.getText();
    			List<String> authors = authorsSelector.getAuthorsList();
    			String graphType = (String)cb.getSelectedItem();
    			
    			String date1 = d1.getDate(),
    				   date2 = d2.getDate();
    			
    			int index = datesSpinner.getSelectedIndex();
    			
    			switch(index) {
	    			case 0: // Last 15 days
	    				date1 = DatePicker.getStringDate(-14);
	    				date2 = DatePicker.getStringDate(0); // Today
	    				break;
	    			case 1: // Last month
	    				date1 = DatePicker.getStringDate(-29);
	    				date2 = DatePicker.getStringDate(0); // Today
	    				break;
	    			case 2: // Last 3 months
	    				date1 = DatePicker.getStringDate(-89);
	    				date2 = DatePicker.getStringDate(0); // Today
	    				break;
	    			case 3: // All time
	    				date1 = null;
	    				date2 = null;
	    				break;
    			}

    			refreshChartPanel(config, graph, chartPanel, dataType, authors, graphType, date1, date2);
    		} catch(Exception e) {};
    	};
 
    }
    
    private static JRadioButton getSelectedButton(List<JRadioButton> buttons) {
    	for(JRadioButton b : buttons) {
    		if(b.isSelected())
    			return b;
    	}
    	return null;
    }
    
    private static void refreshChartPanel(Configuration config, boolean graph, JPanel chartPanel,
    		String dataType, List<String> authors, String graphType, String d1, String d2) {
    	
    	dataType = dataType.toLowerCase();
    	graphType = graphType.toLowerCase();
    	
    	chartPanel.setLayout(new GridLayout());
    	chartPanel.removeAll();
    	ChartPanel chartContainer = null;
    	StatsPreview statsPreview = null;
    	
    	switch(dataType) {
    	case "merges":
    		if(graph) {
	    		ChartCountMergeCommitsPerAuthor chartMerges;
	    		if(d1 != null && d2 != null)
	    			chartMerges = new ChartCountMergeCommitsPerAuthor(config, d1, d2);
	    		else
	    			chartMerges = new ChartCountMergeCommitsPerAuthor(config);
	    		chartMerges.refreshAuthors(authors);
	    		chartContainer = chartMerges.createPanel(graphType);
    		}
    		else {
    			AnalyzerPlugin.Result merges;
	    		if(d1 != null && d2 != null)
	    			merges = (new CountMergesBetweenDaysPlugin(config, d1, d2)).getResult();
	    		else
	    			merges = (new CountMergePerAuthorPlugin(config)).getResult();
    			try {
					statsPreview = new StatsPreview(merges.getResultAsHtmlDiv());
				} catch (IOException e) {}
    		}
    		break;
    	case "commits":
    		if(graph) {
	    		ChartCountCommitsPerAuthor chartCommits;
	    		if(d1 != null && d2 != null)
	    			chartCommits = new ChartCountCommitsPerAuthor(config, d1, d2);
	    		else
	    			chartCommits = new ChartCountCommitsPerAuthor(config);
	    		chartCommits.refreshAuthors(authors);
	    		chartContainer = chartCommits.createPanel(graphType);
    		}
    		else {
    			AnalyzerPlugin.Result commits;
	    		if(d1 != null && d2 != null)
	    			commits = (new CountCommitsBetweenDays(config, d1, d2)).getResult();
	    		else
	    			commits = (new CountCommitsPerAuthorPlugin(config)).getResult();
    			try {
					statsPreview = new StatsPreview(commits.getResultAsHtmlDiv());
				} catch (IOException e) {}
    		}
    		break;
    	case "linesadded":
    		if(graph) {
	    		ChartCountCommitsLinesAdded chartLinesAdded;
	    		if(d1 != null && d2 != null)
	    			chartLinesAdded = new ChartCountCommitsLinesAdded(config, d1, d2);
	    		else
	    			chartLinesAdded = new ChartCountCommitsLinesAdded(config);
	    		chartLinesAdded.refreshAuthors(authors);
	    		chartContainer = chartLinesAdded.createPanel(graphType);
    		}
    		else {
    			AnalyzerPlugin.Result commits;
	    		if(d1 != null && d2 != null)
	    			commits = (new CountCommitLinesChangedBetweenDays(config, d1, d2)).getResult();
	    		else
	    			commits = (new CountCommitLinesChanged(config)).getResult();
    			try {
					statsPreview = new StatsPreview(commits.getResultAsHtmlDiv());
				} catch (IOException e) {}
    		}
    		break;
    	case "linesdeleted":
    		if(graph) {
	    		ChartCountCommitsLinesDeleted chartLinesDeleted;
	    		if(d1 != null && d2 != null)
	    			chartLinesDeleted = new ChartCountCommitsLinesDeleted(config, d1, d2);
	    		else
	    			chartLinesDeleted = new ChartCountCommitsLinesDeleted(config);
	    		chartLinesDeleted.refreshAuthors(authors);
	    		chartContainer = chartLinesDeleted.createPanel(graphType);
    		}
    		else {
    			AnalyzerPlugin.Result commits;
	    		if(d1 != null && d2 != null)
	    			commits = (new CountCommitLinesChangedBetweenDays(config, d1, d2)).getResult();
	    		else
	    			commits = (new CountCommitLinesChanged(config)).getResult();
    			try {
					statsPreview = new StatsPreview(commits.getResultAsHtmlDiv());
				} catch (IOException e) {}
    		}
    		break;
    	}
    	
    	if(chartContainer == null && graph)
    		return;
    	if(statsPreview == null && !graph)
    		return;
    	
    	if(chartContainer != null)
    		chartPanel.add(chartContainer);
    	else if(statsPreview != null)
    		chartPanel.add(new JScrollPane(statsPreview));
    	chartPanel.revalidate();
    	chartPanel.repaint();
    }
    
}
