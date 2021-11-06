package up.visulog.graphs;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

public interface ChartAnalysis {
    
    public CategoryDataset createDataset();

    public JFreeChart createChart(CategoryDataset dataset, String type);
}