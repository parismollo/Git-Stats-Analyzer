package up.visulog.gitrawdata;

public class CommitChangesBuilder{
    
    private int addedLines, deletedLines;
    private String date;
    private String author;

    public CommitChangesBuilder(){ }

    public CommitChangesBuilder(String author, String date, int addedLines, int deletedLines) {
        this.author = author;
        this.date = date;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }



    public CommitChangesBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public CommitChangesBuilder setDate(String date) {
        this.date = date;
        return this;
    }    

    public CommitChangesBuilder setAddedLines(int addedLines) {
        this.addedLines = addedLines;
        return this;
    }

    public CommitChangesBuilder setDeletedLines(int deletedLines) {
        this.deletedLines = deletedLines;
        return this;
    }

    public CommitChanges createCommitChanges() {
        return new CommitChanges(author, date, addedLines, deletedLines);
    }



}
