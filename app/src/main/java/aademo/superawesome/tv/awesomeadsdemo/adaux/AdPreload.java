package aademo.superawesome.tv.awesomeadsdemo.adaux;

import android.content.Context;
import android.util.Log;

import rx.Observable;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.SASession;

public class AdPreload {

    private Context context = null;

    public AdPreload (Context context) {
        this.context = context;
    }

    public Observable<AdFormat> loadAd (int placementId, boolean test) {

        final SASession session = new SASession(context);
        if (test) {
            session.enableTestMode();
        }
        final SALoader loader = new SALoader(context);
        final AdAux adAux = new AdAux();

        return Observable.create(subscriber -> {
            loader.loadAd(placementId, session, saResponse -> {

                AdFormat format = adAux.determineAdType(saResponse);
                subscriber.onNext(format);
                subscriber.onCompleted();
            });
        });
    }

}
