package ro.unibuc.MyLibrary.main.java.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author antoneandreas
 */

public class ConvertDate {

    public static String convert(Date dt) {
        String date = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long diff = timestamp.getTime() - dt.getTime(); //as given

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long days = TimeUnit.MILLISECONDS.toDays(diff);

        if (days != 0) {
            date = String.valueOf(days) + " day";
            if (days > 1) { date = date + "s"; }
        } else if (hours != 0) {
            date = String.valueOf(hours) + " hour";
            if (hours > 1) { date = date + "s"; }
        } else if (minutes != 0) {
            date = String.valueOf(minutes) + " minute";
            if (minutes > 1) { date = date + "s"; }
        } else {
            date = String.valueOf(seconds) + " second";
            if (seconds > 1) { date = date + "s"; }
        }
        return date;
    }


}