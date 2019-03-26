package mcmaster.ca.appcore.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class AppDateFormatter {
    private static final String STANDARD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String SIMPLE_DATE_FORMAT = "EEEE MMM d";
    private static final String FULL_DATE_FORMAT = "EEEE, MMM d, yyyy";
    private static final String TIME_FORMAT = "h:mm a";

    private AppDateFormatter() {
        // Do not let clients instantiate.
    }


    /**
     * Converts a String to a Java {@link Date}.
     *
     * @param dateString
     *     Date string to parse.
     *
     * @return new Date object.
     */
    public static Date convertToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(STANDARD_FORMAT, Locale.US);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * Converts a {@link Date} into a simple date.
     *
     * @param date
     *     Date to convert.
     *
     * @return Simple date string.
     */
    public static String getSimpleDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US);
        return format.format(date);
    }

    /**
     * Converts a Simple Date to a java object.
     *
     * @param simpleDate
     *     simple date to parse.
     *
     * @return new Java Date object.
     */
    public static Date simpleDateToDate(String simpleDate) {
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US);
        try {
            return format.parse(simpleDate);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * Converts a {@link Date} into a full date.
     *
     * @param date
     *     Date to convert.
     *
     * @return full date string.
     */
    public static String getFullDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FULL_DATE_FORMAT, Locale.US);
        return format.format(date);
    }

    /**
     * Converts a {@link Date} into a time.
     *
     * @param date
     *     Date to convert.
     *
     * @return time of the date.
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        return format.format(date);
    }
}
