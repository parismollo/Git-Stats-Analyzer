package up.visulog.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import up.visulog.analyzer.CountCommitsPerWeekdayPlugin;
import up.visulog.config.Configuration;

public class ChartCountCommitsPerWeekday extends ChartAnalysis {
    private Map<String, Integer> data;

    public ChartCountCommitsPerWeekday(Configuration config) {
    	super("Weekdays", "Commits");
    	var plugin = new CountCommitsPerWeekdayPlugin(config);
        this.data = plugin.getResult().getCommitsPerDate(); // Toujours different de null.
    }
    
    public ChartCountCommitsPerWeekday(Map<String, Integer> data) {
    	super("Weekdays", "Commits");
    	this.data = data;
    	if(this.data == null)
    		this.data = new HashMap<>();
    }

    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : data.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de commits",line.getKey());
        }
        return dataset;
    }
    
}
