package up.visulog.graphs;

import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import up.visulog.analyzer.CountCommitsPerAuthorPlugin;
import up.visulog.config.Configuration;

public class ChartCountCommitsPerAuthor extends ChartAnalysis {
    private Map<String, Integer> data;

    public ChartCountCommitsPerAuthor(Configuration config) {
    	super("Authors", "Commits");
    	var plugin = new CountCommitsPerAuthorPlugin(config);
        this.data = plugin.getResult().getCommitsPerAuthor();
    }
    
    public ChartCountCommitsPerAuthor(Map<String, Integer> data) {
    	super("Authors", "Commits");
    	this.data = data;
    }
    
    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : data.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de commits",line.getKey());
        }
        return dataset;
    }

}