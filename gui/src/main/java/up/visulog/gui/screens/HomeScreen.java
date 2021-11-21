package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import up.visulog.gui.components.HomeComponents;
public class HomeScreen extends JFrame {
    // public static void main(String[] args) throws FontFormatException, IOException {
    //     new HomeScreen();
    // }

    // public static void runHome() {
    //     new HomeScreen();
    // }

    public HomeScreen() throws FontFormatException, IOException {
        HomeComponents.setGridBagLayout(this, "DinoLog", "src/main/resources/dinosaur.png");
    }
}
