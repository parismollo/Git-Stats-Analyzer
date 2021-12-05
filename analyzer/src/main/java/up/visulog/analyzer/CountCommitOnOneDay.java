package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.*;

public class CountCommitOnOneDay extends CountCommitsBetweenDays {
	
	public CountCommitOnOneDay(Configuration generalConfiguration, String date) {
		super(generalConfiguration, date, date);
	}

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath(), startDate));
    }
	
}