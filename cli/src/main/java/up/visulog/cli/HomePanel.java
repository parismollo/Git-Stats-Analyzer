package up.visulog.cli;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class HomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel welcomeTitle;
	private ImagePanel chooseFolder;
	private Window win;
	
	public HomePanel(Window win) {
		super();
		this.win = win;
		
		welcomeTitle = new JLabel("Welcome to Visulog !");
		welcomeTitle.setFont(new Font(welcomeTitle.getFont().getFontName(), Font.BOLD, 24));
		welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel bottomContainer = new JPanel();
		
		File folderImg = new File("src/main/resources/folder.png");
		
		chooseFolder = new ImagePanel(folderImg, new Dimension(110, 100));
		chooseFolder.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				File folder = new File(getFolderPath(HomePanel.this));
				if(!isGitProject(folder))
					return;
				bottomContainer.remove(chooseFolder);
				bottomContainer.add(new JLabel("Your project folder is : "+folder.getAbsolutePath()));
				revalidate();
				repaint();
				win.changePage(1);
			}
			
		});
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		bottomContainer.setLayout(new GridBagLayout()); // To center items in the panel
		bottomContainer.add(chooseFolder);
		
		this.add(welcomeTitle);
		this.add(bottomContainer);
	}
	
	public static String getFolderPath(Component comp) {
		JFileChooser choice = new JFileChooser();
		choice.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String path = "";
		int var = choice.showSaveDialog(comp);
		if(var==JFileChooser.APPROVE_OPTION) {
		   path = choice.getSelectedFile().getAbsolutePath();
		}
		
		return path;
	}
	
	public static boolean isGitProject(File path) {
		if(path == null || !path.exists())
			return false;
		
		for(File file : path.listFiles())
			if(file.isDirectory() && file.getName().equals(".git"))
				return true;
		
		return false;
	}
	
	private class ImagePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private BufferedImage image;
		private JLabel title;
		
	    public ImagePanel(File path, Dimension dimension) {
	    	super();
	    	
	    	title = new JLabel("+ add project");
	    	//title.setFont(new Font("Verdana", Font.BOLD, 30));
	    	setPreferredSize(dimension);
	    	setOpaque(false);
	    	
	       try {                
	          image = ImageIO.read(path);
	       } catch (IOException ex) {
	            ex.printStackTrace();
	            System.exit(1);
	       }

	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        int space = title.getHeight()+20;
	        g.drawImage(image, space/2, 0, getWidth()-space, getHeight()-space, null);
	        g.setFont(title.getFont());
	        
	        Graphics2D g2d = (Graphics2D) g;
	        FontMetrics fm = g2d.getFontMetrics();
	        Rectangle2D r = fm.getStringBounds(title.getText(), g2d);
	        int x = (this.getWidth() - (int) r.getWidth()) / 2; // The perfect x value to center the title.
	        
	        g.drawString(title.getText(), x, getHeight()-title.getHeight()-10);
	    }

	}
	
}
