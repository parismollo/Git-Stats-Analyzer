package up.visulog.gitrawdata;

import java.nio.file.Path;

public class LinesChangedBuilder {
    private Path path;
    private int addedLines, deletedLines;

    public LinesChangedBuilder() {}
    
    public LinesChangedBuilder(Path path, int addedLines, int deletedLines) {
        this.path = path;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }

    public LinesChangedBuilder setPath(Path path) {
        this.path = path;
        return this;
    }

    public LinesChangedBuilder setAddedLines(int addedLines) {
        this.addedLines = addedLines;
        return this;
    }

    public LinesChangedBuilder setDeletedLines(int deletedLines) {
        this.deletedLines = deletedLines;
        return this;
    }

    public LinesChanged createLinesChanged() {
        return new LinesChanged(path, addedLines, deletedLines);
    }	
}
