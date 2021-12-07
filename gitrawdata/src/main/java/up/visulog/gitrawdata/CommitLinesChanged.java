package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommitLinesChanged extends Commit {

    private ArrayList<LinesChanged> LinesChangedlist = new ArrayList<LinesChanged>();

    public CommitLinesChanged(String id, String author, String date, String description, String mergedFrom, 
                            ArrayList<LinesChanged> LinesChangedlist) {
        super(id, author, date, description, mergedFrom);
        this.LinesChangedlist = LinesChangedlist;
    }

    public static List<CommitLinesChanged> parseLogFromCommandLinesChanged(Path gitPath) {
        ProcessBuilder builder =
                new ProcessBuilder("git", "log", "--numstat").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git log --numstat\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLogLinesChanged(reader);
    }

    public static List<CommitLinesChanged> parseLogFromCommandLinesChanged(Path gitPath, String startDate, String endDate) {
        ProcessBuilder builder =
                new ProcessBuilder("git", "log", "--numstat","--after='"+startDate+"'", "--before='"+endDate+"'").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git log --numstat\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLogLinesChanged(reader);
    }

    public static List<CommitLinesChanged> parseLogFromCommandLinesChanged(Path gitPath, String day) {
    	String d1 = day+" 00:00",
    		   d2 = day+" 23:59";
    	return parseLogFromCommandLinesChanged(gitPath, d1, d2);
    }

    public static List<CommitLinesChanged> parseLogLinesChanged(BufferedReader reader) {
        var result = new ArrayList<CommitLinesChanged>();
        Optional<CommitLinesChanged> CommitLinesChanged = parseCommitLinesChanged(reader);
        while (CommitLinesChanged.isPresent()) {
            result.add(CommitLinesChanged.get());
            CommitLinesChanged = parseCommitLinesChanged(reader);
        }
        return result;
    }

    public static Optional<CommitLinesChanged> parseCommitLinesChanged(BufferedReader input) {
        try {
        	
            var line = input.readLine();
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = line.split(" ");
            if (!idChunks[0].equals("commit")) parseError();
            var builder = new CommitLinesChangedBuilder(idChunks[1]);
            boolean merge = false;
            
            line = input.readLine();
            while (!line.isEmpty()) {
                var colonPos = line.indexOf(":");
                var fieldName = line.substring(0, colonPos);
                var fieldContent = line.substring(colonPos + 1).trim();
                switch (fieldName) {
                    case "Author":
                        builder.setAuthor(fieldContent);
                        break;
                    case "Merge":
                        builder.setMergedFrom(fieldContent);
                        merge = true;
                        break;
                    case "Date":
                        builder.setDate(fieldContent);
                        break;
                    default: // TODO: warn the user that some field was ignored
                }
                line = input.readLine(); //prepare next iteration
                if (line == null) parseError(); // end of stream is not supposed to happen now (commit data incomplete)
            }

            // now read the commit message per se
            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            builder.setDescription(description); 

            if(merge)
            	return Optional.of(builder.createCommitLinesChanged());
            
           
            var list = new ArrayList<LinesChanged>();
            Optional<LinesChanged> linesChanged = Optional.empty();
            do {
            	try {
            		linesChanged = LinesChanged.parseLinesChanged(input);
            	} catch(Exception e) {
            		linesChanged = Optional.empty();
            	}

            	if(linesChanged.isPresent())
            		list.add(linesChanged.get());
            	
            } while(linesChanged.isPresent());
            
            builder.setArrayList(list);
            
            return Optional.of(builder.createCommitLinesChanged());
        } catch (Exception e) {
        	e.printStackTrace();
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }
    
    // Helper function for generating parsing exceptions. This function *always* quits on an exception. It *never* returns.
    private static void parseError() {
        throw new RuntimeException("Wrong --numstat format.");
    }

    public ArrayList<LinesChanged> getList() {
        return this.LinesChangedlist;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                (mergedFrom != null ? ("mergedFrom...='" + mergedFrom + '\'') : "") + //TODO: find out if this is the only optional field
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", lines changed='" + Arrays.toString(LinesChangedlist.toArray())+
                '}';
    }
}
