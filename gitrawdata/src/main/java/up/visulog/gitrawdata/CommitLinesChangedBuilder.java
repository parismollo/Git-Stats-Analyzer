package up.visulog.gitrawdata;

import java.util.ArrayList;

public class CommitLinesChangedBuilder {
    private final String id;
    private String author;
    private String date;
    private String description;
    private String mergedFrom;
    private ArrayList<LinesChanged> LinesChangedlist = new ArrayList<LinesChanged>();

    public CommitLinesChangedBuilder(String id) {
        this.id = id;
    }

    public CommitLinesChangedBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public CommitLinesChangedBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public CommitLinesChangedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommitLinesChangedBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    public CommitLinesChangedBuilder setArrayList(ArrayList<LinesChanged> LinesChangedlist) {
        this.LinesChangedlist = LinesChangedlist;
        return this;
    }

    public CommitLinesChanged createCommitLinesChanged() {
        return new CommitLinesChanged(id, author, date, description, mergedFrom,LinesChangedlist);
    }
}