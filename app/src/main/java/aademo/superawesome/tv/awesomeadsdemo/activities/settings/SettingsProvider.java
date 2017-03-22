package aademo.superawesome.tv.awesomeadsdemo.activities.settings;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import rx.Observable;
import tv.superawesome.sdk.SuperAwesome;

class SettingsProvider {

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

    SettingsProvider(Context c) {

        settingsDict = new HashMap<>();
        settingsDict.put(KEY_PARENTAL_GATE,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_pg_gate_title),
                        c.getString(R.string.page_settings_row_pg_gate_details),
                        SuperAwesome.getInstance().defaultParentalGate())
        );
        settingsDict.put(KEY_TRANSPARENT_BG,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_bg_color_title),
                        c.getString(R.string.page_settings_row_bg_color_details),
                        SuperAwesome.getInstance().defaultBgColor())
        );
        settingsDict.put(KEY_BACK_BUTTON,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_back_button_title),
                        c.getString(R.string.page_settings_row_back_button_details) ,
                        SuperAwesome.getInstance().defaultBackButton())
        );
        settingsDict.put(KEY_LOCK_PORTRAIT,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_lock_portrait_title),
                        c.getString(R.string.page_settings_row_lock_portrait_details),
                        false)
        );
        settingsDict.put(KEY_LOCK_LANSCAPE,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_lock_landscape_title),
                        c.getString(R.string.page_settings_row_lock_landscape_details),
                        false)
        );
        settingsDict.put(KEY_CLOSE_BUTTON,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_close_button_title),
                        c.getString(R.string.page_settings_row_close_button_details),
                        SuperAwesome.getInstance().defaultCloseButton())
        );
        settingsDict.put(KEY_AUTO_CLOSE,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_auto_close_title),
                        c.getString(R.string.page_settings_row_auto_close_details),
                        SuperAwesome.getInstance().defaultCloseAtEnd())
        );
        settingsDict.put(KEY_SMALL_CLICK,
                new SettingsViewModel(
                        c.getString(R.string.page_settings_row_small_click_title),
                        c.getString(R.string.page_settings_row_small_click_details),
                        SuperAwesome.getInstance().defaultSmallClick())
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
                case smallbanner:
                case normalbanner:
                case bigbanner:
                case mpu: {
                    this.getTransparentBg().setActive(true);
                    break;
                }
                case mobile_portrait_interstitial:
                case mobile_landscape_interstitial:
                case tablet_portrait_interstitial:
                case tablet_landscape_interstitial: {
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
                case appwall: break;
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
