package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.UniversalNotifier;
import rx.functions.Action1;
import rx.functions.Func1;
import tv.superawesome.sdk.views.SAInterstitialAd;

/**
 * Created by gabriel.coman on 24/11/16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // get intent data
        int placementId = getIntent().getIntExtra(getString(R.string.k_intent_pid), 0);
        int format = getIntent().getIntExtra(getString(R.string.k_intent_format), 0);
        AdFormat adFormat = AdFormat.fromInteger(format);

        final SettingsSource source = new SettingsSource();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ScrollView scrollView = (ScrollView) findViewById(R.id.SettingsScrollView);
        TextView item1 = (TextView) findViewById(R.id.Option1Item);
        TextView item2 = (TextView) findViewById(R.id.Option2Item);
        TextView item3 = (TextView) findViewById(R.id.Option3Item);
        TextView item4 = (TextView) findViewById(R.id.Option4Item);
        TextView item5 = (TextView) findViewById(R.id.Option5Item);
        TextView item6 = (TextView) findViewById(R.id.Option6Item);
        TextView item7 = (TextView) findViewById(R.id.Option7Item);
        TextView item8 = (TextView) findViewById(R.id.Option8Item);
        Switch option1Switch = (Switch) findViewById(R.id.Option1Switch);
        Switch option2Switch = (Switch) findViewById(R.id.Option2Switch);
        Switch option3Switch = (Switch) findViewById(R.id.Option3Switch);
        Switch option4Switch = (Switch) findViewById(R.id.Option4Switch);
        Switch option5Switch = (Switch) findViewById(R.id.Option5Switch);
        Switch option6Switch = (Switch) findViewById(R.id.Option6Switch);
        Switch option7Switch = (Switch) findViewById(R.id.Option7Switch);
        Switch option8Switch = (Switch) findViewById(R.id.Option8Switch);
        Button loadAd = (Button) findViewById(R.id.LoadAdButton);

        int blackColor = getResources().getColor(R.color.colorBlack);
        int grayColor = getResources().getColor(R.color.colorGrey);

        UniversalNotifier.observe("PARENTAL_GATE_STATUS_CHANGED").
                map(s -> source.parentalGateStatus).
                subscribe(aBoolean -> {
                    item1.setTextColor(aBoolean ? blackColor : grayColor);
                    option1Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("PARENTAL_GATE_VALUE_CHANGED").
                map(s -> source.parentalGate).
                subscribe(option1Switch::setChecked);

        UniversalNotifier.observe("TRANSPARENT_BG_STATUS_CHANGED").
                map(s -> source.transparentBgStatus).
                subscribe(aBoolean -> {
                    item2.setTextColor(aBoolean ? blackColor : grayColor);
                    option2Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("TRANSPARENT_BG_VALUE_CHANGED").
                map(s -> source.transparentBg).
                subscribe(option2Switch::setChecked);

        UniversalNotifier.observe("BACK_BUTTON_STATUS_CHANGED").
                map(s -> source.backButtonStatus).
                subscribe(aBoolean -> {
                    item3.setTextColor(aBoolean ? blackColor : grayColor);
                    option3Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("BACK_BUTTON_VALUE_CHANGED").
                map(s -> source.backButton).
                subscribe(option3Switch::setChecked);

        UniversalNotifier.observe("LOCK_TO_PORTRAIT_STATUS_CHANGED").
                map(s -> source.lockToPortraitStatus).
                subscribe(aBoolean -> {
                    item4.setTextColor(aBoolean ? blackColor : grayColor);
                    option4Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("LOCK_TO_PORTRAIT_VALUE_CHANGED").
                map(s -> source.lockToPortrait).
                subscribe(option4Switch::setChecked);

        UniversalNotifier.observe("LOCK_TO_LANDSCAPE_STATUS_CHANGED").
                map(s -> source.lockToLandscapeStatus).
                subscribe(aBoolean -> {
                    item5.setTextColor(aBoolean ? blackColor : grayColor);
                    option5Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("LOCK_TO_LANDSCAPE_VALUE_CHANGED").
                map(s -> source.lockToLandscape).
                subscribe(option5Switch::setChecked);

        UniversalNotifier.observe("CLOSE_BUTTON_STATUS_CHANGED").
                map(s -> source.closeButtonStatus).
                subscribe(aBoolean -> {
                    item6.setTextColor(aBoolean ? blackColor : grayColor);
                    option6Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("CLOSE_BUTTON_VALUE_CHANGED").
                map(s -> source.closeButton).
                subscribe(option6Switch::setChecked);

        UniversalNotifier.observe("AUTO_CLOSE_STATUS_CHANGED").
                map(s -> source.autoCloseStatus).
                subscribe(aBoolean -> {
                    item7.setTextColor(aBoolean ? blackColor : grayColor);
                    option7Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("AUTO_CLOSE_VALUE_CHANGED").
                map(s -> source.autoClose).
                subscribe(option7Switch::setChecked);

        UniversalNotifier.observe("SMALL_CLICK_STATUS_CHANGED").
                map(s -> source.smallClickStatus).
                subscribe(aBoolean -> {
                    item8.setTextColor(aBoolean ? blackColor : grayColor);
                    option8Switch.setEnabled(aBoolean);
                });

        UniversalNotifier.observe("SMALL_CLICK_VALUE_CHANGED").
                map(s -> source.smallClick).
                subscribe(option8Switch::setChecked);

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
        RxView.clicks(loadAd).subscribe(aVoid -> executeLoad(placementId, adFormat, source));

    }

    private void executeLoad (int placementId, AdFormat format, SettingsSource source) {
        switch (format) {
            case unknown:
                break;
            case banner:
                break;
            case interstitial: {

                break;
            }
            case video: {
                break;
            }
            case gamewall: {
                break;
            }
        }
    }
}
