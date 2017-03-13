package com.canoo.dolphin.integration.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ValueHelper {

    public static TimeZone getUtcZone() {
        return TimeZone.getTimeZone("UTC");
    }

    public static Calendar createInZone(int year, int month, int dayOfMonth, int hourOfDay, int minute,
                                        TimeZone timeZone) {
        return createInZone(year, month, dayOfMonth, hourOfDay, minute, 0, timeZone);
    }

    public static Calendar createInZone(int year, int month, int dayOfMonth, int hourOfDay,
                                        int minute, int second, TimeZone timeZone) {
        Calendar ret = GregorianCalendar.getInstance(timeZone);
        ret.set(year, month, dayOfMonth,hourOfDay, minute, second);
        ret.set(Calendar.MILLISECOND, 0);
        return ret;
    }

}
