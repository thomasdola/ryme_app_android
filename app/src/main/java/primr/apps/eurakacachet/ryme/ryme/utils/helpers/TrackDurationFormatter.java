package primr.apps.eurakacachet.ryme.ryme.utils.helpers;


import android.text.format.DateUtils;

import java.util.Formatter;
import java.util.Locale;

public class TrackDurationFormatter {

    private static StringBuilder formatBuilder = new StringBuilder();
    private static Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

    public static String formatMs(long milliseconds) {
        if (milliseconds < 0) {
            return "--:--";
        }

        long seconds = (milliseconds % DateUtils.MINUTE_IN_MILLIS) / DateUtils.SECOND_IN_MILLIS;
        long minutes = (milliseconds % DateUtils.HOUR_IN_MILLIS) / DateUtils.MINUTE_IN_MILLIS;
        long hours = (milliseconds % DateUtils.DAY_IN_MILLIS) / DateUtils.HOUR_IN_MILLIS;

        formatBuilder.setLength(0);
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        }

        return formatter.format("%02d:%02d", minutes, seconds).toString();
    }

}
