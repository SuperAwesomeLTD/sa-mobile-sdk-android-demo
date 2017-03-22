package aademo.superawesome.tv.awesomeadsdemo.activities.login;

public class LoginModel {

    private String username;
    private String password;


    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private boolean isUsernameValid () {
        return username != null && !username.isEmpty();
    }

    private boolean isPasswordValid () {
        return password != null && !password.isEmpty();
    }

    public boolean isValid () {
        return isUsernameValid() && isPasswordValid();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
