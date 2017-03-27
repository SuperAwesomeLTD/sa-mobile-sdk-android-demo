package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.Database;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.user.UserHistory;

public class DbAux {

    public static void savePlacementToHistory (Context context, UserHistory history) {
        Database.writeItem(context, Database.DB_HISTORY, "" + history.getPlacementId(), history.writeToJson().toString());
    }

    public static List<UserHistory> getPlacementsFromHistory (Context context) {

        List<String> keys = Database.getEntries(context, Database.DB_HISTORY);

        List<UserHistory> result = new ArrayList<>();

        for (String key : keys) {
            String data = Database.getString(context, Database.DB_HISTORY, key);
            UserHistory history = new UserHistory(data);
            result.add(history);
        }

        return result;

    }

}
