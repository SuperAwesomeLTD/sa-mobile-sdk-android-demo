package aademo.superawesome.tv.awesomeadsdemo.main.user;

public class UserModel {

    private String placementString = null;
    private int placementId = 0;

    public UserModel (String placementString) {
        this.placementString = placementString;
        try {
            placementId = Integer.parseInt(this.placementString);
        } catch (NumberFormatException e) {
            placementId = 0;
        }
    }

    public boolean isValid () {
        return placementString != null && placementId > 0;
    }

    public int getPlacementId () {
        return placementId;
    }


}
