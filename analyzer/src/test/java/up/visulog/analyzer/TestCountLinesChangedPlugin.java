package up.visulog.analyzer;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import up.visulog.gitrawdata.LinesChanged;
import up.visulog.gitrawdata.LinesChangedBuilder;

public class TestCountLinesChangedPlugin {

    @Test
    public void checkCountLinesChangedPlugin() throws Exception {
    	List<LinesChanged> list = new ArrayList<LinesChanged>();
    	int[] addedLines = {4, 8, 10};
    	int[] deletedLines = {2, 12, 5};
    	URI[] files = new URI[] {
    			getClass().getClassLoader().getResource("fichier1.txt").toURI(),
    			getClass().getClassLoader().getResource("fichier2.txt").toURI(),
    			getClass().getClassLoader().getResource("fichier3.txt").toURI(),
    	};
    	
    	for(int i=0;i<addedLines.length;i++) {
    		list.add(new LinesChangedBuilder().setAddedLines(addedLines[i])
    										  .setDeletedLines(deletedLines[i])
    										  .setPath(Paths.get(files[i]))
    										  .createLinesChanged());
    	}
    	
    	var linesChanged = CountLinesChangedPlugin.processLog(list);
    	var results = linesChanged.getLinesChangedPerFile();
    	for(var res : results.entrySet()) {
    		int index = getIndexOf(res.getKey())-1; // Pour "fichier1.txt" on a 1-1 = 0
    		int[] values = res.getValue();
    		assertEquals(addedLines[index], values[0]);
    		assertEquals(deletedLines[index], values[1]);
    		assertEquals("fichier"+(index+1)+".txt", res.getKey());
    	}
    }
    
    public int getIndexOf(String str) { // Pour "fichier1.txt", on recupère 1.
    	return str.charAt(str.indexOf('.')-1) - '0';
    }
    
}
