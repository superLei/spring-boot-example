package com.example.service.qa.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {

	public static String TIME_REQ_PATTERN = "yyyyMMddHHmmss";
	public static final String TIME_DB_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SLANTING_PATTERN = "yyyy/MM/dd";
    public static final String DATE_DB_PATTERN = "yyyy-MM-dd";

	public static Long getDBTime() {
		String str = DateTime.now().toString(TIME_REQ_PATTERN);

		return Long.valueOf(str);
	}

	public static String formatTimeToDB(String dateStr){
		return formatDateToDB(dateStr,TIME_REQ_PATTERN,TIME_DB_PATTERN);
	}

	public static String formatDateToDB(String dateStr,String reqPattern,String dbPattern){
		DateTimeFormatter pattern = DateTimeFormat.forPattern(reqPattern);
		return DateTime.parse(dateStr, pattern).toString(dbPattern);
	}

	public static Timestamp parseTime(String clearPointEndTime) {
		DateTime time = DateTime.parse(clearPointEndTime, DateTimeFormat.forPattern(TIME_DB_PATTERN));
		return new Timestamp(time.getMillis());
	}

	public static DateTime getEndDay(DateTime dateTime) {
		return dateTime.millisOfDay().withMaximumValue();
	}

    public static String formatDateString(Long dateTime) {
        String dateTimeStr = dateTime.toString();
        StringBuilder sb = new StringBuilder();
        if (dateTimeStr.length() == 8 || dateTimeStr.length() == 14) {
            sb.append(dateTimeStr, 0, 4);
            sb.append("-");
            sb.append(dateTimeStr, 4, 6);
            sb.append("-");
            sb.append(dateTimeStr, 6, 8);
        }
        return sb.toString();
    }

    /**
     * timestamp转string (yyyy/MM/dd)
     */
    public static String timeStamp2StrSlanting(Timestamp timestamp) {
        String str = "";
        DateFormat sdf = new SimpleDateFormat(DATE_SLANTING_PATTERN);
        try {
            str = sdf.format(timestamp);
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * timestamp转string (yyyy-MM-dd)
     */
    public static String timeStamp2Str(Timestamp timestamp) {
        String str = "";
        DateFormat sdf = new SimpleDateFormat(DATE_DB_PATTERN);
        try {
            str = sdf.format(timestamp);
            return str;
        } catch (Exception e) {
            return str;
        }
    }
}
