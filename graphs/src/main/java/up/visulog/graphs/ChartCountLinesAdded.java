package up.visulog.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import up.visulog.analyzer.CountLinesChangedPlugin;
import up.visulog.config.Configuration;

public class ChartCountLinesAdded extends ChartAnalysis {
    private Map<String, Integer> data;

    public ChartCountLinesAdded(Configuration config) {
    	super("Fichiers", "Lignes ajoutees");
    	var plugin = new CountLinesChangedPlugin(config);
    	Map<String, int[]> temp = plugin.getResult().getLinesChangedPerFile();
    	this.data = new HashMap<>();
    	int[] lines;
    	for(var file : temp.entrySet()) {
    		lines = file.getValue();
    		if(lines[0] != 0)
    			data.put(file.getKey(), lines[0]); // lines[0] correspond aux nombre de lignes ajoutées.
    	}
    }
    
    public ChartCountLinesAdded(Map<String, Integer> data) {
    	super("Files", "Lignes ajoutees");
    	this.data = data;
    	if(this.data == null)
    		this.data = new HashMap<>();
    }
    
    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : data.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de lignes ajoutees par fichier",line.getKey());
        }
        return dataset;
    }

}