package aademo.superawesome.tv.awesomeadsdemo.library.models;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private int id;
    private String username;
    private String email;
    private String phoneNumber;
    private String companyId;
    private List<Permission> permissions = new ArrayList<>();
    private LogedUser logedUser;

    public UserProfile() {
        // do nothing
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public LogedUser getLogedUser() {
        return logedUser;
    }

    public boolean canImpersonate () {
        boolean can = false;

        for (Permission p : permissions) {
            if (p.getName().equals("Impersonation")) {
                can = true;
                break;
            }
        }

        return can;
    }

    public boolean isValid () {
        return logedUser != null && logedUser.isValid();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
