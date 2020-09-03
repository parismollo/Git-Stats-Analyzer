package up.visulog.gitrawdata;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import java.io.IOException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.AnyObjectId;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class Commit {
    // AD: FIXME: (some of) these fields could have more specialized types than String
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
     * Transforms a time encoded as long into a string with
     * the git log format.
     */
    static String stringOfTime(long time, TimeZone tz) {
	var dtfmt =
	    new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
	dtfmt.setTimeZone(tz);
	dtfmt.format(Long.valueOf(time));
	return dtfmt.format(Long.valueOf(time));
    }

    /**
     * Transform a JGit revCommit into a regular Commit object.
     */
    public static Commit commitOfRevCommit (AnyObjectId id, RevCommit rCommit){
	var  author = rCommit.getAuthorIdent();
	var name = author.getName();
	var email = author.getEmailAddress();
	var time = author.getWhen().getTime();
	var timeZone = author.getTimeZone();
	var commit =
	    new Commit(id.getName(),
		       name + " <" + email+">",
		       stringOfTime(time, timeZone),
		       rCommit.getFullMessage());
	return commit;
    }


    /**
     * Parses a log item and outputs a commit object. Exceptions will
     * be thrown in case the input does not have the proper format.
     */
    public static Commit parse (Repository repo, AnyObjectId id)
	throws MissingObjectException,
	       IncorrectObjectTypeException,
	       IOException {
	try (RevWalk walk = new RevWalk(repo)) {
	    RevCommit rCommit = walk.parseCommit(id);
	    walk.dispose();
	    return commitOfRevCommit(id, rCommit);
	}
    }
}
