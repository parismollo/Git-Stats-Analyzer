package up.visulog.gui.components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
// import javax.swing.SwingConstants;
// import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
// import src.main.java.components.HomeComponents;
import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

import up.visulog.config.Configuration;
import up.visulog.gui.Window;

public class ResultsComponents {
	
    public static void setGridBagLayout(Window window, JPanel panel, String screenTitle, HashSet<String> authors, Configuration config) throws FontFormatException, IOException {
        /*
        1. Set home layout structure (GridBagLayout)
        2. Create componentes
        3. Set Components in Screen

        Info: To modify the style or position, simply go to the function defitinion and add your own style. Otherwise you can
        change the arrangement in the Set function.
        */
    	window.setTitle(screenTitle);

        JButton return_button = createMenuButton("src/main/resources/return.png", "src/main/resources/return-white.png", "Go back");
        
        return_button.addActionListener((event) -> {
        	try {
				window.backToHomeScreen();
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
        });
        
        JLabel project_title = createProjectTitle(getProjectTitle(config));
        JTextArea project_description = createProjectDescriptions(getProjectDescription(config));
        JTextArea project_members = createProjectMembers(getProjectMembers(authors));
        //JButton stats_button = createAnyButton("Generate Stats", "src/main/resources/stats.png");

        //setStatAction(window, stats_button);
        JButton statsGraphsButton = createAnyButton("Generate Graphs & Stats", "src/main/resources/stats.png");
        setGraphAction(window, statsGraphsButton);

        JButton download_button = createMenuButton("src/main/resources/download-circular-button.png", "src/main/resources/download-circular-button-white.png", "Download your results");
        setResultsInScreen(panel, project_title, project_members, project_description, statsGraphsButton, download_button, return_button);
        panel.setBackground(new Color(88,205,113)); 
    }
    
    private static String getProjectMembers(HashSet<String> authors) {
        String s = "Made by";
        for (String author : authors) {
            s+=" "+author;
        }
        
        /*if(s.length() > 150)
        	s = s.substring(0, 150)+"...";*/
        
        return s;
    }
    
    private static String getProjectDescription(Configuration config) {
        File gitDir = new File(config.getGitPath().toString());
        File[] subFiles = gitDir.listFiles();
        String s = "";
        if (subFiles != null) {
            for (File file: subFiles) {
                if(file.getName().equals("README.md")) {
                    try (Scanner scanner = new Scanner(file)) {
                        scanner.useDelimiter(" ");
                        while (scanner.hasNext()) {
                            s +=" "+scanner.next();
                        }
                    } catch (Exception e) {
                        s = "No description so far...";
                    }
                }
            }
        }
        // String s = "Testing Description";
        return s;
    }
    
    public static String getProjectTitle(Configuration config) {
        return config.getGitPath().getFileName().toString();
    }
    
    private static void setResultsInScreen(JPanel panel, JLabel projectTitle, JTextArea projectMembers, JTextArea projectDescription, JButton statsGraphsButton, JButton downloadButton, JButton returnButton) {
        /* GridLayout

        0 0 1  (Return Button)
        1 0 1  (Title) (members)
        0 1 0  (Description)
        1 0 1  (Button 1) (Button 2)
        1 0 0  (Download Button)

        */
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

    	pan.add(projectDescription, BorderLayout.NORTH);
    	pan.add(projectMembers, BorderLayout.CENTER);
        
    	JScrollPane scrollPane = new JScrollPane(pan);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);    	

        panel.add(scrollPane, BorderLayout.CENTER);
        pan = new JPanel();
        pan.setOpaque(false);
        pan.add(statsGraphsButton);
        pan.add(downloadButton);
        
        panel.add(pan, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
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
        //label_button.setBounds(50,50, 100,30);
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
    
    private static JTextArea createProjectMembers(String label) throws FontFormatException, IOException {
        JTextArea members_label = new JTextArea(label);
        members_label.setEditable(false);
        members_label.setOpaque(false);
        members_label.setLineWrap(true); // Pour un retour à ligne automatique
        members_label.setWrapStyleWord(true); // Pour que les mots ne soient pas coupés
        //members_label.setBounds(50,50, 100,30);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        ge.registerFont(customFont);
        members_label.setFont(customFont);
        members_label.setForeground(Color.BLACK);
        members_label.revalidate();
        return members_label;
    }
    
    private static JTextArea createProjectDescriptions(String label) throws FontFormatException, IOException {
        JTextArea label_description = new JTextArea();
        label_description.setEditable(false);
        label_description.setOpaque(false);
        label_description.setLineWrap(true); // Pour un retour à ligne automatique
        label_description.setWrapStyleWord(true); // Pour que les mots ne soient pas coupés
        //label_description.setBounds(50,50, 100,30);
        label_description.setForeground(Color.white);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        ge.registerFont(customFont);
        label_description.setFont(customFont);
        label_description.setText(label);
        //label_description.setText("<html><p align=\"justify\" style=\"width: 400px\">"+label+"</p></html>");
        label_description.revalidate();
        return label_description;
    }
    
    private static JButton createIconButton(String icon_path) {
        Icon icon = createIcon(icon_path);
        JButton button = new JButton(icon);
        //button.setBounds(130, 100, 100, 50);
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
    
    private static void setGraphAction(Window window, JButton button){ 
        button.addActionListener(e -> {
            try {
                // Uploader.uploadFile();
                window.openGraphScreen();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
    
    private static void setStatAction(Window window, JButton button){ 
        button.addActionListener(e -> {
            try {
                // Uploader.uploadFile();
                window.openStatsScreen();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
    
}
