package st.ict.hrs.hrauto.educationfacility;

import java.sql.Date;
import java.util.Calendar;

public class UtilJsonDate 
{
	private int year;
	private int month;
	private int day;
	
	public UtilJsonDate()
	{	}

	public UtilJsonDate(int day, int month, int year) 
	{
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getSqlFormat()
	{
		return String.valueOf(day).concat("-").concat(String.valueOf(month)).concat("-").concat(String.valueOf(year));
	}
	
	public static UtilJsonDate getDateFromSql(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		
		return new UtilJsonDate(day, month, year);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "UtilJsonDate [year=" + year + ", month=" + month + ", day=" + day + "]";
	}
}
