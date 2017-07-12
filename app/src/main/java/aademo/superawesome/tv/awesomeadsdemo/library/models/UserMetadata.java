package aademo.superawesome.tv.awesomeadsdemo.library.models;

import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class UserMetadata {

    private int id;
    private String username;
    private String email;
    private long iat;
    private long exp;
    private String iss;

    public boolean isValid() {
        long now = System.currentTimeMillis() / 1000L;
        long time = now - exp;
        return time < 0;
    }

    public static UserMetadata processMetadata(String oauthToken) {

        if (oauthToken == null) return null;
        String[] components = oauthToken.split("\\.");
        String tokenO = null;
        if (components.length >= 2) tokenO = components[1];
        if (tokenO == null) return null;

        byte[] data;
        try {
            data = Base64.decode(tokenO, Base64.DEFAULT);
        } catch (IllegalArgumentException e1) {
            try {
                tokenO += "=";
                data = Base64.decode(tokenO, Base64.DEFAULT);
            } catch (IllegalArgumentException e2) {
                try {
                    tokenO += "=";
                    data = Base64.decode(tokenO, Base64.DEFAULT);
                } catch (IllegalArgumentException e3){
                    return null;
                }
            }
        }

        String jsonData = null;
        try {
            jsonData = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonData, UserMetadata.class);
    }
}
