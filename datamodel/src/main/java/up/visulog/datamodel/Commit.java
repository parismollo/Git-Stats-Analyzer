package up.visulog.datamodel;

import java.io.BufferedReader;
import java.io.IOException;

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

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Parses a log item and outputs a commit object. Exceptions will be thrown in case the input does not have the proper format.
     */
    public static Commit parse(BufferedReader input) {
        try {
            var idLine = input.readLine();
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
            if (!emptyLine.isBlank()) parseError();

            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isBlank()) // take all lines until the first blank one (commits are separated by blank lines)
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            return new Commit(id, author, date, description);
        } catch (IOException e) {
            parseError();
            return null; // this is supposed to be unreachable, as parseError should never return
        }
    }

    // Helper function for generating parsing exceptions. This function *always* quits on an exception. It *never* returns.
    private static void parseError() {
        throw new RuntimeException("Wrong commit format.");
    }
}
