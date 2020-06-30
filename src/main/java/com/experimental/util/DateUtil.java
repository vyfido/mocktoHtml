package com.experimental.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implement complementary routines for management the dates
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DateUtil {

	/**
	 * Convert a date in a String based in a format
	 * @param date date to convert
	 * @param format format the date
	 * @return string equivalent to date
	 */
	public static String toString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return date == null ? "null" : sdf.format(date);
	}
	
}
