package up.visulog.gui;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import up.visulog.gitrawdata.Commit;
import up.visulog.gui.screens.GraphScreen;
import up.visulog.gui.screens.HomeScreen;
import up.visulog.gui.screens.ResultsScreen;
import up.visulog.gui.screens.StatScreen;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	
	public Window(String projectName, int w, int h) throws FontFormatException, IOException {
		this.projectName = projectName;
		this.setIconImage(new ImageIcon("src/main/resources/dinosaur.png").getImage());
		this.setMinimumSize(new Dimension(w, h)); // On change la taille minimum de la fenetre.
		this.setResizable(true); // true -> la page est redimensionnable.
		this.setLocationRelativeTo(null); // null -> permet de centrer la fenetre au milieu de l'ecran.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On indique qu'il faut que le programme s'arr�te lorsqu'on ferme la fenetre.
		/* Exemple pour arrondir les coins. Mais tous les boutons disparaissent
		 * et la page n'est plus redimensionable. On ne peut plus la bouger non plus.
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 180));
		this.addComponentListener(new ComponentAdapter() {
		               @Override
		                public void componentResized(ComponentEvent e) {
		                    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 80, 80));
		                }
		            });
		*/ 
		
		//setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Met la fenetre en plein ecran.
		
		openHomeScreen();
		
		this.setVisible(true); // On affiche la fenetre.
	}

	public void openHomeScreen() throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new HomeScreen(this));
		revalidate();
		repaint();
	}
	
	public void openResultsScreen(List<Commit> gitlog, String fileName) throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new ResultsScreen(this, gitlog, fileName));
		revalidate();
		repaint();
	}
	
	public void openGraphScreen(String fileName) throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new GraphScreen(this, fileName));
		revalidate();
		repaint();
	}
	
	public void openStatsScreen(String fileName) throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new StatScreen(this, fileName));
		revalidate();
		repaint();
	}
	
	public String getProjectName() {
		return projectName;
	}
	
}
