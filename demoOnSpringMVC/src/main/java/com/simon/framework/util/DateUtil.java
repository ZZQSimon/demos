package com.simon.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.Assert;

/**
 * 
 * @ClassName: DateUtil <br>
 * @Description: Date操作的Util类 <br>
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class DateUtil {

	public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_DATE = "yyyy-MM-dd";

	public static boolean compareDate(final Date firstDate, final Date secondDate) {
		if (firstDate == null || secondDate == null) {
			throw new java.lang.RuntimeException();
		}

		final String strFirstDate = date2String(firstDate, "yyyy-MM-dd");
		final String strSecondDate = date2String(secondDate, "yyyy-MM-dd");
		if (strFirstDate.equals(strSecondDate)) {
			return true;
		}
		return false;
	}

	public static Timestamp currentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static String currentTimestamp2String(final String pattern) {
		return timestamp2String(currentTimestamp(), pattern);
	}

	public static String date2GregorianCalendarString(final Date date) {
		if (date == null) {
			throw new java.lang.IllegalArgumentException("Date is null");
		}
		final long tmp = date.getTime();
		final GregorianCalendar ca = new GregorianCalendar();
		ca.setTimeInMillis(tmp);
		try {
			final XMLGregorianCalendar t_XMLGregorianCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(ca);
			return t_XMLGregorianCalendar.normalize().toString();
		} catch (final DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException("Date is null");
		}

	}

	public static String date2String(final java.util.Date date, String pattern) {
		if (date == null) {
			throw new java.lang.IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date getEndTimeOfDate(final Date currentDate) {
		Assert.notNull(currentDate);
		final String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 23:59:59";

		return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
	}

	public static Date getFirstDayOfMonth(final Calendar c) {
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH);
		final int day = 1;
		c.set(year, month, day, 0, 0, 0);
		return c.getTime();
	}

	public static Date getLastDayOfMonth(final Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		final int day = 1;
		if (month > 11) {
			month = 0;
			year = year + 1;
		}
		c.set(year, month, day - 1, 0, 0, 0);
		return c.getTime();
	}

	public static Date getStartTimeOfDate(final Date currentDate) {
		Assert.notNull(currentDate);
		final String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 00:00:00";
		return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
	}

	public static void main(final String[] args) {

		// String str1 = "2011-01-01";
		// String str2 = "1988-09-09";
		// Date date1 = DateUtil.string2Date(str1, "yyyy-MM-dd");
		// Date date2 = DateUtil.string2Date(str2, "yyyy-MM-dd");
		// Calendar c1 = Calendar.getInstance();
		// Calendar c2 = Calendar.getInstance();
		// c1.setTime(date1);
		// c2.setTime(date2);
		// c2.add(Calendar.YEAR, 4);
		// if (c2.before(c1)) {
		// System.out.println("illegal");
		// } else {
		// System.out.println("ok");
		// }

		DateUtil.string2Timestamp("2015-12-19 14:05:01", DateUtil.PATTERN_STANDARD);
	}

	public static Date string2Date(final String strDate, String pattern) {
		if (strDate == null || strDate.equals("")) {
			throw new RuntimeException("str date null");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = DateUtil.PATTERN_DATE;
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;

		try {
			date = sdf.parse(strDate);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static Timestamp string2Timestamp(final String strDateTime, String pattern) {
		if (strDateTime == null || strDateTime.equals("")) {
			throw new java.lang.IllegalArgumentException("Date Time Null Illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(strDateTime);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
		return new Timestamp(date.getTime());
	}

	public static String stringToDay(final String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new java.lang.IllegalArgumentException("str dest null");
		}

		final Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		final int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			return "0".concat(String.valueOf(day));
		}
		return String.valueOf(day);
	}

	public static String stringToMonth(final String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new java.lang.IllegalArgumentException("str dest null");
		}

		final Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		month = month + 1;
		if (month < 10) {
			return "0".concat(String.valueOf(month));
		}
		return String.valueOf(month);
	}

	public static String stringToYear(final String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new java.lang.IllegalArgumentException("str dest null");
		}

		final Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return String.valueOf(c.get(Calendar.YEAR));
	}

	public static String timestamp2String(final Timestamp timestamp, String pattern) {
		if (timestamp == null) {
			throw new java.lang.IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}
		return new SimpleDateFormat(pattern).format(new Date(timestamp.getTime()));
	}
}