package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

import up.visulog.gui.components.StatComponents;
public class StatScreen extends JFrame {
    // public static void main(String[] args) throws FontFormatException, IOException {
    //     new StatScreen();
    // }

    StatScreen() throws FontFormatException, IOException {
        StatComponents.setGridBagLayout(this, "DinoLog - Stats Generator", "dinosaur.png");
    }
}