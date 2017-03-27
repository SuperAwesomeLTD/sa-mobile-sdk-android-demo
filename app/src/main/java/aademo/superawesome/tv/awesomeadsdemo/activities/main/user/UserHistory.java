package aademo.superawesome.tv.awesomeadsdemo.activities.main.user;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class UserHistory extends SABaseObject {

    private int placementId;
    private long timestamp;

    public UserHistory (int placementId) {
        this.placementId = placementId;
        this.timestamp = System.currentTimeMillis();
    }

    public UserHistory (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    public UserHistory (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "placementId", placementId,
                "timestamp", timestamp
        });
    }

    @Override
    public void readFromJson(JSONObject json) {
        placementId = SAJsonParser.getInt(json, "placementId");
        try {
            timestamp = json.getLong("timestamp");
        } catch (JSONException e) {
            //
        }
    }

    public int getPlacementId() {
        return placementId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
