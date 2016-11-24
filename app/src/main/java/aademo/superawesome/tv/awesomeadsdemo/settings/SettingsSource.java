package aademo.superawesome.tv.awesomeadsdemo.settings;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.UniversalNotifier;

public class SettingsSource {

    public boolean parentalGateStatus = false;
    public boolean parentalGate = false;

    public boolean transparentBgStatus = false;
    public boolean transparentBg = false;

    public boolean backButtonStatus = false;
    public boolean backButton = false;

    public boolean lockToPortraitStatus = false;
    public boolean lockToPortrait = false;

    public boolean lockToLandscapeStatus = false;
    public boolean lockToLandscape = false;

    public boolean closeButtonStatus = false;
    public boolean closeButton = false;

    public boolean autoCloseStatus = false;
    public boolean autoClose = false;

    public boolean smallClickStatus = false;
    public boolean smallClick = false;

    public void setup(AdFormat format) {

        setParentalGateStatus(true);
        setParentalGate(true);

        setTransparentBgStatus(false);
        setTransparentBg(false);

        setBackButtonStatus(false);
        setBackButton(false);

        setLockToPortraitStatus(false);
        setLockToPortrait(false);

        setLockToLandscapeStatus(false);
        setLockToLandscape(false);

        setCloseButtonStatus(false);
        setCloseButton(false);

        setAutoCloseStatus(false);
        setAutoClose(false);

        setSmallClickStatus(false);
        setSmallClick(false);

        switch (format) {
            case unknown:
            case smallbanner:
            case normalbanner:
            case bigbanner:
            case mpu: {
                setTransparentBgStatus(true);
                break;
            }
            case interstitial: {
                setBackButtonStatus(true);
                setLockToPortraitStatus(true);
                setLockToLandscapeStatus(true);
                break;
            }
            case video: {
                setBackButtonStatus(true);
                setLockToPortraitStatus(true);
                setLockToLandscapeStatus(true);
                setCloseButtonStatus(true);
                setCloseButton(true);
                setAutoCloseStatus(true);
                setSmallClickStatus(true);
                break;
            }
            case gamewall: {
                setBackButtonStatus(true);
                break;
            }
        }
    }

    public void setParentalGateStatus(boolean value) {
        parentalGateStatus = value;
        UniversalNotifier.postNotification("PARENTAL_GATE_STATUS_CHANGED");
    }

    public void setParentalGate (boolean value) {
        parentalGate = value;
        UniversalNotifier.postNotification("PARENTAL_GATE_VALUE_CHANGED");
    }

    public void setTransparentBgStatus(boolean value) {
        transparentBgStatus = value;
        UniversalNotifier.postNotification("TRANSPARENT_BG_STATUS_CHANGED");
    }

    public void setTransparentBg (boolean value) {
        transparentBg = value;
        UniversalNotifier.postNotification("TRANSPARENT_BG_VALUE_CHANGED");
    }

    public void setBackButtonStatus(boolean value) {
        backButtonStatus = value;
        UniversalNotifier.postNotification("BACK_BUTTON_STATUS_CHANGED");
    }

    public void setBackButton (boolean value) {
        backButton = value;
        UniversalNotifier.postNotification("BACK_BUTTON_VALUE_CHANGED");
    }

    public void setLockToPortraitStatus(boolean value) {
        lockToPortraitStatus = value;
        UniversalNotifier.postNotification("LOCK_TO_PORTRAIT_STATUS_CHANGED");
    }

    public void setLockToPortrait (Boolean value) {
        lockToPortrait = value;
        if (lockToLandscape) {
            lockToLandscape = false;
            UniversalNotifier.postNotification("LOCK_TO_LANDSCAPE_VALUE_CHANGED");
        }
        UniversalNotifier.postNotification("LOCK_TO_PORTRAIT_VALUE_CHANGED");
    }

    public void setLockToLandscapeStatus(boolean value) {
        lockToLandscapeStatus = value;
        UniversalNotifier.postNotification("LOCK_TO_LANDSCAPE_STATUS_CHANGED");
    }

    public void setLockToLandscape (Boolean value) {
        lockToLandscape = value;
        if (lockToPortrait) {
            lockToPortrait = false;
            UniversalNotifier.postNotification("LOCK_TO_PORTRAIT_VALUE_CHANGED");
        }
        UniversalNotifier.postNotification("LOCK_TO_LANDSCAPE_VALUE_CHANGED");
    }

    public void setCloseButtonStatus(boolean value) {
        closeButtonStatus = value;
        UniversalNotifier.postNotification("CLOSE_BUTTON_STATUS_CHANGED");
    }

    public void setCloseButton (boolean value) {
        closeButton = value;
        if (!autoClose && !closeButton) {
            autoClose = true;
            UniversalNotifier.postNotification("AUTO_CLOSE_VALUE_CHANGED");
        }
        UniversalNotifier.postNotification("CLOSE_BUTTON_VALUE_CHANGED");
    }

    public void setAutoCloseStatus(boolean value) {
        autoCloseStatus = value;
        UniversalNotifier.postNotification("AUTO_CLOSE_STATUS_CHANGED");
    }

    public void setAutoClose (boolean value) {
        autoClose = value;
        if (!autoClose && !closeButton) {
            closeButton = true;
            UniversalNotifier.postNotification("CLOSE_BUTTON_VALUE_CHANGED");
        }
        UniversalNotifier.postNotification("AUTO_CLOSE_VALUE_CHANGED");
    }

    public void setSmallClickStatus(boolean value) {
        smallClickStatus = value;
        UniversalNotifier.postNotification("SMALL_CLICK_STATUS_CHANGED");
    }

    public void setSmallClick (boolean value) {
        smallClick = value;
        UniversalNotifier.postNotification("SMALL_CLICK_VALUE_CHANGED");
    }
}
