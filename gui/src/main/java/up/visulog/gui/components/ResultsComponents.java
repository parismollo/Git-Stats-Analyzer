package up.visulog.gui.components;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
// import src.main.java.components.HomeComponents;

public class ResultsComponents {
    public static void setGridBagLayout(JFrame frame, String screenTitle, String filePath, HashSet<String> authors, String fileName) throws FontFormatException, IOException {
        /*
        1. Set home layout structure (GridBagLayout)
        2. Create componentes
        3. Set Components in Screen

        Info: To modify the style or position, simply go to the function defitinion and add your own style. Otherwise you can
        change the arrangement in the Set function.
        */
        HomeComponents.setFrame(frame, filePath, 600, 450);
        GridBagLayout GridBagLayoutgrid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        frame.setLayout(GridBagLayoutgrid);  
        frame.setTitle(screenTitle);  
        GridBagLayout layout = new GridBagLayout();  
        frame.setLayout(layout);

        JButton return_button = createMenuButton("src/main/resources/return.png", "src/main/resources/return-white.png", "Go back");
        JLabel project_title = createProjectTitle(getProjectTitle(fileName));
        JLabel project_description = createProjectDescriptions(getProjectDescription());
        JLabel project_members = createProjectMembers(getProjectMembers(authors));
        JButton stats_button = createAnyButton("Generate Stats", "src/main/resources/stats.png");
        JButton graphs_button = createAnyButton("Generate Graphs", "src/main/resources/stats.png");
        JButton download_button = createMenuButton("src/main/resources/download-circular-button.png", "src/main/resources/download-circular-button-white.png", "Download your results");
        setResultsInScreen(frame, project_title, project_members, project_description, stats_button, graphs_button, download_button, return_button, gbc);
        frame.getContentPane().setBackground(new Color(88,205,113)); 
    }
    private static String getProjectMembers(HashSet<String> authors) {
        String s = "Made by";
        for (String author : authors) {
            s+=" "+author;
        }
        return s;
    }
    private static String getProjectDescription() {
        // TODO
        String s = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin facilisis ornare augue, nec sagittis felis accumsan a. Suspendisse at nibh ac ante ultricies malesuada sit amet sed lorem. Aenean viverra elit nec quam suscipit, a scelerisque sapien consectetur. Cras faucibus quam neque, consequat tincidunt risus sollicitudin at. In at nibh sed leo fringilla bibendum.";
        return s;
    }
    public static String getProjectTitle(String filename) {
        // TODO: temporary, the ideia to to receive a config file and return the name only.
        return filename;
    }
    private static void setResultsInScreen(JFrame frame, JLabel projectTitle, JLabel projectMembers, JLabel projectDescription, JButton statsButton, JButton graphsButton, JButton downloadButton, JButton returnButton, GridBagConstraints gbc) {
        /* GridLayout

        0 0 1  (Return Button)
        1 0 1  (Title) (members)
        0 1 0  (Description)
        1 0 1  (Button 1) (Button 2)
        1 0 0  (Download Button)

        */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0,10,0,0);  //left padding
        frame.add(projectTitle, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 3;
        // gbc.anchor = GridBagConstraints.PAGE_END;
        // gbc.insets = new Insets(0,10,0,0);  //left padding
        frame.add(projectMembers, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        // gbc.anchor = GridBagConstraints.CENTER;
        frame.add(statsButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        // gbc.anchor = GridBagConstraints.CENTER;
        frame.add(graphsButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 5;
        frame.add(downloadButton, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        frame.add(returnButton, gbc);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 40; 
        gbc.weightx = 0.0;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(projectDescription, gbc);
    }

    public static JButton createMenuButton(String icon_path, String icon_path_white, String label) throws FontFormatException, IOException {
        JButton button = createIconButton(icon_path);
        button.setForeground(Color.white);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        ge.registerFont(customFont);
        button.setFont(customFont);
        button.revalidate();
        button.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (button.getModel().isRollover()) {
                    Icon icon = createIcon(icon_path_white);
                    button.setIcon(icon); // 219,156,159
                } else {
                    Icon icon = createIcon(icon_path);
                    button.setIcon(icon); // 219,156,159
                }
            }
        });
        return button;
    }
    public static JButton createAnyButton(String label, String icon_path) throws FontFormatException, IOException {
        Icon icon = new ImageIcon(icon_path);
        Image image = ((ImageIcon) icon).getImage(); // transform it 
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back
        // String upload_message = "Upload your jurrassic project!";
        JButton button = new JButton(icon);
        button.setBounds(130, 100, 100, 50);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.white);
        button.setText(label);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(12f);
        ge.registerFont(customFont);
        button.setFont(customFont);
        button.revalidate();
        button.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (button.getModel().isRollover()) {
                    // button.setText(" Go back ");
                    button.setForeground(Color.black); // 219,156,159
                } else {
                    button.setForeground(Color.white);
                    // button.setText("");
                }
            }
        });
        return button;
    }

    public static JLabel createProjectTitle(String label) throws FontFormatException, IOException {
        JLabel label_button = new JLabel(label);  
        label_button.setBounds(50,50, 100,30);
        // 38	97	156
        // 35	46	58
        // 242	24	24
        label_button.setForeground(Color.black);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(33f);
        ge.registerFont(customFont);
        label_button.setFont(customFont);
        label_button.revalidate();
        return label_button;
    }
    private static JLabel createProjectMembers(String label) throws FontFormatException, IOException {
        JLabel members_label = new JLabel(label);  
        members_label.setBounds(50,50, 100,30);
        members_label.setForeground(Color.black);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        ge.registerFont(customFont);
        members_label.setFont(customFont);
        members_label.revalidate();
        return members_label;
    }
    private static JLabel createProjectDescriptions(String label) throws FontFormatException, IOException {
        JLabel label_description = new JLabel();  
        label_description.setBounds(50,50, 100,30);
        label_description.setForeground(Color.white);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        ge.registerFont(customFont);
        label_description.setFont(customFont);
        label_description.revalidate();
        label_description.setText("<html><p align=\"justify\" style=\"width: 400px\">"+label+"</p></html>");
        return label_description;
    }
    private static JButton createIconButton(String icon_path) {
        Icon icon = createIcon(icon_path);
        JButton button = new JButton(icon);
        button.setBounds(130, 100, 100, 50);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        return button;
    }

    private static Icon createIcon(String icon_path) {
        Icon icon = new ImageIcon(icon_path);
        Image image = ((ImageIcon) icon).getImage(); // transform it 
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }

}
