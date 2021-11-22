package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

import up.visulog.gui.components.TableComponents;
public class TableScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	public TableScreen() throws FontFormatException, IOException {
        TableComponents.setFrameAndTable(this, "DinoLog - Stats Table", "src/main/resources/dinosaur.png");
    }
}