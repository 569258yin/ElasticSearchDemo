package es.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * Created by cao on 14/10/29.
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat YEAR_MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");
    public static final FastDateFormat DATE_DAY_FORMAT = FastDateFormat.getInstance("MM-dd");
    public static final FastDateFormat DAY_FORMAT = FastDateFormat.getInstance("dd");
    public static final FastDateFormat MONTH_DAY_HOUR_MIN_FORMAT = FastDateFormat.getInstance("MM-dd HH:mm");
    public static final FastDateFormat HOUR_MIN_FORMAT = FastDateFormat.getInstance("HH:mm");
    public static final FastDateFormat DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat YEAR_MONTH_DAY_HOUR_MIN_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    public static final FastDateFormat NO_CONNECTOR_DATETIME_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat NO_CONNECTOR_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat NO_CONNECTOR_SIMPLE_DATE_FORMAT = FastDateFormat.getInstance("yyMMdd");
    public static final FastDateFormat FILE_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy:MM:dd-HH:mm:ss");
    public static final FastDateFormat EXCEL_DATE_FORMATE = FastDateFormat.getInstance("yyyy/MM/dd");

    public static final String[] WEEK = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static boolean inRange(Date date, Date from, Date to) {
        if (date == null || from == null || to == null) {
            throw new RuntimeException("日期不能为null");
        }
        long fromTime = getYearMonDayDate(from).getTime();
        long toTime = getYearMonDayDate(to).getTime();
        long time = date.getTime();
        return time <= toTime && time >= fromTime;
    }

    public static boolean isBefore(Date from, Date to) {
        if (from == null || to == null) {
            throw new RuntimeException("日期不能为null");
        }
        return from.getTime() < to.getTime();
    }

    public static String getWeekString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return WEEK[weekIndex];
    }

    /**
     * 判断两个时间是否相同long 值进行判断
     */
    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date1.getTime() == date2.getTime();
        } else if ((date1 == null && date2 != null) || date1 != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否属于同一天2017-05-01 2017-05-01
     */
    public static boolean isSameDay(Date date1,Date date2){
        if (date1 != null && date2 != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date2);
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
        }
        return false;
    }

    public static String generateDateWithFormat(Date date, String format) {
        FastDateFormat dateFormate = FastDateFormat.getInstance(format);
        try {
            return dateFormate.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateFromExcelDateStr(String dateTime) {
        try {
            return EXCEL_DATE_FORMATE.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateFromDateTimeStr(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        try {
            return DATETIME_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateFromYearMonDayHourMinStr(String dateTime) {
        try {
            return YEAR_MONTH_DAY_HOUR_MIN_FORMAT.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean isDateValid(String date) {
        try {
            DATE_FORMAT.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseYearMonthDate(String date) {
        try {
            return YEAR_MONTH_FORMAT.parse(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean isYearMonthDateValid(String date) {
        try {
            YEAR_MONTH_FORMAT.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getFormatYearMonDayHourMin(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return YEAR_MONTH_DAY_HOUR_MIN_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getExcelDateFormate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return EXCEL_DATE_FORMATE.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatDay(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return DAY_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return DATE_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatNoConnectorDateTime(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return NO_CONNECTOR_DATETIME_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatNoConnectorSimpleDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return NO_CONNECTOR_SIMPLE_DATE_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatNoConnectorDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return NO_CONNECTOR_DATE_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return StringUtils.EMPTY;
        }
    }

    public static String getFormatMonthDayHourMin(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return MONTH_DAY_HOUR_MIN_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return "";
        }
    }

    public static String getFormatMonthDayHourMin(String date) {
        if (StringUtils.isEmpty(date)) {
            return StringUtils.EMPTY;
        }
        Date convertData = getDateFromTimeStampStr(date);
        try {
            return MONTH_DAY_HOUR_MIN_FORMAT.format(convertData);
        } catch (Exception e) {
            logger.warn("格式化日期错误", e);
            return "";
        }
    }

    public static String getHourMinFormat(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return HOUR_MIN_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误");
            return "";
        }
    }

    public static String getFormatDateDay(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return DATE_DAY_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误");
            return "";
        }
    }

    public static String getYearMonthFormat(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return YEAR_MONTH_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误");
            return "";
        }
    }


    public static String getFormatDateTime(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        try {
            return DATETIME_FORMAT.format(date);
        } catch (Exception e) {
            logger.warn("格式化日期错误");
            return "";
        }
    }

    public static Date getYearMonDayHourMinDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getYearMonDayDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateFromTimeStampStr(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        try {
            return java.sql.Timestamp.valueOf(time);
        } catch (IllegalArgumentException e) {
            logger.warn("timestamp转换时间错误, time = {}", time);
            return null;
        }
    }

    public static String formatTimeStamp(String time) {
        Date date = getDateFromTimeStampStr(time);
        if (date != null) {
            return getFormatDateTime(date);
        }

        return StringUtils.EMPTY;
    }

    public static Date getDateFromDateStr(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        try {
            return java.sql.Date.valueOf(time);
        } catch (IllegalArgumentException e) {
            logger.warn("date转换时间错误, time = {}", time);
            return null;
        }
    }

    public static Date nextDay(Date date) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date nextNDay(Date date, int n) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime();
    }

    public static Date previousDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static Date previousDay(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -number);
        return calendar.getTime();
    }

    public static Date previousMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    public static Date previousHalfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -6);
        return calendar.getTime();
    }

    public static Date nextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date previousYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    public static String relativeTime(String time, Date compareTime) {
        Date date = DateUtils.getDateFromTimeStampStr(time);
        String relativeTime = StringUtils.EMPTY;
        if (date != null) {
            if (compareTime == null)
                compareTime = new Date();
            if (date.getTime() <= DateUtils.getDateFromTimeStampStr(DateUtils.getFormatDate(compareTime) + " 23:59:59").getTime()
                    && date.getTime() >= DateUtils.getDateFromTimeStampStr(DateUtils.getFormatDate(compareTime) + " 00:00:00").getTime())
                relativeTime = "今天 " + DateUtils.getHourMinFormat(date);
            else if (date.getTime() <= DateUtils.getDateFromTimeStampStr(DateUtils.getFormatDate(DateUtils.previousDay(compareTime)) + " 23:59:59").getTime()
                    && date.getTime() >= DateUtils.getDateFromTimeStampStr(DateUtils.getFormatDate(DateUtils.previousDay(compareTime)) + " 00:00:00").getTime())
                relativeTime = "昨天 " + DateUtils.getHourMinFormat(date);
            else
                relativeTime = DateUtils.getFormatMonthDayHourMin(date);
        }
        return relativeTime;
    }

    public static String getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date firstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getLastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date lastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DATE_FORMAT.format(calendar.getTime());
    }


    public static Date firstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date lastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date firstDayOfSeason() {
        Calendar calendar = Calendar.getInstance();
        List<Integer> seasons = Lists.newArrayList(Calendar.JANUARY, Calendar.APRIL, Calendar.JULY, Calendar.OCTOBER);
        calendar.set(Calendar.MONTH, seasons.get(calendar.get(Calendar.MONTH) / 3));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date lastDayOfSeason() {
        Calendar calendar = Calendar.getInstance();
        List<Integer> seasons = Lists.newArrayList(Calendar.MARCH, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.DECEMBER);
        calendar.set(Calendar.MONTH, seasons.get(calendar.get(Calendar.MONTH) / 3));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getFirstDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date firstDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getLastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Date lastDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int timeCompare(Date time1, Date time2) {
        if (time1 == null && time2 == null) {
            return 0;
        } else if (time1 == null) {
            return -1;
        } else if (time2 == null) {
            return 1;
        } else {
            return time1.compareTo(time2);
        }
    }

    public static int dayDifference(Date time1, Date time2) {
        Calendar day1 = Calendar.getInstance();
        Calendar day2 = Calendar.getInstance();
        day1.setTime(time1);
        day2.setTime(time2);

        return day1.get(Calendar.YEAR) * 365 + day1.get(Calendar.DAY_OF_YEAR) - day2.get(Calendar.YEAR) * 365 - day2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取制定日期的00:00:00日期
     */
    public static Date getFirstTimeOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }
    /**
     * 获取制定日期的23:59:59秒日期
     */
    public static Date getLastTimeOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }


    public static void main(String[] args) throws ParseException {

//        System.out.println(relativeTime("2015-03-09 11:00:00", new Date()));

        Date date = DATETIME_FORMAT.parse("2017-08-01 14:00:06");
        Date date1 =  previousDay(date);
        System.out.println(DATETIME_FORMAT.format(date1));

    }


}
