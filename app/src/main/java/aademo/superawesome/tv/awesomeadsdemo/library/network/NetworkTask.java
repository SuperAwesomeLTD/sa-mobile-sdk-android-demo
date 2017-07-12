package aademo.superawesome.tv.awesomeadsdemo.library.network;

import android.content.Context;
import android.util.Log;

import aademo.superawesome.tv.awesomeadsdemo.library.Task;
import rx.Single;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;

public class NetworkTask implements Task<NetworkRequest, String, Single<String>> {

    private Context context;

    public NetworkTask(Context context) {
        this.context = context;
    }

    @Override
    public Single<String> execute(NetworkRequest input) {

        SANetwork network = new SANetwork();

        return Single.create(subscriber -> {

            SANetworkInterface listener = (status, payload, success) -> {
                if (status == 200 && payload != null) {
                    subscriber.onSuccess(payload);
                } else {
                    subscriber.onError(new Throwable());
                }
            };

            if (input.getMethod().equals("POST")) {
                network.sendPOST(context, input.getEndpoint(), input.getQuery(), input.getHeaders(), input.getBody(), listener);
            } else {
                network.sendGET(context, input.getEndpoint(), input.getQuery(), input.getHeaders(), listener);
            }
        });
    }
}
