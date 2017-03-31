package aademo.superawesome.tv.awesomeadsdemo.activities.main.user;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

class UserHistoryViewModel implements Comparable{

    private int placementId;
    private String placementString;
    private String date;
    private long timestamp;

    private Calendar calendar;

    UserHistoryViewModel(UserHistory history) {

        // Init calendar
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(history.getTimestamp());

        // init fields
        this.timestamp = history.getTimestamp();
        this.placementId = history.getPlacementId();
        this.placementString = "" + placementId;
        this.date = getDayOfMonth() + " " + getMonth() + " " + getYear();
    }

    private String getDayOfMonth() {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth <= 9 ? ("0" + dayOfMonth) : ("" + dayOfMonth);
    }

    private String getMonth () {
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

    private int getYear () {
        return calendar.get(Calendar.YEAR);
    }

    int getPlacementId() {
        return placementId;
    }

    String getDate() {
        return date;
    }

    String getPlacementString() {
        return placementString;
    }

    @Override
    public int compareTo(Object o) {
        UserHistoryViewModel u = (UserHistoryViewModel) o;
        return timestamp > u.timestamp ? - 1 : 1;
    }
}
