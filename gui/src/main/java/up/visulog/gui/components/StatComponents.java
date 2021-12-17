package up.visulog.gui.components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
// import java.io.File;
import java.io.IOException;
// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
// import javax.swing.DefaultListModel;
// import javax.swing.Icon;
// import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.JList;
import javax.swing.JRadioButton;

import up.visulog.config.Configuration;
import up.visulog.gui.Window;
import up.visulog.gui.screens.TableScreen;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
public class StatComponents {

    public static void setGridBagLayout(Window window, JPanel panel, String screenTitle, Configuration config) throws FontFormatException, IOException {
    	window.setTitle(screenTitle);

        GridBagLayout GridBagLayoutgrid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        panel.setLayout(GridBagLayoutgrid);  
        GridBagLayout layout = new GridBagLayout();  
        panel.setLayout(layout);
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
        // List<JRadioButton> graphTypes = createRadioButton(getGraphTypes());
        List<JRadioButton> dataType = createRadioButton(getDataTypes());
        JButton runStat = ResultsComponents.createAnyButton("Run", "src/main/resources/stats.png");
        setRunAction(runStat);
        setResultsInScreen(panel, projectTitle, dataType, downloadButton, returnButton, runStat, gbc);
    }
    
    private static void setResultsInScreen(JPanel panel, JLabel projectTitle, List<JRadioButton> dataType, JButton downloadButton, JButton returnButton, JButton runStat, GridBagConstraints gbc) {
        
    	panel.setLayout(new BorderLayout());
    	
    	JPanel pan = new JPanel();
    	pan.setOpaque(false);
    	pan.setLayout(new BorderLayout());
    	pan.add(projectTitle, BorderLayout.EAST);
        pan.add(returnButton, BorderLayout.WEST);
        
        panel.add(pan, BorderLayout.NORTH);
        
        pan = new JPanel();
        pan.setOpaque(false);
        
        for (JRadioButton radio: dataType)
            pan.add(radio);
        
        panel.add(pan, BorderLayout.CENTER);
        
        pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.setOpaque(false);
        
        pan.add(runStat, BorderLayout.CENTER);
        pan.add(downloadButton, BorderLayout.LINE_END);
    	
        panel.add(pan, BorderLayout.SOUTH);
    }    
    public static List<JRadioButton> createRadioButton(String[] dataTypes) throws FontFormatException, IOException {
        // String[] dataTypes = getDataTypes();
        ButtonGroup bg=new ButtonGroup();    
        List<JRadioButton> JRadiobuttons = new ArrayList<JRadioButton>();
        
        for(String type: dataTypes) {
            JRadioButton r=new JRadioButton(type);
            r.setFocusPainted(false);
            r.setBackground(new Color(88,205,113));
            r.setForeground(Color.white);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(12f);
            ge.registerFont(customFont);
            r.setFont(customFont);
            r.revalidate();
            r.setOpaque(false);
            JRadiobuttons.add(r);
            bg.add(r);
        }
        
        return JRadiobuttons;

    }
    
    private static String[] getDataTypes() {
        // TODO (code below is temporary)
        String[] s = {"Commits:Author", "Commits:Weekday", "Lines:Mod"};
        return s;
    }

    private static void setRunAction(JButton button){ 
        button.addActionListener(e -> {
            try {
                // Uploader.uploadFile();
                new TableScreen(); // Temporary :)
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    // private static String[] getGraphTypes() {
    //     // TODO (code below is temporary)
    //     String[] s = {"Pie", "Histogram", "Distribution"};
    //     return s;
    // }
    // private static void getAndAddElements(DefaultListModel<String> l1) {
    //     l1.addElement("Item1");  
    //     l1.addElement("Item2");  
    //     l1.addElement("Item3");  
    //     l1.addElement("Item4");  
    //     // TODO (code above is temporary)
    // }
    
}
