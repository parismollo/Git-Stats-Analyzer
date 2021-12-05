package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.*;

public class CountCommitLinesChangedOnOneDay extends CountCommitsBetweenDays {
	
	public CountCommitLinesChangedOnOneDay(Configuration generalConfiguration, String date) {
		super(generalConfiguration, date, date);
	}

    @Override
    public void run() {
        result = processLog(CommitLinesChanged.parseLogFromCommand(configuration.getGitPath(), startDate));
    }
	
}