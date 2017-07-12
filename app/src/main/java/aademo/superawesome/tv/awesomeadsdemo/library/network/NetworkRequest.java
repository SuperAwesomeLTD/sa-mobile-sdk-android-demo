package aademo.superawesome.tv.awesomeadsdemo.library.network;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import aademo.superawesome.tv.awesomeadsdemo.library.Request;

public class NetworkRequest implements Request, RequestOptions {

    private NetworkOperation operation;

    public NetworkRequest(NetworkOperation operation) {
        this.operation = operation;
    }

    @NonNull
    @Override
    public String getMethod() {
        return operation.getMethod();
    }

    @NonNull
    @Override
    public String getEndpoint() {
        return operation.getEndpoint();
    }

    @NonNull
    @Override
    public JSONObject getQuery() {
        return operation.getQuery();
    }

    @NonNull
    @Override
    public JSONObject getHeaders() {
        return operation.getHeaders();
    }

    @NonNull
    @Override
    public JSONObject getBody() {
        return operation.getBody();
    }
}
