package up.visulog.graphs;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CategoryToPieDataset;

public abstract class ChartAnalysis {
    
	private String titleX, titleY;
	
	public ChartAnalysis(String titleX, String titleY) {
		this.titleX = titleX;
		this.titleY = titleY;
	}
	
    public abstract CategoryDataset createDataset();

    public HashMap<String, Integer> copy(Map<String, Integer> map) {
    	HashMap<String, Integer> map2 = new HashMap<>();
    	map2.putAll(map);
    	return map2;
    }
    
    public static JFreeChart createChart(CategoryDataset dataset, String type,
    									 String title, String titleX, String titleY) {
        JFreeChart chart = null;
        switch(type) {
            case "line" :
                chart = ChartFactory.createLineChart(title, titleX, titleY,
                        dataset,PlotOrientation.VERTICAL, false, true, false);
                break;
                // (titre graphique, nom axe abscisse, nom axe ordonnées, les données, orientation, legende, , url)
            case "bar" :
                chart = ChartFactory.createBarChart(title, titleX, titleY,
                        dataset,PlotOrientation.VERTICAL, false, true, false);
                break;
            case "pie" :
                CategoryToPieDataset tmp = new CategoryToPieDataset(dataset,TableOrder.BY_ROW, 0);
                chart = ChartFactory.createPieChart("", tmp, false, true, false);
                break;
            default :
                System.out.println("Unknown type of chart");
                System.exit(1);
        }
        return chart;
    }
    
    public static String[] getGraphTypes() {
    	return new String[] {"bar", "line", "pie"};
    }
    
    public JFreeChart createChart(String type) {
    	return createChart(createDataset(), type, "", titleX, titleY);
    }
    
    public ChartPanel createPanel(String type) {
        JFreeChart chart = this.createChart(type); // Donnees du graphique
        ChartPanel chartPanel = new ChartPanel(chart); // Stocker dans une variable temporaire
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Bordure entre le graphe et les contours
        chartPanel.setBackground(Color.white); // Fond blanc hors du graphique
        return chartPanel;
    }
    
}