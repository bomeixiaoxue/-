package frameUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;

public class Time {
	//设置入店,离店的下拉列表时间
		public static void setInOutTime(JComboBox<String> inComboBox,JComboBox<String> outComboBox){
			Calendar calendar = null;
			SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd 13:30:00");
			SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd 12:30:00");
			calendar = Calendar.getInstance();
			inComboBox.addItem(sdfIn.format(calendar.getTime()));
			for(int i = 1; i < 30; i++){
				calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, i);
				inComboBox.addItem(sdfIn.format(calendar.getTime()));
				outComboBox.addItem(sdfOut.format(calendar.getTime()));
			}
			calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 30);
			outComboBox.addItem(sdfOut.format(calendar.getTime()));
		}
}
