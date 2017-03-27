package aademo.superawesome.tv.awesomeadsdemo.loginaux;

import android.content.Context;

import org.json.JSONObject;

import rx.Single;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;

class LoginService {

    private String getEndpoint () {
       return "https://api.dashboard.superawesome.tv/v2/user/login";
    }

    private JSONObject getQuery () {
        return new JSONObject ();
    }

    private JSONObject getHeader () {
        return SAJsonParser.newObject(new Object[] {
                "Content-Type", "application/json"
        });
    }

    private JSONObject getBody (String username, String password) {
        return SAJsonParser.newObject(new Object[] {
                "username", username,
                "password", password
        });
    }

    Single<LoginUser> login(Context context, String username, String password) {

        SANetwork network = new SANetwork();

        String endpoint = getEndpoint ();
        JSONObject query = getQuery();
        JSONObject header = getHeader ();
        JSONObject body = getBody(username, password);

        return Single.create(subscriber -> {

            network.sendPOST(context, endpoint, query, header, body, (status, payload, success) -> {
                subscriber.onSuccess(new LoginUser(payload));
            });

        });

    }

}
