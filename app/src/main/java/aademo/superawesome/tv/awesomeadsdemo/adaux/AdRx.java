package aademo.superawesome.tv.awesomeadsdemo.adaux;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import rx.Observable;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.sanetwork.request.SANetwork;
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

                if (saEvent == SAEvent.adLoaded) {
                    subscriber.onCompleted();
                }
            });

            bannerAd.load(placementId);

        });

    }

    public static Observable<SAEvent> loadInterstitial (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAInterstitialAd.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);

                if (saEvent == SAEvent.adLoaded) {
                    subscriber.onCompleted();
                }
            });

            SAInterstitialAd.load(placementId, context);

        });

    }

    public static Observable<SAEvent> loadVideo (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAVideoAd.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);

                if (saEvent == SAEvent.adLoaded) {
                    subscriber.onCompleted();
                }
            });

            SAVideoAd.load(placementId, context);

        });

    }

    public static Observable<SAEvent> loadAppWall (Context context, int placementId) {

        return Observable.create(subscriber -> {

            SAAppWall.setListener((SAInterface) (i, saEvent) -> {
                subscriber.onNext(saEvent);

                if (saEvent == SAEvent.adLoaded) {
                    subscriber.onCompleted();
                }
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

                if (format == AdFormat.unknown) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onNext(format);
                    subscriber.onCompleted();
                }

            });

        });
    }

    public static Observable<SACreative> loadCreative (Context context, int placementId) {

        SASession session = new SASession(context);

        String url = session.getBaseUrl() + "/ad/" + placementId;

        JSONObject query = SAJsonParser.newObject(new Object[] {
                "debug", "json",
                "forceCreative", 1,
                "jwtToken", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6NiwidXNlcm5hbWUiOiJBZG1pbiIsImVtYWlsIjoiYXdlc29tZWFkcy5hZG1pbkBzdXBlcmF3ZXNvbWUudHYiLCJpYXQiOjE0ODk2ODM4MTAsImV4cCI6MTQ5MDI4ODYxMCwiaXNzIjoiZGFzaGJvYXJkLnN0YXNnaW5nLnN1cGVyYXdlc29tZS50diJ9.xob0vNjEvMQcp2Fe1_MJkTLxNlSa2zgUbIndtCH_51Y"
        });

        return Observable.create(subscriber -> {

            SANetwork network = new SANetwork();
            network.sendGET(context, url, query, new JSONObject(), (status, payload, success) -> {

                if (success && status == 200 && payload != null) {

                    JSONObject jsonObject = SAJsonParser.newObject(payload);
                    List<SACreative> creatives = SAJsonParser.getListFromJsonArray(jsonObject, "allCreatives", new SAJsonToList<SACreative, JSONObject>() {
                        @Override
                        public SACreative traverseItem(JSONObject jsonObject) {
                            return new SACreative(jsonObject);
                        }
                    });

                    for (SACreative creative : creatives) {
                        subscriber.onNext(creative);
                    }
                    subscriber.onCompleted();

                } else {
                    subscriber.onError(new Throwable("Network error"));
                }

            });

        });
    }
}
