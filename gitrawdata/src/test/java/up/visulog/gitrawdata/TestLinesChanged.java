package up.visulog.gitrawdata;

//import java.util.ArrayList;
//import java.util.List;

import org.junit.Test;

public class TestLinesChanged {

    @Test
    public void testLinesChanged() {
    	/* Test pas encore terminer
    	List<LinesChanged> list = new ArrayList<LinesChanged>();
    	int[] addedLines = {4, 8, 10};
    	int[] deletedLines = {2, 12, 5};
    	String[] files = new String[] {
    			"fichier1",
    			"fichier2",
    			"fichier3"
    	};
    	String lines = "";
    	for(int i=0;i<addedLines.length;i++) {
    		lines += buildLine(addedLines[i], deletedLines[i], files[i]);
    		if(i < addedLines.length-1)
    			lines += "\n";
    	}*/
    	
    	//var linesChanged = LinesChanged.par
    	
    }
    
	public String buildLine(int addedLines, int deletedLines, String path) {
		return addedLines+"\t"+deletedLines+"\t"+path;
	}
    
	
}
