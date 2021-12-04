package up.visulog.gui.components;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

// import up.visulog.config.Configuration;
// import up.visulog.gitrawdata.Commit;
import up.visulog.gui.Window;
import up.visulog.gui.validators.HomeValidators;

// When user clicks upload, it will run this function bellow
public class Uploader {

    public static void uploadFile(Window window) throws FontFormatException, IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory to upload: ");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showOpenDialog(window);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				System.out.println("You selected the directory: " + jfc.getSelectedFile());
			}else {
				window.openWarningDialog("Sorry, but this is not a git folder. Only folders with .git will work!");
				System.out.println("Empty or null");
			}
		}
		if (jfc.getSelectedFile() != null) {
			File file_path = jfc.getSelectedFile();
			if (HomeValidators.isGitFolder(file_path)) {
				System.out.println("Is git folder!");
				window.setLatestProject(file_path);
				window.setConfiguration(file_path.toPath());
				runAnalysis(window);
			} else {
				window.openWarningDialog("Sorry, but this is not a git folder. Only folders with .git will work!");
				System.out.println("Is not a git folder!");
			}
		}
    }

	private static void runAnalysis(Window window) throws FontFormatException, IOException{
		window.openResultsScreen();
		window.backToResultsScreen();
	}

	public static void forceAnalysis(Window window) throws FontFormatException, IOException {
		window.setConfiguration(window.getLastProject().toPath());
		runAnalysis(window);
	}
}