package up.visulog.graphs;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

public class PrintChart extends JFrame {
    // fichier pour temporairement afficher les graphs
    ChartAnalysis rawChart;
    String type;

    public PrintChart(ChartAnalysis rawChart, String type) {
        this.rawChart = rawChart;
        this.type = type;
    }

    public void afficheChart() {
        CategoryDataset dataset = rawChart.createDataset();

        JFreeChart chart = rawChart.createChart(dataset, type); //Donnée du graphiques
        ChartPanel chartPanel = new ChartPanel(chart); //Stocker dans une variable temporaire
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); //Bordure entre le graphe et les contours
        chartPanel.setBackground(Color.white); //Fond blanc hors du graphique

        chart.setBackgroundPaint(Color.white); // changement du fond du graphe en noire
        add(chartPanel); // ajouter les informations donnée auparavant 

        pack(); // Permet de redimentionner automatiquelent la taille de la fenetre qui va s'ouvrir
        setTitle("Graph display"); //Titre de la fenetre 
        setLocationRelativeTo(null); // permet de recentrer la fenetre au milieu de l'écran
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arret du programme lors de la fermeture de la fenetre
    }
}