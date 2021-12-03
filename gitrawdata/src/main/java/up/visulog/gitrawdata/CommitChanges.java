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

public class CommitChanges {

    private String author;
    private String date;
    private int addedLines;
    private int deletedLines;

    public CommitChanges(String author, String date, int addedLines, int deletedLines) {
        this.author = author;
        this.date = date;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }

    public static List<CommitChanges> parseLogFromCommand(Path gitPath) {
        ProcessBuilder builder =
                new ProcessBuilder("git", "log", "--numstat").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git log--numstat\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLog(reader);
    }

    public static List<CommitChanges> parseLog(BufferedReader reader) {
        var result = new ArrayList<CommitChanges>();
        Optional<CommitChanges> commitChanged = parseCommitLinesChanges(reader);
        while (commitChanged.isPresent()) {
            result.add(commitChanged.get());
            commitChanged = parseCommitLinesChanges(reader);
        }
        return result;
    }

    public static Optional<CommitChanges> parseCommitLinesChanges(BufferedReader input) {
        try {
            String line = input.readLine();
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            int[] lines = new int[2];//contains addedLines and deletedLines
            String[] infos = line.split("\t");
            if(infos == null || infos.length != 3)
            	parseError();
            
            CommitChangesBuilder builder = new CommitChangesBuilder();
            builder.setAddedLines(Integer.parseInt(infos[0]));
            builder.setDeletedLines(Integer.parseInt(infos[1]));
            
            return Optional.of(builder.createCommitChanges());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }
    
    private static void parseError() {
        throw new RuntimeException("Wrong diff format.");
    }

    public int getAddedLines() {
    	return addedLines;
    }
    
    public int getDeletedLines() {
    	return deletedLines;
    }
    
    @Override
    public String toString() {
        return "CommitLineChanges{" +
        		"Nom='"+author+"',"+
        		"addedLines='"+addedLines+"',"+
        		"deletedLines='"+deletedLines+"'"+
                "}";
    }


    
}
