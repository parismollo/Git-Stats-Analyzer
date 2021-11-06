package up.visulog.graphs;

import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartCountCommitsPerAuthor implements ChartAnalysis {
    Map<String, Integer> data;

    public ChartCountCommitsPerAuthor(Map<String, Integer> data) {
        this.data = data;
    }
    
    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : data.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de commits",line.getKey());
        }
        return dataset;
    }

    public JFreeChart createChart(CategoryDataset dataset, String type) {
        JFreeChart chart = null;
        switch(type) {
            case "line" :
                chart = ChartFactory.createLineChart("Number of commits per author","Author","Number of commits",
                        dataset,PlotOrientation.VERTICAL,true, true, false); break;
                // (titre graphique, nom axe abscisse, nom axe ordonnées, les données, orientation, legende, , url)
            case "bar" :
                chart = ChartFactory.createBarChart("Number of commits per author","Author","Number of commits",
                        dataset,PlotOrientation.VERTICAL,true, true, false); break;
            case "pie" :
                CategoryToPieDataset tmp = new CategoryToPieDataset(dataset,TableOrder.BY_ROW, 0);
                chart = ChartFactory.createPieChart("Number of commits per author", tmp); break;
            default :
                // FIXME: mettre une exception à la place ou autre chose
                System.out.println("Unknown type of chart");
                System.exit(1);
        }
        return chart;
    }  
}