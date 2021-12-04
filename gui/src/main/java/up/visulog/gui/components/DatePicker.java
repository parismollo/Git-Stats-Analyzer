package up.visulog.gui.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class DatePicker extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private JComboBox<String> days, months, years;
	
	public DatePicker() {
		days = new JComboBox<String>(getStringList(1, 31));
		months = new JComboBox<String>(getStringList(1, 12));
		
		String[] dateInfos = getDateInfos(new Date());
		int actualYear = Integer.parseInt(dateInfos[0]);
		String[] yearsTab = getStringList(1970, actualYear);
		years = new JComboBox<String>(yearsTab);
		
		years.setSelectedIndex(yearsTab.length-1);
		months.setSelectedIndex(Integer.parseInt(dateInfos[1])-1);
		days.setSelectedIndex(Integer.parseInt(dateInfos[2])-1);
		
		setOpaque(false);
		add(years);
		add(months);
		add(days);
	}
		
	
	public static String[] getStringList(int start, int end) {
		if(start > end)
			return new String[0];
		String[] tab = new String[end-start+1];
		for(int i=0;i<end-start+1;i++) {
			String str = start+i+"";
			if(str.length() == 1)
				str = "0"+str;
			tab[i] = str;
		}
		return tab;
	}
	
	public static String[] getDateInfos(Date date) {
		SimpleDateFormat day = new SimpleDateFormat("dd"),
						 month = new SimpleDateFormat("MM"),
						 year = new SimpleDateFormat("yyyy");
		return new String[] {year.format(date), month.format(date),
							 day.format(date)};
	}
	
	public String getDate() {
		return years.getSelectedItem()+"-"+
			   months.getSelectedItem()+"-"+
			   days.getSelectedItem();
	}
	
	public static Date getDate(Date date, int nb) {
		return new Date(date.getTime()+nb*24*60*60*1000);
	}
	
    public static String getStringDate(int nb) { // Donne le string de la date situee nb jours avant/apres aujourd'hui
    	Date d = new Date();
		int add = (nb > 0 ? -1 : 1);
        while(nb != 0) {
            d = DatePicker.getDate(d, -add);
            nb += add;
        }
        return defaultFormat.format(d);
    }
	
}
