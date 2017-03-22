package aademo.superawesome.tv.awesomeadsdemo.loginaux;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

class LoginResponse extends SABaseObject {

    private String token;

    LoginResponse(String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "token", token
        });
    }

    @Override
    public void readFromJson(JSONObject json) {
        token = SAJsonParser.getString(json, "token");
    }

    @Override
    public boolean isValid() {
        return token != null;
    }

    String getToken() {
        return token;
    }
}
