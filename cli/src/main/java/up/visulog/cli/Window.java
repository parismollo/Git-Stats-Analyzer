package up.visulog.cli;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Window(int w, int h) {
		super();
		this.setTitle("Visulog"); // On change le titre de la fenetre.
		this.setMinimumSize(new Dimension(w, h)); // On change la taille minimum de la fenetre.
		this.setResizable(true); // true -> la page est redimensionnable.
		this.setLocationRelativeTo(null); // null -> permet de centrer la fenetre au milieu de l'ecran.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On indique qu'il faut que le programme s'arrête lorsqu'on ferme la fenetre.

		//setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Met la fenetre en plein ecran.
		
		this.getContentPane().add(new HomePanel(this), BorderLayout.CENTER); // On ajoute la page d'accueil au panel principal.
		
		this.setVisible(true); // On affiche la fenetre.
	}

	public void changePage(int index) // Fonction pour passer d'une page à une autre...
	{
		this.getContentPane().removeAll(); // On vide le panel principal.
		switch(index)
		{
		case 0:
			getContentPane().add(new HomePanel(this), BorderLayout.CENTER); // On ajoute au panel principale la nouvelle page.
		case 1:
			// getContentPane().add(new AutrePage(this), BorderLayout.CENTER); // Par exemple...
			break;
		}
		revalidate(); // On indique à java qu'il y a eu des changements.
		repaint(); // On force Java à re-dessiner tous les elements.
	}
	
}
