package primr.apps.eurakacachet.ryme.ryme.utils.helpers.time;


import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-mm-dd";
    public static final String E_MMM_DD_YYYY_HH_MM_A = "E, MMM dd, yyyy hh:mm a";

    public DateFormatter(){}

    public static Date convertStringToDate(String dateString, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        Date startDate = new Date();
        try {
            startDate = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getRelative(Date date){
        long current = (new Date()).getTime();
        return DateUtils.getRelativeTimeSpanString(date.
                getTime(), current, DateUtils.FORMAT_ABBREV_RELATIVE)
                .toString();
    }

    public static String formatDate(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(date);
    }

}
