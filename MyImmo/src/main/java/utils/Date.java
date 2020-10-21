/**
 * Edit by EC.
 */

package main.java.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {

    /**
     * convert a String date into a Gregorian Calendar. Do by EC
     * @param date Must be in format "GG/MM/AAAA HH:MM:SS"
     * @return
     */
    public static GregorianCalendar stringToGregorianCalendar(String date) {
        GregorianCalendar newDate = new GregorianCalendar();
        String[] s = date.split(" ");
        String[] s1 = s[0].split("/");
        String[] s2 = s[1].split(":");
        newDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s1[0]));
        newDate.set(Calendar.MONTH, Integer.parseInt(s1[1]) - 1);
        newDate.set(Calendar.YEAR, Integer.parseInt(s1[2]));
        newDate.set(Calendar.HOUR, Integer.parseInt(s2[0]));
        newDate.set(Calendar.MINUTE, Integer.parseInt(s2[1]));
        newDate.set(Calendar.SECOND, Integer.parseInt(s2[2]));
        return newDate;
    }

    /**
     * convert a GregorianCalendar into a String with format "GG/MM/AAAA HH:MM:SS". Do by EC
     * @param date
     * @return
     */
    public static String gregorianCalendarToString(GregorianCalendar date) {
        String s = "";
        s += Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + "/";
        s += Integer.toString(date.get(Calendar.MONTH) + 1) + "/";
        s += Integer.toString(date.get(Calendar.YEAR)) + " ";
        s += Integer.toString(date.get(Calendar.HOUR)) + ":";
        s += Integer.toString(date.get(Calendar.MINUTE)) + ":";
        s += Integer.toString(date.get(Calendar.SECOND));
        return s;
    }

}
