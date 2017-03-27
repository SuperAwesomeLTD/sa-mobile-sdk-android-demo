package aademo.superawesome.tv.awesomeadsdemo.activities.main.user;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

class UserHistoryViewModel {

    private int placementId;
    private String placementString;
    private String date;

    private Calendar calendar;

    UserHistoryViewModel(UserHistory history) {

        Log.d("SuperAwesome", "Timestamp " + history.getTimestamp());

        // Init calendar
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(history.getTimestamp());

        // init fields
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
}
