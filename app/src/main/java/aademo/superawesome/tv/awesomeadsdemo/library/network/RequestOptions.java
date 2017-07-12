package aademo.superawesome.tv.awesomeadsdemo.library.network;

import android.support.annotation.NonNull;

import org.json.JSONObject;

public interface RequestOptions {
    @NonNull String getMethod();
    @NonNull String getEndpoint();
    @NonNull JSONObject getQuery();
    @NonNull JSONObject getHeaders();
    @NonNull JSONObject getBody();
}