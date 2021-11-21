package src.main.java.components;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// import src.main.java.components.Uploader;

public class HomeComponents {

    public static void setGridBagLayout(JFrame frame, String screenTitle, String filePath) throws FontFormatException, IOException {
        setFrame(frame, filePath, 600, 450);
        GridBagLayout GridBagLayoutgrid = new GridBagLayout();  
        GridBagConstraints gbc = new GridBagConstraints();  
        frame.setLayout(GridBagLayoutgrid);  
        frame.setTitle(screenTitle);  
        GridBagLayout layout = new GridBagLayout();  
        frame.setLayout(layout);
        JButton b1 = setUploadtButton();
        JButton b2 = setLatestButton();
        JButton b3 = setMostLikedButton();
        setHomeButtons(frame, b1, b2, b3, gbc);
        frame.getContentPane().setBackground(new Color(88,205,113)); 
    }
    private static void setHomeButtons(JFrame frame, JButton b1, JButton b2, JButton b3, GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 1;  
        gbc.gridy = 0;  
        frame.add(b1, gbc); 
        // gbc.gridx = 1;  
        // gbc.gridy = 0;  
        // frame.add(b2, gbc);  
        // gbc.fill = GridBagConstraints.HORIZONTAL;  
        // gbc.ipady = 20;  
        // gbc.gridx = 0;  
        // gbc.gridy = 1;  
        // this.add(setUploadtButton(), gbc);  
        // gbc.gridx = 1;  
        // gbc.gridy = 1;  
        // this.add(setUploadtButton(), gbc);  
        gbc.gridx = 0;  
        gbc.gridy = 2;  
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 2;  
        frame.add(b2, gbc);
        gbc.gridx = 0;  
        gbc.gridy = 3;  
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 2;  
        frame.add(b3, gbc);
    }
    private static JButton setUploadtButton() throws FontFormatException, IOException {
        Icon icon = new ImageIcon("../resources/cloud-computing.png");
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
        upload_button.setText(" Upload :)");
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("../resources/Poppins-Bold.ttf")).deriveFont(25f);
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
                    upload_button.setText(" Upload ;)");
                    upload_button.setForeground(Color.black); // 219,156,159
                } else {
                    upload_button.setForeground(Color.white);
                    upload_button.setText(" Upload :)");
                }
            }
        });
        // upload_button.setBackground(Color.white);
        setButtonAction(upload_button);
        return upload_button;
}
    private static void setButtonAction(JButton button) { 
            button.addActionListener(e -> Uploader.uploadFile());
    }
    private static JButton setLatestButton() throws FontFormatException, IOException {
        Icon icon = new ImageIcon("../resources/latest_icon.png");
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
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("../resources/Poppins-Bold.ttf")).deriveFont(15f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
        latest_button.setFont(customFont);
        latest_button.revalidate();
        return latest_button;
    }
    private static JButton setMostLikedButton() throws FontFormatException, IOException {
        Icon icon = new ImageIcon("../resources/heart.png");
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
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("../resources/Poppins-Bold.ttf")).deriveFont(15f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
        latest_button.setFont(customFont);
        latest_button.revalidate();
        return latest_button;
    }
    public static void setFrame(JFrame frame, String filePath, int width, int height) {
        ImageIcon img = new ImageIcon(filePath);
        frame.setIconImage(img.getImage());
        frame.setSize(width, height); // 600, 450
        // setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

}
