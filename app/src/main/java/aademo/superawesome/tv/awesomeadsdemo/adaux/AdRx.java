package aademo.superawesome.tv.awesomeadsdemo.adaux;

import android.content.Context;

import rx.Observable;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

public class AdRx {

    public static Observable<SAEvent> loadBanner (SABannerAd bannerAd, int placementId) {

        return Observable.create(subscriber -> {

            bannerAd.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);
                subscriber.onCompleted();
            });

            bannerAd.load(placementId);

        });

    }

    public static Observable<SAEvent> loadInterstitial (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAInterstitialAd.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);
                subscriber.onCompleted();
            });

            SAInterstitialAd.load(placementId, context);

        });

    }

    public static Observable<SAEvent> loadVideo (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAVideoAd.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);
                subscriber.onCompleted();
            });

            SAVideoAd.load(placementId, context);

        });

    }

    public static Observable<SAEvent> loadAppWall (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAAppWall.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);
                subscriber.onCompleted();
            });

            SAAppWall.load(placementId, context);

        });
    }

    public static Observable<AdFormat> loadAd (Context context, int placementId, boolean test) {

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
