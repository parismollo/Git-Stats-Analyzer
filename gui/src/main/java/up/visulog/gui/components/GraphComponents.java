package up.visulog.gui.components;
import java.awt.*;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
// import javax.swing.JList;
import javax.swing.JRadioButton;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
public class GraphComponents {

    public static void setGridBagLayout(JFrame frame, String screenTitle, String filePath) throws FontFormatException, IOException {
        HomeComponents.setFrame(frame, filePath, 600, 350);
        GridBagLayout GridBagLayoutgrid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        frame.setLayout(GridBagLayoutgrid);  
        frame.setTitle(screenTitle);  
        GridBagLayout layout = new GridBagLayout();  
        frame.setLayout(layout);
        frame.getContentPane().setBackground(new Color(88,205,113));

        // Create elements/buttons
        // SetScreen
        JButton returnButton = ResultsComponents.createMenuButton("src/main/resources/return.png", "src/main/resources/return-white.png", "Go back");
        JLabel projectTitle = ResultsComponents.createProjectTitle(ResultsComponents.getProjectTitle());
        JButton downloadButton = ResultsComponents.createMenuButton("src/main/resources/download-circular-button.png", "src/main/resources/download-circular-button-white.png", "Download your results");
        List<JRadioButton> graphTypes = createRadioButton(getGraphTypes());
        List<JRadioButton> dataType = createRadioButton(getDataTypes());
        JButton runGraph = ResultsComponents.createAnyButton("Run", "src/main/resources/stats.png");
        setResultsInScreen(frame, projectTitle, graphTypes, dataType, downloadButton, returnButton, runGraph, gbc);
    }
    private static void setResultsInScreen(JFrame frame, JLabel projectTitle, List<JRadioButton> graphTypes, List<JRadioButton> dataType, JButton downloadButton, JButton returnButton, JButton runGraph, GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0;
        gbc.gridx = 5; //TODO dynamicaly
        gbc.gridy = 0;
        frame.add(returnButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,10,0,0);  //left padding
        frame.add(projectTitle, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        // gbc.anchor = GridBagConstraints.CENTER;
        int n = 0;
        for (JRadioButton radio: dataType) {
            gbc.gridx = n+1;
            frame.add(radio, gbc);
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
            frame.add(radio, gbc);
            i = i + 1;
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = -1;
        gbc.gridy = 7;
        frame.add(runGraph, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = 5; //TODO dynamicaly
        gbc.gridy = 8;
        frame.add(downloadButton, gbc);
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
    private static String[] getDataTypes() {
        // TODO (code below is temporary)
        String[] s = {"Modifications", "Merges", "Commits"};
        return s;
    }

    private static String[] getGraphTypes() {
        // TODO (code below is temporary)
        String[] s = {"Pie", "Histogram", "Distribution"};
        return s;
    }
    // private static void getAndAddElements(DefaultListModel<String> l1) {
    //     l1.addElement("Item1");  
    //     l1.addElement("Item2");  
    //     l1.addElement("Item3");  
    //     l1.addElement("Item4");  
    //     // TODO (code above is temporary)
    // }
    
}
