package ozden.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateWrapper {
	private LocalDateTime localDateTime;
	private Date date;
	
	public DateWrapper(){
		
	}
	
	public static Date getDate(LocalDateTime dt){
		Calendar inst = Calendar.getInstance();
		inst.set(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), dt.getMinute(), dt.getSecond());
		return inst.getTime();
	}
	
	public static LocalDateTime getDate(Date d){
		return LocalDateTime.of(d.getYear(), d.getMonth(), d.getDay(), d.getHours(), d.getMinutes());	
	}
}
