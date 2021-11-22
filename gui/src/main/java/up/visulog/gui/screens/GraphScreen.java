package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

import up.visulog.gui.components.GraphComponents;
public class GraphScreen extends JFrame {
    // public static void main(String[] args) throws FontFormatException, IOException {
    //     new GraphScreen();
    // }

    public GraphScreen(String filename) throws FontFormatException, IOException {
        GraphComponents.setGridBagLayout(this, "DinoLog - Graphs", "src/main/resources/dinosaur.png", filename);
    }
}