package up.visulog.cli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Window(int w, int h) {
		super();
		this.setTitle("Visulog");
		this.setMinimumSize(new Dimension(w, h));
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JToolBar bar = new JToolBar();
		
		bar.add(new JButton("save"));
		JButton color = new JButton("color");
		bar.add(color);
		
		color.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				getContentPane().setBackground(Color.RED);
			}
			
		});
		
		this.getContentPane().add(bar, BorderLayout.NORTH);
		this.getContentPane().add(new HomePanel(this), BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	public void changePage(int index)
	{
		this.getContentPane().removeAll();
		switch(index)
		{
		case 1:
			getContentPane().add(new JButton("ahhhh"));
			break;
		}
		revalidate();
		repaint();
	}
	
}
