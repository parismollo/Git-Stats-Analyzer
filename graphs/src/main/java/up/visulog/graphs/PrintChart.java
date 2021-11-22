package up.visulog.graphs;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

public class PrintChart extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// fichier pour temporairement afficher les graphs
    private ChartAnalysis rawChart;
    private String type;

    public PrintChart(ChartAnalysis rawChart, String type) {
        this.rawChart = rawChart;
        this.type = type;
    }

    public void afficheChart() {
    	ChartPanel chartPanel = rawChart.createPanel(type);

        add(chartPanel); // ajouter les informations donnée auparavant 
        setMinimumSize(new Dimension(600, 600)); // On set la taille minimum de la fenetre.
        setTitle("Graph display"); //Titre de la fenetre 
        setLocationRelativeTo(null); // permet de recentrer la fenetre au milieu de l'écran
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Arret du programme lors de la fermeture de la fenetre
        setVisible(true); // On affiche la fenetre;
    }
}