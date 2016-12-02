package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.os.Parcelable;

/**
 * Created by gabriel.coman on 02/12/2016.
 */

public class SettingsViewModel {

    private String item;
    private String details;
    private boolean value;
    private boolean active = false;

    SettingsViewModel(String item, String details, boolean value) {
        this.item = item;
        this.details = details;
        this.value = value;
    }

    String getItem() {
        return item;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    String getDetails() {
        return details;
    }
}
