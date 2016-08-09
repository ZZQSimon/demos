package com.simon.framework.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: CalendarUtils <br>
 * @Description: packaging java.util.Calendar <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class CalendarUtils {

	private CalendarUtils() {
	}

	/**
	 * <p>
	 * Discription:[see {@link java.util.Calendar}]
	 * </p>
	 * 
	 * @param field
	 *            int
	 * @param date
	 *            Date
	 * @param value
	 *            Date
	 * @return Date
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static Date add(int field, Date date, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int fieldNewValue = (c.get(field) + value);
		c.set(field, fieldNewValue);
		return c.getTime();
	}

	/**
	 * <p>
	 * Discription:[[see {@link java.util.Calendar}]]
	 * </p>
	 * 
	 * @param field
	 *            int
	 * @param date
	 *            Date
	 * @return int
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static int get(int field, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * <p>
	 * Discription:[[see {@link java.util.Calendar}]]
	 * </p>
	 * 
	 * @param field
	 *            int
	 * @param d1
	 *            Date
	 * @param d2
	 *            Date
	 * @return boolean
	 * @date 2016-08-09 09:36:14 <br>
	 * 
	 * @author Simon
	 */
	public static boolean isEqualField(int field, Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return c1.get(field) == c2.get(field);
	}

}
