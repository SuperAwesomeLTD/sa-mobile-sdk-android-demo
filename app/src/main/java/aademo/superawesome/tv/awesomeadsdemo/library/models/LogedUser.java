package aademo.superawesome.tv.awesomeadsdemo.library.models;

public class LogedUser {

    private String token;

    public LogedUser () {
        // do nothing
    }

    public String getToken() {
        return token;
    }

    public boolean isValid () {
        return token != null;
    }
}
