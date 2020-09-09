package up.visulog.gitrawdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Commit {
    // FIXME: (some of) these fields could have more specialized types than String
    public final String id;
    public final String date;
    public final String author;
    public final String description;

    public Commit(String id, String author, String date, String description) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    // TODO: factor this out (similar code will have to be used for all git commands)
    public static List<Commit> parseLogFromCommand(Path gitPath) {
        ProcessBuilder builder =
                new ProcessBuilder("git", "log").directory(gitPath.toFile());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Error running \"git log\".", e);
        }
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parseLog(reader);
    }

    public static List<Commit> parseLog(BufferedReader reader) {
        var result = new ArrayList<Commit>();
        Optional<Commit> commit = parseCommit(reader);
        while (commit.isPresent()) {
            result.add(commit.get());
            commit = parseCommit(reader);
        }
        return result;
    }

    /**
     * Parses a log item and outputs a commit object. Exceptions will be thrown in case the input does not have the proper format.
     * Returns an empty optional if there is nothing to parse anymore.
     */
    public static Optional<Commit> parseCommit(BufferedReader input) {
        try {
            var idLine = input.readLine();
            if (idLine == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = idLine.split(" ");
            if (!idChunks[0].equals("commit")) parseError();
            var id = idChunks[1];

            var authorLine = input.readLine();
            if (!authorLine.startsWith("Author: ")) parseError();
            var author = authorLine.substring(8);

            var dateLine = input.readLine();
            if (!dateLine.startsWith("Date: ")) parseError();
            var date = dateLine.substring(6);

            var emptyLine = input.readLine();
            if (!emptyLine.isEmpty()) parseError();

            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            return Optional.of(new Commit(id, author, date, description));
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }

    // Helper function for generating parsing exceptions. This function *always* quits on an exception. It *never* returns.
    private static void parseError() {
        throw new RuntimeException("Wrong commit format.");
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
