package aademo.superawesome.tv.awesomeadsdemo.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import rx.Observable;

/**
 * Created by gabriel.coman on 02/12/2016.
 */

public class SettingsProvider {

    // constants
    private static int KEY_PARENTAL_GATE = 1;
    private static int KEY_TRANSPARENT_BG = 2;
    private static int KEY_BACK_BUTTON = 3;
    private static int KEY_LOCK_PORTRAIT = 4;
    private static int KEY_LOCK_LANSCAPE = 5;
    private static int KEY_CLOSE_BUTTON = 6;
    private static int KEY_AUTO_CLOSE = 7;
    private static int KEY_SMALL_CLICK = 8;

    private HashMap<Integer, SettingsViewModel> settingsDict;

    public SettingsProvider () {

        settingsDict = new HashMap<>();
        settingsDict.put(KEY_PARENTAL_GATE,
                new SettingsViewModel(
                        "Parental gate enabled",
                        "Enabling this setting means that users will be greeted by a Parental Gate before proceeding to the click through destination. The Parental Gate will only allow them to go forward if they perform a simple mathematical operation." ,
                        true)
        );
        settingsDict.put(KEY_TRANSPARENT_BG,
                new SettingsViewModel(
                        "Transparent background color",
                        "This setting controls whether a display ad (banner, MPU, etc) will have a solid grey background or a transparent one.",
                        false)
        );
        settingsDict.put(KEY_BACK_BUTTON,
                new SettingsViewModel(
                        "Back button",
                        "Enabling this setting will permit users to close an interstitial or fullscreen video ad to close it by tapping on the standard Android device 'back button'." ,
                        false)
        );
        settingsDict.put(KEY_LOCK_PORTRAIT,
                new SettingsViewModel(
                        "Lock to portrait",
                        "Enabling this setting will lock the ad unit to portrait mode, irrespective of the user's original lock setting. This is useful when you're sure ads running on your placements are better displayed in portrait mode (e.g. rich media interstitials). Cannot be set at the same time as 'Lock to landscape'.",
                        false)
        );
        settingsDict.put(KEY_LOCK_LANSCAPE,
                new SettingsViewModel(
                        "Lock to landscape",
                        "Enabling this setting will lock the ad unit to landscape mode, irrespective of the user's original lock setting. This is useful when you're sure ads running on your placements are better displayed in landscape mode (e.g. video ads). Cannot be set at the same time as 'Lock to portrait'.",
                        false)
        );
        settingsDict.put(KEY_CLOSE_BUTTON,
                new SettingsViewModel(
                        "Close button",
                        "This setting controls whether video ads will allow for a close button to appear in the top-right corner of the ad. This will allow users to close ads before they have finished running in full. It is enabled by default. Must always be anabled if 'Auto close at end' is disabled.",
                        true)
        );
        settingsDict.put(KEY_AUTO_CLOSE,
                new SettingsViewModel(
                        "Auto close at end",
                        "Enabling this setting will tell a video ad to automatically close when it has finished running. Disabled by default. Must always be enabled if 'Close button' is disabled.",
                        false)
        );
        settingsDict.put(KEY_SMALL_CLICK,
                new SettingsViewModel(
                        "Small click button",
                        "Enabling this setting will provide a video ad a small button in the bottom-left side of the screen to direct the user to the click through. This setting is disabled by default, and the full video surface is clickable.",
                        false)
        );

    }

    Observable<SettingsViewModel> getSettings(AdFormat format) {
        return Observable.create(subscriber -> {

            // customise options
            this.getParentalGate().setActive(true);

            switch (format) {
                case unknown: {
                    this.getParentalGate().setActive(false);
                    break;
                }
                case smallbanner:case normalbanner:case bigbanner: case mpu: {
                    this.getTransparentBg().setActive(true);
                    break;
                }
                case interstitial: {
                    this.getBackButton().setActive(true);
                    this.getLockToPortrait().setActive(true);
                    this.getLockToLandscape().setActive(true);
                    break;
                }
                case video: {
                    this.getBackButton().setActive(true);
                    this.getLockToPortrait().setActive(true);
                    this.getLockToLandscape().setActive(true);
                    this.getCloseButton().setActive(true);
                    this.getAutoClose().setActive(true);
                    this.getSmallClick().setActive(true);
                    break;
                }
                case gamewall: break;
            }

            // create an array of settings to use for observer
            List<SettingsViewModel> settings = new ArrayList<>();

            for (int i = 1; i <= 8; i++) {
                if (this.settingsDict.containsKey(i)) {
                    settings.add(this.settingsDict.get(i));
                }
            }

            for (SettingsViewModel vm : settings) {
                subscriber.onNext(vm);
            }
            subscriber.onCompleted();

        });
    }

    public SettingsViewModel getParentalGate () {
        return settingsDict.get(KEY_PARENTAL_GATE);
    }

    public SettingsViewModel getTransparentBg () {
        return settingsDict.get(KEY_TRANSPARENT_BG);
    }

    public SettingsViewModel getBackButton () {
        return settingsDict.get(KEY_BACK_BUTTON);
    }

    public SettingsViewModel getLockToPortrait () {
        return settingsDict.get(KEY_LOCK_PORTRAIT);
    }

    public SettingsViewModel getLockToLandscape () {
        return settingsDict.get(KEY_LOCK_LANSCAPE);
    }

    public SettingsViewModel getCloseButton () {
        return settingsDict.get(KEY_CLOSE_BUTTON);
    }

    public SettingsViewModel getAutoClose () {
        return settingsDict.get(KEY_AUTO_CLOSE);
    }

    public SettingsViewModel getSmallClick () {
        return settingsDict.get(KEY_SMALL_CLICK);
    }

    public boolean getParentalGateValue () {
        return getParentalGate().isValue();
    }

    public boolean getTransparentBgValue () {
        return getTransparentBg().isValue();
    }

    public boolean getBackButtonValue () {
        return getBackButton().isValue();
    }

    public boolean getLockToPortraitValue () {
        return getLockToPortrait().isValue();
    }

    public boolean getLockToLandscapeValue () {
        return getLockToLandscape().isValue();
    }

    public boolean getCloseButtonValue () {
        return getCloseButton().isValue();
    }

    public boolean getAutoCloseValue () {
        return getAutoClose().isValue();
    }

    public boolean getSmallClickValue () {
        return getSmallClick().isValue();
    }
}
