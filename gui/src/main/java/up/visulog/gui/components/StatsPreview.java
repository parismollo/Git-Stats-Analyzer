package up.visulog.gui.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;

public class StatsPreview extends JEditorPane {
	private static final long serialVersionUID = 1L;

	public static String pageBegin = "<html lang=\"fr\"><head>\t<meta charset=\"utf-8\"/>"
									 + "<head></head>",
						 pageEnd = "</html>";
	private static File tmpFile;
	
	private String content;
	
	public StatsPreview(String content) throws IOException {
		this.content = content;
		this.setEditable(false);
		this.setEditorKit(new HTMLEditorKit());
		if(tmpFile == null) {
			tmpFile = File.createTempFile("stats", ".tmp");
			tmpFile.deleteOnExit();
		}
		FileWriter fW = new FileWriter(tmpFile);
		fW.write(pageBegin+content+pageEnd);
		fW.close();
		setPage(tmpFile.toURI().toURL());
		System.out.println(tmpFile.toURI().toURL());
	}
	
}
