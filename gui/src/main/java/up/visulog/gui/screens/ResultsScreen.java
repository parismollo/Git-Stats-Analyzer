package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import up.visulog.gitrawdata.Commit;
import up.visulog.gui.components.ResultsComponents;
import up.visulog.gui.validators.*;

public class ResultsScreen extends JFrame {
    // public static void main(String[] args) throws FontFormatException, IOException {
    //     new ResultsScreen();
    // }

    public ResultsScreen(List<Commit> gitlog, String fileName) throws FontFormatException, IOException {
        HashSet<String> authors = ResultsValidators.getAuthors(gitlog);
        ResultsComponents.setGridBagLayout(this, "DinoLog - Results", "src/main/resources/dinosaur.png", authors, fileName);
    }
}