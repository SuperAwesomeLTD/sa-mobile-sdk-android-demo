package aademo.superawesome.tv.awesomeadsdemo.loginaux;

import android.content.Context;
import android.content.SharedPreferences;

import rx.Observable;
import rx.Single;

public class LoginManager {

    private static final String LOGIN_FILE = "AA_APP_FILE";
    private static final String LOGIN_KEY = "AA_APP_KEY";

    // only singleton instance
    private static LoginManager manager;

    // singleton accessor method
    public static LoginManager getManager () {
        if (manager == null) {
            manager = new LoginManager();
        }
        return manager;
    }

    private LoginService service;
    private LoginUser loginUser;

    private LoginManager () {
        service = new LoginService();
    }

    public Observable<LoginUser> login (Context context, String username, String password) {

        return service.login(context, username, password)
                .map(loginResponse -> LoginUser.getUserFromToken(loginResponse.getToken()))
                .toObservable();

    }

    public Single<LoginUser> check (Context context) {
        return Single.create(subscriber -> {

            SharedPreferences preferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
            String raw = preferences.getString(LOGIN_KEY, null);
            LoginUser user = new LoginUser(raw);
            subscriber.onSuccess(user);

        });
    }

    public String getLoggedUserToken () {
        return loginUser != null ? loginUser.getToken() : "";
    }

    public void setLoggedUser(LoginUser loggedUser) {
        loginUser = loggedUser;
    }

    public void saveUser (Context context, LoginUser user) {

        context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE)
                .edit()
                .putString(LOGIN_KEY, user.writeToJson().toString())
                .apply();
    }

}
