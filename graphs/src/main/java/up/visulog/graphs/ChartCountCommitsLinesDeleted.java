package up.visulog.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import up.visulog.analyzer.CountCommitLinesChanged;
import up.visulog.analyzer.CountCommitLinesChangedBetweenDays;
import up.visulog.config.Configuration;

public class ChartCountCommitsLinesDeleted extends ChartAnalysis {
    private Map<String, Integer> data;
    private HashMap<String, Integer> modifiedData;

    public ChartCountCommitsLinesDeleted(Configuration config) {
    	super("Auteurs", "Deleted lines");
    	var plugin = new CountCommitLinesChanged(config);
    	var tmp = plugin.getResult().getLinesChangedPerAuthor();
        this.data = new HashMap<>(); // Toujours different de null.
        for(var elt : tmp.entrySet())
        	data.put(elt.getKey(), elt.getValue()[1]);
        this.modifiedData = copy(data);
        keepOnlyFirstName(); // On garde que le premier nom de chaque auteur.
    }
    
    public ChartCountCommitsLinesDeleted(Configuration config, String startDate, String endDate) {
    	super("Auteurs", "Deleted lines");
    	var plugin = new CountCommitLinesChangedBetweenDays(config, startDate, endDate);
    	var tmp = plugin.getResult().getLinesChangedPerAuthor();
        this.data = new HashMap<>(); // Toujours different de null.
        for(var elt : tmp.entrySet())
        	data.put(elt.getKey(), elt.getValue()[1]);
        this.modifiedData = copy(data);
        keepOnlyFirstName(); // On garde que le premier nom de chaque auteur.
    }
    
    public ChartCountCommitsLinesDeleted(Configuration config, String date) {
    	this(config, date, date);
    }

	// si deux auteurs ont le même premier nom, on ajoute un chiffre pour les différencier
	private String getNewName(HashMap<String, Integer> map, String name) {
		if(map.containsKey(name)) {
			int i = 2;
			while(map.containsKey(name+"("+i+")")) {
				i++;
			}
			name = name+"("+i+")";
		}
		return name;
	}
    
    public void keepOnlyFirstName() {
    	keepOnlyFirstName(modifiedData);
    }
    
    public void keepOnlyFirstName(HashMap<String, Integer> map) {
        Scanner scan;
		String nName = "";
		map.clear();
        for(var elt : data.entrySet()) {
        	scan = new Scanner(elt.getKey());
        	if(scan.hasNext()) {
				nName = getNewName(map, scan.next());
	        	map.put(nName, elt.getValue()); // On garde que le premier nom de l'auteur.
        	}
        	scan.close();
        }
    }
    
    public List<String> getAuthors(boolean realName) {
    	HashMap<String, Integer> tmpMap = copy(data);
    	if(!realName)
    		keepOnlyFirstName(tmpMap);
    	
    	List<String> list = new ArrayList<String>();
    	for(var elt : tmpMap.entrySet()) {
    		list.add(elt.getKey());
    	}
    	return list;
    }
    
    public void refreshAuthors(List<String> authors) {
    	if(authors == null || authors.size() == 0)
    		return;
    	HashMap<String, Integer> dataCopy = copy(data);
    	keepOnlyFirstName(dataCopy);
    	HashMap<String, Integer> tempData = new HashMap<>();
    	for(String author : authors) {
    		if(dataCopy.containsKey(author)) {
    			tempData.put(author, dataCopy.get(author));
    		}
    	}
    	//if(tempData.size() != 0) // Si aucun des auteurs cites n'existent alors on laisse la liste comme elle est.
    	modifiedData = tempData;
    }
    
    public CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Entry<String, Integer> line : modifiedData.entrySet()) {
            dataset.addValue(line.getValue(),"Nombre de commits",line.getKey());
        }
        return dataset;
    }

}