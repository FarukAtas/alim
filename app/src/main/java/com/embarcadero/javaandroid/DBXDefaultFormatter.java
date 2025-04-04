package com.embarcadero.javaandroid;

import android.text.format.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class DBXDefaultFormatter {
    public static final String ANDROID_TIMEFORMAT_WO_MS = "kk:mm:ss";
    public static final int CURRENCYDECIMALPLACE = 4;
    public static final String DATEFORMAT = "yyyy-MM-dd";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final long MILLISECONDSINADAY = 86400000;
    public static final String TIMEFORMAT_MS = "HH:mm:ss.SSS";
    public static final String TIMEFORMAT_WO_MS = "HH:mm:ss";
    private static SimpleDateFormat dateFormatter;
    private static SimpleDateFormat datetimeFormatter;
    private static DBXDefaultFormatter instance;
    private static Locale locale;
    private static SimpleDateFormat timeFormatterWOms;
    private static SimpleDateFormat timeFormatterms;

    public static DBXDefaultFormatter getInstance() {
        if (instance == null) {
            instance = new DBXDefaultFormatter();
        }
        return instance;
    }

    private DBXDefaultFormatter() {
        locale = Locale.US;
        timeFormatterms = new SimpleDateFormat(TIMEFORMAT_MS);
        timeFormatterWOms = new SimpleDateFormat(TIMEFORMAT_WO_MS);
        dateFormatter = new SimpleDateFormat(DATEFORMAT);
        datetimeFormatter = new SimpleDateFormat(DATETIMEFORMAT);
    }

    public Date StringToDate(String StringValue) throws ParseException {
        return dateFormatter.parse(StringValue);
    }

    public Date StringToTime(String StringValue) throws ParseException {
        return timeFormatterms.parse(StringValue);
    }

    public String doubleToString(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###############.###############");
        return decimalFormat.format(value);
    }

    public String Base64Encode(String value) {
        return Base64.encode(value);
    }

    public String TDBXTimeToString(int value) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date d = new Date(value);
        c.setTime(d);
        return DateFormat.format(ANDROID_TIMEFORMAT_WO_MS, c).toString();
    }

    public String TDBXDateToString(int value) {
        GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance(getLocale());
        gcal.setGregorianChange(new Date(Long.MIN_VALUE));
        gcal.clear();
        gcal.set(1, 1);
        gcal.set(2, 0);
        gcal.set(5, 1);
        gcal.add(5, value - 1);
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setCalendar(gcal);
        String s = sdf.format(gcal.getTime());
        return s;
    }

    public int StringToTDBXTime(String value) {
        String[] parts = value.split(":");
        return (Integer.valueOf(parts[0]).intValue() * 3600000) + (Integer.valueOf(parts[1]).intValue() * 60000) + (Integer.valueOf(parts[2]).intValue() * 1000);
    }

    public int StringToTDBXDate(String value) {
        String[] parts = value.split("-");
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance(getLocale());
        cal.setGregorianChange(new Date(Long.MIN_VALUE));
        cal.clear();
        cal.set(1, Integer.parseInt(parts[0]));
        cal.set(2, Integer.parseInt(parts[1]) - 1);
        cal.set(5, Integer.parseInt(parts[2]));
        long ms = cal.getTimeInMillis() + 62135596800000L + MILLISECONDSINADAY;
        int val = (int) (ms / MILLISECONDSINADAY);
        return val;
    }

    public String floatToString(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("###############.###############");
        return decimalFormat.format(value);
    }

    public String currencyToString(double value) {
        Formatter formatter = new Formatter(getLocale());
        return formatter.format("%.4f", Double.valueOf(value)).toString();
    }

    private Locale getLocale() {
        return locale;
    }

    public double StringToDouble(String value) {
        return Double.valueOf(value).doubleValue();
    }

    public String DateTimeToString(Date dateValue) {
        return datetimeFormatter.format(dateValue);
    }

    public String TimeToString(Date timeValue) {
        return timeFormatterms.format(timeValue);
    }

    public String TimeToStringWOms(Date timeValue) {
        return timeFormatterWOms.format(timeValue);
    }

    public String DateToString(Date dateValue) {
        return dateFormatter.format(dateValue);
    }

    public Date StringToDateTime(String value) throws ParseException {
        return datetimeFormatter.parse(value);
    }

    public String AnsiStringToString(String value) {
        return value;
    }

    public String WideStringToString(String value) {
        return value;
    }

    public String Int8ToString(int value) {
        return String.valueOf(value);
    }

    public String Int16ToString(int value) {
        return String.valueOf(value);
    }

    public String Int32ToString(int value) {
        return String.valueOf(value);
    }

    public String Int64ToString(long value) {
        return String.valueOf(value);
    }

    public String UInt8ToString(int value) {
        return String.valueOf(value);
    }

    public String UInt16ToString(int value) {
        return String.valueOf(value);
    }

    public String UInt32ToString(long value) {
        return String.valueOf(value);
    }

    public String UInt64ToString(long value) {
        return String.valueOf(value);
    }

    public String tryObjectToString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Number) {
            if (value instanceof Double) {
                return doubleToString(((Double) value).doubleValue());
            }
            if (value instanceof Float) {
                return floatToString(((Float) value).floatValue());
            }
            return Int64ToString(((Number) value).longValue());
        }
        if (value instanceof Date) {
            return DateTimeToString((Date) value);
        }
        if (value instanceof Boolean) {
            return booleanToString(((Boolean) value).booleanValue());
        }
        return "unsupportedtype(" + value.getClass().getName() + ")";
    }

    public String booleanToString(boolean booleanValue) {
        return booleanValue ? "true" : "false";
    }

    public boolean stringToBoolean(String stringValue) throws DBXException {
        if (stringValue.equalsIgnoreCase("true")) {
            return true;
        }
        if (stringValue.equalsIgnoreCase("false")) {
            return false;
        }
        throw new DBXException("[" + stringValue + "] is not a valid boolean");
    }
}
