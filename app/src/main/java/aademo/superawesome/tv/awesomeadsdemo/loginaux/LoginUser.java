package aademo.superawesome.tv.awesomeadsdemo.loginaux;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class LoginUser extends SABaseObject {

    private String token;
    private LoginMetadata metadata;

    static LoginUser getUserFromToken(String token) {
        LoginUser user = new LoginUser();
        user.token = token;
        user.metadata = LoginMetadata.processMetadata(token);
        return user;
    }

    private LoginUser () {
        // do nothing
    }

    LoginUser(String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "token", token,
                "metadata", metadata.writeToJson()
        });
    }

    @Override
    public void readFromJson(JSONObject json) {
        token = SAJsonParser.getString(json, "token");
        metadata = new LoginMetadata(SAJsonParser.getJsonObject(json, "metadata"));
    }

    @Override
    public boolean isValid() {
        return token != null && metadata != null && metadata.isValid();
    }

    String getToken() {
        return token;
    }
}
