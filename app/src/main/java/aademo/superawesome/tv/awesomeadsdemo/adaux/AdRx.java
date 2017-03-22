package aademo.superawesome.tv.awesomeadsdemo.adaux;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.loginaux.LoginManager;
import rx.Observable;
import rx.Single;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sasession.SASession;

public class AdRx {

    public static Single<SAAd> loadTestAd (Context context, int placementId) {

        final SALoader loader = new SALoader(context);
        final SASession session = new SASession(context);
        session.enableTestMode();

        return Single.create(subscriber -> {

            loader.loadAd(placementId, session, saResponse -> {

                if (saResponse.isValid()) {
                    subscriber.onSuccess(saResponse.ads.get(0));
                } else {
                    subscriber.onError(new Throwable());
                }

            });

        });

    }

    public static Single<SAResponse> processAd (Context context, String payload) {

        final int testPlacement = 10000;
        final SALoader loader = new SALoader(context);
        final SASession session = new SASession(context);

        return Single.create(subscriber -> {

            loader.processAd(testPlacement, payload, 200, session, saResponse -> {

                if (saResponse.isValid()) {
                    subscriber.onSuccess(saResponse);
                } else {
                    subscriber.onError(new Throwable());
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
                "jwtToken", LoginManager.getManager().getLoggedUserToken()
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
