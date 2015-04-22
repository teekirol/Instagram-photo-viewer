package codepath.com.instagramphotoviewer;


import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Date;

public class RelativeTimeStampHelper {

    private static final int WEEKS_IN_A_YEAR = 52;
    private static final int WEEKS_IN_A_MONTH = 4;

    private static final String WEEKS_SHORT_SUFFIX = "w";
    private static final String DAYS_SHORT_SUFFIX = "d";
    private static final String HOURS_SHORT_SUFFIX = "h";
    private static final String MINUTES_SHORT_SUFFIX = "m";
    private static final String SECONDS_SHORT_SUFFIX = "s";

    public static String shortRelativeTime(Date d) {
        try {
            Period p = new Period(new DateTime(d), DateTime.now());
            if(p.getYears() > 0) {
                return WEEKS_IN_A_YEAR*p.getYears() + WEEKS_SHORT_SUFFIX;
            } else if(p.getMonths() > 0) {
                return WEEKS_IN_A_MONTH*p.getMonths() + WEEKS_SHORT_SUFFIX;
            } else if(p.toStandardWeeks().getWeeks() > 0) {
                return p.toStandardWeeks().getWeeks() + WEEKS_SHORT_SUFFIX;
            } else if(p.toStandardDays().getDays() > 0) {
                return p.toStandardDays().getDays() + DAYS_SHORT_SUFFIX;
            } else if(p.toStandardHours().getHours() > 0) {
                return p.toStandardHours().getHours() + HOURS_SHORT_SUFFIX;
            } else if(p.toStandardMinutes().getMinutes() > 0) {
                return p.toStandardMinutes().getMinutes() + MINUTES_SHORT_SUFFIX;
            } else {
                return p.toStandardSeconds().getSeconds() + SECONDS_SHORT_SUFFIX;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

}
