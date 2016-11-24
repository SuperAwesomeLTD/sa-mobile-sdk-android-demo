package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.jakewharton.rxbinding.view.RxView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.UniversalNotifier;
import aademo.superawesome.tv.awesomeadsdemo.display.DisplayActivity;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.sdk.views.SAAppWall;
import tv.superawesome.sdk.views.SAInterface;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAVideoAd;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // get intent data
        int placementId = getIntent().getIntExtra(getString(R.string.k_intent_pid), 0);
        int format = getIntent().getIntExtra(getString(R.string.k_intent_format), 0);
        boolean test = getIntent().getBooleanExtra(getString(R.string.k_intent_test), false);
        AdFormat adFormat = AdFormat.fromInteger(format);

        final SettingsSource source = new SettingsSource();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // get layout item
        LinearLayout option1 = (LinearLayout) findViewById(R.id.Option1);
        LinearLayout option2 = (LinearLayout) findViewById(R.id.Option2);
        LinearLayout option3 = (LinearLayout) findViewById(R.id.Option3);
        LinearLayout option4 = (LinearLayout) findViewById(R.id.Option4);
        LinearLayout option5 = (LinearLayout) findViewById(R.id.Option5);
        LinearLayout option6 = (LinearLayout) findViewById(R.id.Option6);
        LinearLayout option7 = (LinearLayout) findViewById(R.id.Option7);
        LinearLayout option8 = (LinearLayout) findViewById(R.id.Option8);
        Switch option1Switch = (Switch) findViewById(R.id.Option1Switch);
        Switch option2Switch = (Switch) findViewById(R.id.Option2Switch);
        Switch option3Switch = (Switch) findViewById(R.id.Option3Switch);
        Switch option4Switch = (Switch) findViewById(R.id.Option4Switch);
        Switch option5Switch = (Switch) findViewById(R.id.Option5Switch);
        Switch option6Switch = (Switch) findViewById(R.id.Option6Switch);
        Switch option7Switch = (Switch) findViewById(R.id.Option7Switch);
        Switch option8Switch = (Switch) findViewById(R.id.Option8Switch);
        Button loadAd = (Button) findViewById(R.id.LoadAdButton);

        // observe changes in what items should be visible or not
        UniversalNotifier.observe("PARENTAL_GATE_STATUS_CHANGED").
                map(s -> source.parentalGateStatus).
                subscribe(aBoolean -> setChanged(option1, aBoolean));

        UniversalNotifier.observe("TRANSPARENT_BG_STATUS_CHANGED").
                map(s -> source.transparentBgStatus).
                subscribe(aBoolean -> setChanged(option2, aBoolean));

        UniversalNotifier.observe("BACK_BUTTON_STATUS_CHANGED").
                map(s -> source.backButtonStatus).
                subscribe(aBoolean -> setChanged(option3, aBoolean));

        UniversalNotifier.observe("LOCK_TO_PORTRAIT_STATUS_CHANGED").
                map(s -> source.lockToPortraitStatus).
                subscribe(aBoolean -> setChanged(option4, aBoolean));

        UniversalNotifier.observe("LOCK_TO_LANDSCAPE_STATUS_CHANGED").
                map(s -> source.lockToLandscapeStatus).
                subscribe(aBoolean -> setChanged(option5, aBoolean));

        UniversalNotifier.observe("CLOSE_BUTTON_STATUS_CHANGED").
                map(s -> source.closeButtonStatus).
                subscribe(aBoolean -> setChanged(option6, aBoolean));

        UniversalNotifier.observe("AUTO_CLOSE_STATUS_CHANGED").
                map(s -> source.autoCloseStatus).
                subscribe(aBoolean -> setChanged(option7, aBoolean));

        UniversalNotifier.observe("SMALL_CLICK_STATUS_CHANGED").
                map(s -> source.smallClickStatus).
                subscribe(aBoolean -> setChanged(option8, aBoolean));

        // observe changes in what switches have been automatically enabled or not
        UniversalNotifier.observe("PARENTAL_GATE_VALUE_CHANGED").
                map(s -> source.parentalGate).
                subscribe(option1Switch::setChecked);

        UniversalNotifier.observe("TRANSPARENT_BG_VALUE_CHANGED").
                map(s -> source.transparentBg).
                subscribe(option2Switch::setChecked);

        UniversalNotifier.observe("BACK_BUTTON_VALUE_CHANGED").
                map(s -> source.backButton).
                subscribe(option3Switch::setChecked);

        UniversalNotifier.observe("LOCK_TO_PORTRAIT_VALUE_CHANGED").
                map(s -> source.lockToPortrait).
                subscribe(option4Switch::setChecked);

        UniversalNotifier.observe("LOCK_TO_LANDSCAPE_VALUE_CHANGED").
                map(s -> source.lockToLandscape).
                subscribe(option5Switch::setChecked);

        UniversalNotifier.observe("CLOSE_BUTTON_VALUE_CHANGED").
                map(s -> source.closeButton).
                subscribe(option6Switch::setChecked);

        UniversalNotifier.observe("AUTO_CLOSE_VALUE_CHANGED").
                map(s -> source.autoClose).
                subscribe(option7Switch::setChecked);

        UniversalNotifier.observe("SMALL_CLICK_VALUE_CHANGED").
                map(s -> source.smallClick).
                subscribe(option8Switch::setChecked);

        // observe changes when pressing on a switch
        RxView.clicks(option1Switch).
                map(aVoid -> option1Switch.isChecked()).
                subscribe(source::setParentalGate);

        RxView.clicks(option2Switch).
                map(aVoid -> option2Switch.isChecked()).
                subscribe(source::setTransparentBg);

        RxView.clicks(option3Switch).
                map(aVoid -> option3Switch.isChecked()).
                subscribe(source::setBackButton);

        RxView.clicks(option4Switch).
                map(aVoid -> option4Switch.isChecked()).
                subscribe(source::setLockToPortrait);

        RxView.clicks(option5Switch).
                map(aVoid -> option5Switch.isChecked()).
                subscribe(source::setLockToLandscape);

        RxView.clicks(option6Switch).
                map(aVoid -> option6Switch.isChecked()).
                subscribe(source::setCloseButton);

        RxView.clicks(option7Switch).
                map(aVoid -> option7Switch.isChecked()).
                subscribe(source::setAutoClose);

        RxView.clicks(option8Switch).
                map(aVoid -> option8Switch.isChecked()).
                subscribe(source::setSmallClick);

        // setup must be done at the end
        source.setup(adFormat);

        // load ad button
        RxView.clicks(loadAd).subscribe(aVoid -> executeLoad(placementId, test, adFormat, source));

    }

    private void executeLoad (int placementId, boolean test, AdFormat format, SettingsSource source) {
        switch (format) {
            case unknown: {
                loadErrorPopup();
                break;
            }
            case smallbanner:
            case normalbanner:
            case bigbanner:
            case mpu: {

                Intent settings = new Intent(this, DisplayActivity.class);
                settings.putExtra(getString(R.string.k_intent_pid), placementId);
                settings.putExtra(getString(R.string.k_intent_test), test);
                settings.putExtra(getString(R.string.k_intent_format), format.ordinal());
                settings.putExtra(getString(R.string.k_intent_pg), source.parentalGate);
                settings.putExtra(getString(R.string.k_intent_bg), source.transparentBg);
                startActivity(settings);

                break;
            }
            case interstitial: {
                SAInterstitialAd.disableTestMode();
                SAInterstitialAd.disableParentalGate();
                SAInterstitialAd.disableBackButton();
                SAInterstitialAd.setOrientationAny();
                if (test) {
                    SAInterstitialAd.enableTestMode();
                }
                if (source.parentalGate) {
                    SAInterstitialAd.enableParentalGate();
                }
                if (source.backButton) {
                    SAInterstitialAd.enableBackButton();
                }
                if (source.lockToPortrait) {
                    SAInterstitialAd.setOrientationPortrait();
                }
                if (source.lockToLandscape) {
                    SAInterstitialAd.setOrientationLandscape();
                }

                // start loading
                SAInterstitialAd.setListener((SAInterface) (i, saEvent) -> {
                    switch (saEvent) {
                        case adLoaded: {
                            SAInterstitialAd.play(i, SettingsActivity.this);
                            break;
                        }
                        case adFailedToLoad: {
                            loadErrorPopup();
                            break;
                        }
                        case adFailedToShow: {
                            showErrorPopup();
                            break;
                        }
                        case adShown:
                        case adClicked:
                        case adClosed:
                            break;
                    }
                });
                SAInterstitialAd.load(placementId, this);

                break;
            }
            case video: {
                SAVideoAd.disableTestMode();
                SAVideoAd.disableParentalGate();
                SAVideoAd.disableBackButton();
                SAVideoAd.setOrientationAny();
                SAVideoAd.disableCloseButton();
                SAVideoAd.disableCloseAtEnd();
                SAVideoAd.disableSmallClickButton();
                if (test) {
                    SAVideoAd.enableTestMode();
                }
                if (source.parentalGate) {
                    SAVideoAd.enableParentalGate();
                }
                if (source.backButton) {
                    SAVideoAd.enableBackButton();
                }
                if (source.lockToPortrait) {
                    SAVideoAd.setOrientationPortrait();
                }
                if (source.lockToLandscape) {
                    SAVideoAd.setOrientationLandscape();
                }
                if (source.closeButton) {
                    SAVideoAd.enableCloseButton();
                }
                if (source.autoClose) {
                    SAVideoAd.enableCloseAtEnd();
                }
                if (source.smallClick) {
                    SAVideoAd.enableSmallClickButton();
                }

                SAVideoAd.setListener((SAInterface) (i, saEvent) -> {
                    switch (saEvent) {

                        case adLoaded: {
                            SAVideoAd.play(i, SettingsActivity.this);
                            break;
                        }
                        case adFailedToLoad: {
                            loadErrorPopup();
                            break;
                        }
                        case adFailedToShow: {
                            showErrorPopup();
                            break;
                        }
                        case adShown:
                        case adClicked:
                        case adClosed:
                            break;
                    }
                });
                SAVideoAd.load(placementId, this);

                break;
            }
            case gamewall: {
                SAAppWall.disableTestMode();
                SAAppWall.disableParentalGate();
                SAAppWall.disableBackButton();
                if (test) {
                    SAAppWall.enableTestMode();
                }
                if (source.parentalGate) {
                    SAAppWall.enableParentalGate();
                }
                if (source.backButton) {
                    SAAppWall.enableBackButton();
                }

                SAAppWall.setListener((SAInterface) (i, saEvent) -> {
                    switch (saEvent) {
                        case adLoaded: {
                            SAAppWall.play(i, SettingsActivity.this);
                            break;
                        }
                        case adFailedToLoad: {
                            loadErrorPopup();
                            break;
                        }
                        case adFailedToShow: {
                            showErrorPopup();
                            break;
                        }
                        case adShown:
                        case adClicked:
                        case adClosed:
                            break;
                    }
                });
                SAAppWall.load(placementId, this);
                break;
            }
        }
    }

    private void setChanged (LinearLayout layout, Boolean enabled) {
        if (!enabled) {
            layout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, 0));
        } else {
            layout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void loadErrorPopup () {
        SAAlert.getInstance().show(
                this,
                getString(R.string.activity_settings_demo_error_load_popup_title),
                getString(R.string.activity_settings_demo_error_load_popup_message),
                getString(R.string.activity_settings_demo_error_load_popup_btn),
                null,
                false,
                0,
                null);
    }

    private void showErrorPopup () {
        SAAlert.getInstance().show(
                this,
                getString(R.string.activity_settings_demo_error_show_popup_title),
                getString(R.string.activity_settings_demo_error_show_popup_message),
                getString(R.string.activity_settings_demo_error_show_popup_btn),
                null,
                false,
                0,
                null);
    }
}
