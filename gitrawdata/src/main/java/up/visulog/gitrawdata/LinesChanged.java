package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinesChanged {
    private Path path;
    private int addedLines, deletedLines;
    
    public LinesChanged(Path path, int addedLines, int deletedLines) {
        this.path = path;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }
    
    public static List<LinesChanged> parseLogFromCommand(Path gitPath) {
        ProcessBuilder builder =
                new ProcessBuilder("git", "diff", "--numstat").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git diff --numstat\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLog(reader);
    }

    public static List<LinesChanged> parseLog(BufferedReader reader) {
        var result = new ArrayList<LinesChanged>();
        Optional<LinesChanged> linesChanged = parseLinesChanged(reader);
        while (linesChanged.isPresent()) {
            result.add(linesChanged.get());
            linesChanged = parseLinesChanged(reader);
        }
        return result;
    }
    
    public static Optional<LinesChanged> parseLinesChanged(BufferedReader input) {
        try {

            String line = input.readLine();
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer

            String[] infos = line.split("\t");
            if(infos == null || infos.length != 3)
            	parseError();
            
            LinesChangedBuilder builder = new LinesChangedBuilder();

            builder.setAddedLines(getNumber(infos[0]));
            builder.setDeletedLines(getNumber(infos[1]));

            if(infos[2].charAt(0) == '{') {
	            try {
		            int index = infos[2].indexOf(" => ")+4;
		            String pathStr = infos[2].substring(index);
		            int badChar = pathStr.indexOf('}');
		            
		            pathStr = pathStr.substring(0, badChar)+pathStr.substring(badChar+1);
	            	builder.setPath(FileSystems.getDefault().getPath(pathStr));
	            } catch(Exception e) {}
        	}
            return Optional.of(builder.createLinesChanged());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }
    
    public static int getNumber(String nb) {
    	int n = 0;
    	try {
    		n = Integer.parseInt(nb);
    	} catch(Exception e) {}
    	
    	return n;
    }
    
    private static void parseError() {
        throw new RuntimeException("Wrong diff format.");
    }

    public Path getPath() {
    	return path;
    }
    
    public int getAddedLines() {
    	return addedLines;
    }
    
    public int getDeletedLines() {
    	return deletedLines;
    }
    
    @Override
    public String toString() {
        return "CharDiff{" +
        		"File='"+path.toAbsolutePath()+"',"+
        		"addedLines='"+addedLines+"',"+
        		"deletedLines='"+deletedLines+"'"+
                "}";
    }
    
}
