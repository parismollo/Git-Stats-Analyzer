package up.visulog.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import up.visulog.analyzer.CountLinesChangedPlugin;
import up.visulog.config.Configuration;

public class ChartCountLinesDeleted extends ChartAnalysis {
    private Map<String, Integer> data;

    public ChartCountLinesDeleted(Configuration config) {
    	super("Fichiers", "Lignes supprimées");
    	var plugin = new CountLinesChangedPlugin(config);
    	Map<String, int[]> temp = plugin.getResult().getLinesChangedPerFile();
    	this.data = new HashMap<>();
    	int[] lines;
    	for(var file : temp.entrySet()) {
    		lines = file.getValue();
    		if(lines[1] != 0)
    			data.put(file.getKey(), lines[1]); // lines[1] correspond aux nombre de lignes suprimées.
    	}
    }
    
    public ChartCountLinesDeleted(Map<String, Integer> data) {
    	super("Files", "Lignes supprimées");
    	this.data = data;
    	if(this.data == null)
    		this.data = new HashMap<>();
    }
    
    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : data.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de lignes suprimées par fichier",line.getKey());
        }
        return dataset;
    }

}
