package up.visulog.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OldHomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel welcomeTitle, errorMsg; // Titre et message d'erreur
	private JPanel bottomContainer; // Contient le bouton dossier et eventuellement un message d'erreur.
	private GridBagConstraints gbc; // Contraintes de placement des objets dans le bottomContainer.
	private ImagePanel chooseFolder; // bouton en forme de dossier
	private Window win; // On garde un lien vers la fenetre. Pratique pour appeller la methode "changePage(index)".
	
	public OldHomePanel(Window win) {
		super();
		this.win = win;
		
		welcomeTitle = new JLabel("Welcome to Visulog !"); // Permet d'afficher un texte dans un panel.
		welcomeTitle.setFont(new Font(welcomeTitle.getFont().getFontName(), Font.BOLD, 24)); // On met la police en gras taille 24.
		welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // On centre horizontalement le titre.
		
		errorMsg = new JLabel("It's not a git project !"); // On cree le message d'erreur (qu'on utilisera pas forcement !)
		errorMsg.setForeground(Color.RED); // On met le texte en couleur rouge.
		
		bottomContainer = new JPanel(); // On cree un autre panel o� on va mettre le bouton pour selectionner le projet git.
		
		gbc = new GridBagConstraints(); // Contraintes de placements pour le bouton dossier et le message d'erreur.
        gbc.anchor = GridBagConstraints.LINE_END;
		
		File folderImg = new File("src/main/resources/folder.png"); // On recupere l'image en forme de dossier.
		
		chooseFolder = new ImagePanel(folderImg, new Dimension(110, 100)); // On cree le bouton avec une image de dossier, taille 110, 100.
		chooseFolder.addMouseListener(new MouseAdapter() { // On ajoute un mouseListener pour detecter un clique de souris sur le bouton.
			
			@Override
			public void mouseReleased(MouseEvent e) { // S'execute lorsqu'on lache le click de la souris.
				String folderPath = getFolderPath(OldHomePanel.this);
				if(folderPath.isEmpty()) // L'utilisateur � cliqu� sur cancel ou sur croix. Pas de message � afficher.
					return;
				File folder = new File(folderPath); // On demande de choisir un dossier puis on met son URL dans un objet File.
				if(!isGitProject(folder)) { // Si ce n'est pas un projet git
					if(bottomContainer.getComponent(bottomContainer.getComponentCount()-1) == errorMsg) // Le message d'erreur est d�j� affich� � l'�cran.
						return;
					// Sinon on affiche le message d'erreur en ajoutant le JLabel au bottomContainer :

			        gbc.gridx = 1;
			        gbc.gridy = 1; // On le place en dessous du bouton dossier (gridy=0)
					bottomContainer.add(errorMsg, gbc); // On ajoute au bottomContainer et en precisant les contraintes.
				}
				else {
					bottomContainer.remove(errorMsg); // Si le message d'erreur est affich�, on le retire. Sinon on fait rien.
					
					bottomContainer.remove(chooseFolder); // On enleve le bouton.
					bottomContainer.add(new JLabel("Your project folder is : "+folder.getAbsolutePath())); // On ajoute un texte avec l'adresse du dossier choisi.
				}
				revalidate(); // On indique � java qu'il y a eu des changements sur les elements.
				repaint(); // On force java � re-dessiner tous les elements.
				//win.changePage(1); // On change de page. Inutile pour l'instant.
			}
			
		});
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // On indique que les elements de la page doivent etre plac�s verticalement.
		
		bottomContainer.setLayout(new GridBagLayout()); // To center items in the panel

        gbc.gridx = 1;
        gbc.gridy = 0; // On place le dossier en premier (avant le futur message d'erreur [si il y en a un])
		bottomContainer.add(chooseFolder, gbc); // On ajoute le bouton en forme de dossier au bottomContainer (JPanel) en pr�cisant les contraintes de placement.
		
		this.add(welcomeTitle); // On ajoute le titre � notre HomePanel
		this.add(bottomContainer); // Puis on ajoute le bottomContainer, qui contient lui meme notre bouton.
	}
	
	public static String getFolderPath(Component comp) { // Demande de selectionner un dossier.
		JFileChooser choice = new JFileChooser();
		choice.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // On force l'utilisateur � choisir uniquement des dossiers.
		String path = "";
		int var = choice.showOpenDialog(comp); // On ouvre l'explorateur de fichier et on renvoie 0 ou 1, 2.. selon la maniere dont l'utilisateur quitte l'explorateur (Choose, croix, cancel..)
		if(var==JFileChooser.APPROVE_OPTION) { // Si il a cliquer sur valider.
		   path = choice.getSelectedFile().getAbsolutePath(); // On recupere le chemin absolu du dossier selectionn�
		}
		
		return path; // On renvoie l'adresse du dossier selectionn� ou un string vide.
	}
	
	public static boolean isGitProject(File path) { // Fonction pour verifier si un dossier correspond bien � un projet git.
		if(path == null || !path.exists()) // Si le dossier n'existe pas, on quitte.
			return false;
		if(!path.isDirectory()) // Si ce n'est pas un dossier, on part.
			return false;
		
		File[] files = path.listFiles(); // On recupere tous les fichiers et dossiers contenus dans le dossier pass� en argurment.
		
		for(File file : path.listFiles()) // On parcourt le tableau.
			if(file.isDirectory() && file.getName().equals(".git")) // Si on trouve un dossier qui a pour nom exacte ".git" alors on renvoie true.
				return true;
		
		return false; // Si on � pas trouv� de dossier ".git".
	}
	
	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private BufferedImage image;
		private JLabel title;
		
	    public ImagePanel(File path, Dimension dimension) {
	    	super();
	    	
	    	title = new JLabel("+ add project"); // On cree le petit texte sous l'image.
	    	//title.setFont(new Font("Verdana", Font.BOLD, 30));
	    	setPreferredSize(dimension); // On met la dimension aux valeurs precis�es en parametres.
	    	setOpaque(false); // La panel est desormais transparent.
	    	
	       try {                
	          image = ImageIO.read(path); // On recupere l'image pass� en parametre.
	       } catch (IOException ex) {
	            ex.printStackTrace();
	            System.exit(1);
	       }

	    }

	    @Override
	    protected void paintComponent(Graphics g) { // Permet de dessiner le panel. On peut donc overrider la methode et dessiner ce qu'on veut par dessus.
	        super.paintComponent(g);
	        int space = title.getHeight()+20;
	        g.drawImage(image, space/2, 0, getWidth()-space, getHeight()-space, null); // On dessine l'image en haut et au milieu horizontalement.
	        g.setFont(title.getFont()); // On change la police d'ecriture en recuperant la police par default du JLabel titre.
	        
	        Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
	        Rectangle2D r = fm.getStringBounds(title.getText(), g2d); // Permet de recuperer la taille en pixels du JLabel titre.
	        int x = (this.getWidth() - (int) r.getWidth()) / 2; // On calcule le x parfait pour mettre le titre au centre.
	        
	        g.drawString(title.getText(), x, getHeight()-title.getHeight()-10); // On dessine le titre au centre et en bas.
	    }

	}
	
}
