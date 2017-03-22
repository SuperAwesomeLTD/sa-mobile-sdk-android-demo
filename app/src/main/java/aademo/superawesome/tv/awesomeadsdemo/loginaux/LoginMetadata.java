package aademo.superawesome.tv.awesomeadsdemo.loginaux;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

class LoginMetadata extends SABaseObject {

    private int id;
    private String username;
    private String email;
    private long iat;
    private long exp;
    private String iss;

    LoginMetadata(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    @Override
    public void readFromJson(JSONObject json) {
        id = SAJsonParser.getInt(json, "id");
        username = SAJsonParser.getString(json, "username");
        email = SAJsonParser.getString(json, "email");
        iat = SAJsonParser.getLong(json, "iat");
        exp = SAJsonParser.getLong(json, "exp");
        iss = SAJsonParser.getString(json, "iss");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "id", id,
                "username", username,
                "email", email,
                "iat", iat,
                "exp", exp,
                "iss", iss
        });
    }

    @Override
    public boolean isValid() {
        long now = System.currentTimeMillis() / 1000L;
        long time = now - exp;
        return time < 0;
    }

    static LoginMetadata processMetadata(String oauthToken) {

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

        try {
            String jsonData = new String(data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonData);
            return new LoginMetadata(jsonObject);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
