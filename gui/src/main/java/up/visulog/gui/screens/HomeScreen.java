package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import src.main.java.components.HomeComponents;
public class HomeScreen extends JFrame {
    public static void main(String[] args) throws FontFormatException, IOException {
        new HomeScreen();
    }

    HomeScreen() throws FontFormatException, IOException {
        HomeComponents.setGridBagLayout(this, "DinoLog", "../resources/dinosaur.png");
    }
}
