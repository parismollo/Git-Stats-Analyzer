package up.visulog.gui.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import up.visulog.gui.Window;
import up.visulog.gui.screens.TableScreen;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
public class StatComponents {

    public static void setGridBagLayout(Window window, JPanel panel, String screenTitle, String filename) throws FontFormatException, IOException {
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
        JLabel projectTitle = ResultsComponents.createProjectTitle(ResultsComponents.getProjectTitle(filename));
        JButton downloadButton = ResultsComponents.createMenuButton("src/main/resources/download-circular-button.png", "src/main/resources/download-circular-button-white.png", "Download your results");
        // List<JRadioButton> graphTypes = createRadioButton(getGraphTypes());
        List<JRadioButton> dataType = createRadioButton(getDataTypes());
        JButton runStat = ResultsComponents.createAnyButton("Run", "src/main/resources/stats.png");
        setRunAction(runStat);
        setResultsInScreen(panel, projectTitle, dataType, downloadButton, returnButton, runStat, gbc);
    }
    
    private static void setResultsInScreen(JPanel panel, JLabel projectTitle, List<JRadioButton> dataType, JButton downloadButton, JButton returnButton, JButton runStat, GridBagConstraints gbc) {
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

        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // // gbc.gridx = 0;
        // gbc.gridy = 3;
        // gbc.weightx = 0.2;
        // // gbc.anchor = GridBagConstraints.CENTER;
        // int i = 0;
        // for (JRadioButton radio: graphTypes) {
        //     gbc.gridx = i+1;
        //     panel.add(radio, gbc);
        //     i = i + 1;
        // }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = -1;
        gbc.gridy = 7;
        panel.add(runStat, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = 5; //TODO dynamicaly
        gbc.gridy = 8;
        panel.add(downloadButton, gbc);
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
