package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;

import java.util.List;

import aademo.superawesome.tv.Database;
import rx.Observable;

public class DbAux {

    public static void savePlacementToHistory (Context context, int placementId) {
        Database.writeItem(context, Database.DB_HISTORY, "" + placementId, placementId);
    }

    public Observable<Integer> getPlacementsFromHistory (Context context) {

        return Observable.create(subscriber -> {

            List<String> keys = Database.getEntries(context, Database.DB_HISTORY);

            for (String key : keys) {
                int placement = Database.getInt(context, Database.DB_HISTORY, key);
                subscriber.onNext(placement);
            }
            subscriber.onCompleted();

        });

    }

}
