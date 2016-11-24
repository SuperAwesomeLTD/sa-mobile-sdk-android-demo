package aademo.superawesome.tv.awesomeadsdemo.display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

/**
 * Created by gabriel.coman on 24/11/16.
 */
public class DisplayActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // get intent data
        int placementId = getIntent().getIntExtra(getString(R.string.k_intent_pid), 0);
        int format = getIntent().getIntExtra(getString(R.string.k_intent_format), 0);
        boolean test = getIntent().getBooleanExtra(getString(R.string.k_intent_test), false);
        AdFormat adFormat = AdFormat.fromInteger(format);
        boolean pg = getIntent().getBooleanExtra(getString(R.string.k_intent_pg), false);
        boolean bg = getIntent().getBooleanExtra(getString(R.string.k_intent_bg), false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.DisplayToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // normal & big banner
        SABannerAd banner1 = (SABannerAd) findViewById(R.id.Banner1);
        banner1.disableTestMode();
        banner1.disableParentalGate();
        banner1.setBackgroundColor(getResources().getColor(R.color.colorLightGrey3));
        banner1.setListener((SAInterface) (i, saEvent) -> {
            switch (saEvent) {
                case adLoaded: banner1.play(DisplayActivity.this);break;
                case adFailedToLoad: loadErrorPopup(); break;
                case adFailedToShow: showErrorPopup(); break;
            }
        });
        // small banner
        SABannerAd banner2 = (SABannerAd) findViewById(R.id.Banner2);
        banner2.disableTestMode();
        banner2.disableParentalGate();
        banner2.setBackgroundColor(getResources().getColor(R.color.colorLightGrey3));
        banner2.setListener((SAInterface) (i, saEvent) -> {
            switch (saEvent) {
                case adLoaded: banner2.play(DisplayActivity.this);break;
                case adFailedToLoad: loadErrorPopup(); break;
                case adFailedToShow: showErrorPopup(); break;
            }
        });
        // mpu
        SABannerAd banner3 = (SABannerAd) findViewById(R.id.Banner3);
        banner3.disableTestMode();
        banner3.disableParentalGate();
        banner3.setBackgroundColor(getResources().getColor(R.color.colorLightGrey3));
        banner3.setListener((SAInterface) (i, saEvent) -> {
            switch (saEvent) {
                case adLoaded: banner3.play(DisplayActivity.this);break;
                case adFailedToLoad: loadErrorPopup(); break;
                case adFailedToShow: showErrorPopup(); break;
            }
        });

        switch (adFormat) {

            case smallbanner: {
                if (test) {
                    banner2.enableTestMode();
                }
                if (pg) {
                    banner2.enableParentalGate();
                }
                if (bg) {
                    banner2.setColorTransparent();
                }
                banner2.load(placementId);
                break;
            }
            case normalbanner:
            case bigbanner: {
                if (test) {
                    banner1.enableTestMode();
                }
                if (pg) {
                    banner1.enableParentalGate();
                }
                if (bg) {
                    banner1.setColorTransparent();
                }
                banner1.load(placementId);
                break;
            }
            case mpu: {
                if (test) {
                    banner3.enableTestMode();
                }
                if (pg) {
                    banner3.enableParentalGate();
                }
                if (bg) {
                    banner3.setColorTransparent();
                }
                banner3.load(placementId);
                break;
            }
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
