package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import up.visulog.gui.components.ResultsComponents;
public class ResultsScreen extends JFrame {
    // public static void main(String[] args) throws FontFormatException, IOException {
    //     new ResultsScreen();
    // }

    public ResultsScreen() throws FontFormatException, IOException {
        ResultsComponents.setGridBagLayout(this, "DinoLog", "src/main/resources/dinosaur.png");
    }
}