package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdRx;
import aademo.superawesome.tv.awesomeadsdemo.display.DisplayActivity;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.Observable;
import rx.functions.Func1;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAProgressDialog;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // get intent data
        int placementId = getIntent().getIntExtra(getString(R.string.k_intent_pid), 0);
        boolean test = getIntent().getBooleanExtra(getString(R.string.k_intent_test), false);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // get the list view
        ListView listView = (ListView) findViewById(R.id.SettingsListView);
        Button loadBtn = (Button) findViewById(R.id.LoadAdButton);

        // create the provider and the preload object
        SettingsProvider provider = new SettingsProvider();

        // create an Rx shared observable
        Observable<AdFormat> formatRx = AdRx.loadAd(this, placementId, test).share();
        Observable<Void> buttonRx = RxView.clicks(loadBtn).share();

        // act on the loading observable
        formatRx
                .doOnSubscribe(() -> SAProgressDialog.getInstance().showProgress(SettingsActivity.this))
                .doOnCompleted(() -> SAProgressDialog.getInstance().hideProgress())
                .doOnError(throwable -> SAProgressDialog.getInstance().hideProgress())
                .flatMap(new Func1<AdFormat, Observable<SettingsViewModel>>() {
                    @Override
                    public Observable<SettingsViewModel> call(AdFormat adFormat) {
                        return provider.getSettings(adFormat);
                    }
                })
                .filter(SettingsViewModel::isActive)
                .toList()
                .subscribe(settingsViewModels -> {

                    RxDataSource.from(SettingsActivity.this, settingsViewModels)
                            .bindTo(listView)
                            .customiseRow(R.layout.row_settings, SettingsViewModel.class, (viewModel, holderView) -> {

                                Context context = SettingsActivity.this;

                                Switch itemSwitch = (Switch) holderView.findViewById(R.id.OptionSwitch);
                                itemSwitch.setChecked(viewModel.isValue());
                                RxView.clicks(itemSwitch).subscribe(aVoid -> {
                                    viewModel.setValue(itemSwitch.isChecked());
                                });

                                TextView nameTextView = (TextView) holderView.findViewById(R.id.OptionName);
                                nameTextView.setText(viewModel.getItem() != null ? viewModel.getItem() : context.getString(R.string.settings_row_option_name_default));

                                TextView detailsTextView = (TextView) holderView.findViewById(R.id.OptionDetails);
                                detailsTextView.setText(viewModel.getDetails() != null ? viewModel.getDetails() : context.getString(R.string.settings_row_option_details_default));

                            })
                            .fire();
                });

        // case for error
        formatRx
                .filter(adFormat -> adFormat == AdFormat.unknown)
                .subscribe(adFormat -> {
                    SAAlert.getInstance().show(
                            SettingsActivity.this,
                            getString(R.string.activity_settings_demo_error_load_popup_title),
                            getString(R.string.activity_settings_demo_error_load_popup_message),
                            getString(R.string.activity_settings_demo_error_load_popup_btn),
                            null,
                            false,
                            0,
                            (i, s) -> onBackPressed()
                    );
                });

        // act on the loading and button observables together to make a
        // decision for the banner type ad
        Observable
                .combineLatest(formatRx, buttonRx, (adFormat, aVoid) -> adFormat)
                .filter(adFormat -> adFormat == AdFormat.smallbanner ||
                        adFormat == AdFormat.normalbanner ||
                        adFormat == AdFormat.bigbanner ||
                        adFormat == AdFormat.mpu)
                .subscribe(adFormat -> {

                    Intent settings = new Intent(SettingsActivity.this, DisplayActivity.class);
                    settings.putExtra(getString(R.string.k_intent_pid), placementId);
                    settings.putExtra(getString(R.string.k_intent_test), true);
                    settings.putExtra(getString(R.string.k_intent_format), adFormat.ordinal());
                    settings.putExtra(getString(R.string.k_intent_pg), provider.getParentalGateValue());
                    settings.putExtra(getString(R.string.k_intent_bg), provider.getTransparentBgValue());
                    SettingsActivity.this.startActivity(settings);

                });

        // act on the loading and button observables together to make a
        // decision for the interstitial type ad
        Observable
                .combineLatest(formatRx, buttonRx, (adFormat, aVoid) -> adFormat)
                .filter(adFormat -> adFormat == AdFormat.interstitial)
                .doOnNext(adFormat -> {

                    SAInterstitialAd.setTestMode(test);
                    SAInterstitialAd.setParentalGate(provider.getParentalGateValue());
                    SAInterstitialAd.setBackButton(provider.getBackButtonValue());
                    SAInterstitialAd.setOrientation(
                            provider.getLockToLandscapeValue() ?
                                    SAOrientation.LANDSCAPE :
                                    provider.getLockToPortraitValue() ?
                                            SAOrientation.PORTRAIT : SAOrientation.ANY);
                })
                .flatMap(new Func1<AdFormat, Observable<SAEvent>>() {
                    @Override
                    public Observable<SAEvent> call(AdFormat adFormat) {
                        return AdRx.loadInterstitial(SettingsActivity.this, placementId);
                    }
                })
                .filter(saEvent -> saEvent == SAEvent.adLoaded)
                .subscribe(saEvent -> {
                    SAInterstitialAd.play(placementId, SettingsActivity.this);
                });

        // act on the loading and button observables together to make a
        // decision for the video type ad
        Observable
                .combineLatest(formatRx, buttonRx, (adFormat, aVoid) -> adFormat)
                .filter(adFormat -> adFormat == AdFormat.video)
                .doOnNext(adFormat -> {

                    SAVideoAd.setTestMode(test);
                    SAVideoAd.setParentalGate(provider.getParentalGateValue());
                    SAVideoAd.setBackButton(provider.getBackButtonValue());
                    SAVideoAd.setCloseButton(provider.getCloseButtonValue());
                    SAVideoAd.setCloseAtEnd(provider.getAutoCloseValue());
                    SAVideoAd.setSmallClick(provider.getSmallClickValue());
                    SAVideoAd.setOrientation(
                            provider.getLockToLandscapeValue() ?
                                    SAOrientation.LANDSCAPE :
                                    provider.getLockToPortraitValue() ?
                                            SAOrientation.PORTRAIT : SAOrientation.ANY);

                })
                .flatMap(new Func1<AdFormat, Observable<SAEvent>>() {
                    @Override
                    public Observable<SAEvent> call(AdFormat adFormat) {
                        return AdRx.loadVideo(SettingsActivity.this, placementId);
                    }
                })
                .filter(saEvent -> saEvent == SAEvent.adLoaded)
                .subscribe(saEvent -> {
                    SAVideoAd.play(placementId, SettingsActivity.this);
                });

    }
}
