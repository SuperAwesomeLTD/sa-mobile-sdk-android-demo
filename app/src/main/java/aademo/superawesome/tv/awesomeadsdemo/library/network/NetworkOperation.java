package aademo.superawesome.tv.awesomeadsdemo.library.network;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SAJsonParser;

public abstract class NetworkOperation implements RequestOptions {

    private NetworkOperation() {}

    public static NetworkOperation login(String username, String password) {
        return new NetworkOperation() {
            @NonNull
            @Override
            public String getMethod() {
                return "POST";
            }

            @NonNull
            @Override
            public String getEndpoint() {
                return "https://api.dashboard.superawesome.tv/v2/user/login/";
            }

            @NonNull
            @Override
            public JSONObject getQuery() {
                return new JSONObject();
            }

            @NonNull
            @Override
            public JSONObject getHeaders() {
                return SAJsonParser.newObject("Content-Type", "application/json");
            }

            @NonNull
            @Override
            public JSONObject getBody() {
                return SAJsonParser.newObject("username", username, "password", password);
            }
        };
    }

    public static NetworkOperation profile(String jwtToken) {
        return new NetworkOperation() {
            @NonNull
            @Override
            public String getMethod() {
                return "GET";
            }

            @NonNull
            @Override
            public String getEndpoint() {
                return "https://api.dashboard.superawesome.tv/v2/user/me";
            }

            @NonNull
            @Override
            public JSONObject getQuery() {
                return SAJsonParser.newObject("include", "permission");
            }

            @NonNull
            @Override
            public JSONObject getHeaders() {
                return SAJsonParser.newObject("aa-user-token", jwtToken);
            }

            @NonNull
            @Override
            public JSONObject getBody() {
                return new JSONObject();
            }
        };
    }

    public static NetworkOperation getApps(String company, String jwtToken) {
        return new NetworkOperation() {
            @NonNull
            @Override
            public String getMethod() {
                return "GET";
            }

            @NonNull
            @Override
            public String getEndpoint() {
                return "https://api.dashboard.superawesome.tv/v2/companies/" + company + "/apps";
            }

            @NonNull
            @Override
            public JSONObject getQuery() {
                return SAJsonParser.newObject("include", "placement", "sort", "name", "all", "true");
            }

            @NonNull
            @Override
            public JSONObject getHeaders() {
                return SAJsonParser.newObject("aa-user-token", jwtToken);
            }

            @NonNull
            @Override
            public JSONObject getBody() {
                return new JSONObject();
            }
        };
    }

    public static NetworkOperation getCompanies(String jwtToken) {
        return new NetworkOperation() {
            @NonNull
            @Override
            public String getMethod() {
                return "GET";
            }

            @NonNull
            @Override
            public String getEndpoint() {
                return "https://api.dashboard.superawesome.tv/v2/companies";
            }

            @NonNull
            @Override
            public JSONObject getQuery() {
                return SAJsonParser.newObject("sort", "name", "all", "true");
            }

            @NonNull
            @Override
            public JSONObject getHeaders() {
                return SAJsonParser.newObject("aa-user-token", jwtToken);
            }

            @NonNull
            @Override
            public JSONObject getBody() {
                return new JSONObject();
            }
        };
    }

    public static NetworkOperation getCompany (String id, String jwtToken) {
        return new NetworkOperation() {
            @NonNull
            @Override
            public String getMethod() {
                return "GET";
            }

            @NonNull
            @Override
            public String getEndpoint() {
                return "https://api.dashboard.superawesome.tv/v2/companies/" + id;
            }

            @NonNull
            @Override
            public JSONObject getQuery() {
                return new JSONObject();
            }

            @NonNull
            @Override
            public JSONObject getHeaders() {
                return SAJsonParser.newObject("aa-user-token", jwtToken);
            }

            @NonNull
            @Override
            public JSONObject getBody() {
                return new JSONObject();
            }
        };
    }
}