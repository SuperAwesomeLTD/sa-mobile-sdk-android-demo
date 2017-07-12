package aademo.superawesome.tv.awesomeadsdemo.library;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.library.models.Company;
import aademo.superawesome.tv.awesomeadsdemo.library.models.UserProfile;

public class DataStore {

    private static final String LOGIN_FILE = "AA_APP_FILE";
    private static final String LOGIN_KEY = "AA_APP_KEY";

    private static DataStore shared;

    public DataStore() {
    }

    public static DataStore getShared () {
        if (shared == null) {
            shared = new DataStore();
        }
        return shared;
    }

    private List<Company> companies = new ArrayList<>();
    private UserProfile profile;


    public List<Company> getCompanies() {
        return companies;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public String getJwtToken(Context context) {
        return context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE)
                .getString(LOGIN_KEY, null);
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public void setJwtToken(Context context, String jwtToken) {
        context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE)
                .edit()
                .putString(LOGIN_KEY, jwtToken)
                .apply();
    }
}
