package up.visulog.gui.components;
import java.awt.*;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class TableComponents {

    public static void setFrameAndTable(JFrame frame, String screenTitle, String filePath) throws FontFormatException, IOException {
        setFrame(frame, filePath, 600, 450);
        frame.setTitle(screenTitle);  
        frame.getContentPane().setBackground(new Color(88,205,113));
        JScrollPane scp = createScroolTable();
        frame.add(scp);
    }

    public static JScrollPane createScroolTable() {

        JTable dataTable = new JTable(getTableData(), getTableColNames());
        dataTable.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(dataTable);

        return sp;
    }

    public static void setFrame(JFrame frame, String filePath, int width, int height) {
        ImageIcon img = new ImageIcon(filePath);
        frame.setIconImage(img.getImage());
        frame.setSize(width, height); // 600, 450
        // setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private static String[][] getTableData() {
        // TODO: connect to gitrawdata
        String[][] s ={
            { "Rick", "4031", "main" },
            { "Morty", "6014", "test" }
        };
        return s;
    }

    private static String[] getTableColNames() {
        // TODO: connect to gitrawdata
        String[] s =
            { "Member", "Commits", "Branch" };
        return s;
    }
}
