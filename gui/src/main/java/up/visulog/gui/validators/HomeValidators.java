package up.visulog.gui.validators;

import java.io.File;

public class HomeValidators {

	public static boolean isGitFolder(File file_path) {
		if(file_path == null || !file_path.exists()) {
			return false;
		} // Si le dossier n'existe pas, on quitte.
		File[] files = file_path.listFiles(); // On recupere tous les fichiers et dossiers contenus dans le dossier passé en argurment.
		for(File file : files) {
			// On parcourt le tableau.
			if(file.isDirectory() && file.getName().equals(".git")) {
				return true;
			} // Si on trouve un dossier qui a pour nom exacte ".git" alors on renvoie true.
		}
		return false; // Si on à pas trouvé de dossier ".git".
	}
}
