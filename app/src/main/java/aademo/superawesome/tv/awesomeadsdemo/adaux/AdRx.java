package aademo.superawesome.tv.awesomeadsdemo.adaux;

import android.content.Context;

import rx.Observable;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

/**
 * Created by gabriel.coman on 02/12/2016.
 */

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

}
