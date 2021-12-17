package up.visulog.gui.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import up.visulog.gui.Window;

// import src.main.java.components.Uploader;

public class HomeComponents {

    public static void setGridBagLayout(Window window, JPanel panel, String screenTitle) throws FontFormatException, IOException {
    	window.setTitle(screenTitle);
        GridBagLayout GridBagLayoutgrid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        panel.setLayout(GridBagLayoutgrid);  
        GridBagLayout layout = new GridBagLayout();  
        panel.setLayout(layout);
        JButton b1 = setUploadButton(window);
        JButton b2 = setLatestButton(window);
        // JButton b3 = setMostLikedButton(window);
        setHomeButtons(panel, b1, b2, gbc);
        panel.setBackground(new Color(88,205,113));
    }
    
    private static void setHomeButtons(JPanel panel, JButton b1, JButton b2, GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 1;  
        gbc.gridy = 0;  
        panel.add(b1, gbc); 
 
        gbc.gridx = 0;  
        gbc.gridy = 2;  
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 2;  
        panel.add(b2, gbc);
        
        // gbc.gridx = 0;  
        // gbc.gridy = 3;  
        // gbc.fill = GridBagConstraints.HORIZONTAL;  
        // gbc.gridwidth = 2;  
        // panel.add(b3, gbc);
    }
    
    private static JButton setUploadButton(Window window) throws FontFormatException, IOException {
        Icon icon = new ImageIcon("src/main/resources/cloud-computing.png");
        Image image = ((ImageIcon) icon).getImage(); // transform it 
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back
        // String upload_message = "Upload your jurrassic project!";
        JButton upload_button = new JButton(icon);
        upload_button.setBounds(130, 100, 100, 50);
        upload_button.setOpaque(false);
        upload_button.setContentAreaFilled(false);
        upload_button.setBorderPainted(false);
        upload_button.setFocusPainted(false);
        upload_button.setForeground(Color.white);
        upload_button.setText(" Open Project ");
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(25f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
        upload_button.setFont(customFont);
        upload_button.revalidate();
        upload_button.setVerticalTextPosition(SwingConstants.BOTTOM);
        upload_button.setHorizontalTextPosition(SwingConstants.CENTER);
        upload_button.getModel().addChangeListener((ChangeListener) new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (upload_button.getModel().isRollover()) {
                    // upload_button.setText(" Open Folder ;)");
                    upload_button.setForeground(Color.black); // 219,156,159
                } else {
                    upload_button.setForeground(Color.white);
                    // upload_button.setText(" Open Folder :)");
                }
            }
        });
        // upload_button.setBackground(Color.white);
        setButtonAction(window, upload_button);
        return upload_button;
    }
    
    private static void setButtonAction(Window window, JButton button){ 
            button.addActionListener(e -> {
                try {
                    Uploader.uploadFile(window);
                } catch (FontFormatException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
    }
    
    private static JButton setLatestButton(Window window) throws FontFormatException, IOException {
        Icon icon = new ImageIcon("src/main/resources/latest_icon.png");
        Image image = ((ImageIcon) icon).getImage(); // transform it 
        Image newimg = image.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back

        JButton latest_button = new JButton(icon);
        latest_button.setBounds(100, 80, 80, 50);
        latest_button.setOpaque(false);
        latest_button.setContentAreaFilled(false);
        latest_button.setBorderPainted(false);
        latest_button.setFocusPainted(false);
        latest_button.setForeground(Color.white);
        latest_button.setText(" Run the latest project!");
        latest_button.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (latest_button.getModel().isRollover()) {
                    latest_button.setText(" Run the latest project!");
                    latest_button.setForeground(Color.black); // 219,156,159
                } else {
                    latest_button.setForeground(Color.white);
                    latest_button.setText(" Run the latest project!");
                }
            }
        });
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
        latest_button.setFont(customFont);
        latest_button.revalidate();
        setLatestAction(window, latest_button);
        return latest_button;
    }

    private static void setLatestAction(Window window, JButton button){ 
        button.addActionListener(e -> {
            try {
                Uploader.forceAnalysis(window);
            } catch (Exception exc) {
                window.openWarningDialog("Sorry, but this is empty...Try again after running some projects!");
                // exc.printStackTrace();
            }
        });
}
    
    private static JButton setMostLikedButton(Window window) throws FontFormatException, IOException {
        Icon icon = new ImageIcon("src/main/resources/heart.png");
        Image image = ((ImageIcon) icon).getImage(); // transform it 
        Image newimg = image.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back

        JButton latest_button = new JButton(icon);
        latest_button.setBounds(100, 80, 80, 50);
        latest_button.setOpaque(false);
        latest_button.setContentAreaFilled(false);
        latest_button.setBorderPainted(false);
        latest_button.setFocusPainted(false);
        latest_button.setForeground(Color.white);
        latest_button.setText(" Run the most liked project!");
        latest_button.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (latest_button.getModel().isRollover()) {
                    latest_button.setText(" Run the most liked project!");
                    latest_button.setForeground(Color.black); // 219,156,159
                } else {
                    latest_button.setForeground(Color.white);
                    latest_button.setText(" Run the most liked project!");
                }
            }
        });
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Poppins-Bold.ttf")).deriveFont(15f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
        latest_button.setFont(customFont);
        latest_button.revalidate();
        setMostLikedAction(window, latest_button);
        return latest_button;
    }

    private static void setMostLikedAction(Window window, JButton button){ 
        button.addActionListener(e -> {
            window.openWarningDialog("Sorry, but this is not implemented yet....Try again soon!");
        });
}

}
