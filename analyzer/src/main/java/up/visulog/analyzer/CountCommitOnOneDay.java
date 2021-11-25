package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gitrawdata.CommitOnOneDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitOnOneDay extends CountCommitsBetweenDays {
	
	public CountCommitOnOneDay(Configuration generalConfiguration, String date) {
		super(generalConfiguration, date, date);
	}

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath(), startDate));
    }
	
}