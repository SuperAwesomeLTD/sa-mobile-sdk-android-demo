package aademo.superawesome.tv.awesomeadsdemo.activities.settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.display.DisplayActivity;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdRx;
import gabrielcoman.com.rxdatasource.RxDataSource;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SALoadScreen;
import tv.superawesome.sdk.views.SAInterstitialAd;
import tv.superawesome.sdk.views.SAOrientation;
import tv.superawesome.sdk.views.SAVideoAd;

public class SettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ListView listView = (ListView) findViewById(R.id.SettingsListView);
        Button loadBtn = (Button) findViewById(R.id.LoadAdButton);

        getStringExtras(getString(R.string.k_intent_ad))
                .flatMap(data -> AdRx.processAd(SettingsActivity.this, data))
                .doOnSubscribe(() -> SALoadScreen.getInstance().show(SettingsActivity.this))
                .doOnSuccess(response -> SALoadScreen.getInstance().hide())
                .doOnError(throwable -> SALoadScreen.getInstance().hide())
                .subscribe(saResponse -> {

                    AdFormat format = AdFormat.fromResponse(saResponse);

                    SettingsProvider provider = new SettingsProvider(SettingsActivity.this);
                    provider.getSettings(format)
                            .filter(SettingsViewModel::isActive)
                            .toList()
                            .subscribe(settingsViewModels -> {

                                RxDataSource.create(SettingsActivity.this)
                                        .bindTo(listView)
                                        .customiseRow(R.layout.row_settings, SettingsViewModel.class, (view, viewModel) -> {

                                            Switch itemSwitch = (Switch) view.findViewById(R.id.OptionSwitch);
                                            itemSwitch.setChecked(viewModel.isValue());
                                            RxView.clicks(itemSwitch).subscribe(aVoid -> {
                                                viewModel.setValue(itemSwitch.isChecked());
                                            });

                                            ((TextView) view.findViewById(R.id.OptionName)).setText(viewModel.getItem());
                                            ((TextView) view.findViewById(R.id.OptionDetails)).setText(viewModel.getDetails());

                                        })
                                        .update(settingsViewModels);
                            });

                    RxView.clicks(loadBtn)
                            .subscribe(aVoid -> {

                                if (AdFormat.isBannerType(format))
                                    playBanner(saResponse, format, provider);
                                else if (AdFormat.isInterstitialType(format))
                                    playInterstitial(saResponse, provider);
                                else if (AdFormat.isVideoType(format))
                                    playVideo(saResponse, provider);
                                else if (AdFormat.isAppWallType(format))
                                    playAppWall();

                            });

                }, throwable -> {
                    SAAlert.getInstance().show(
                            SettingsActivity.this,
                            getString(R.string.page_settings_popup_error_title),
                            getString(R.string.page_settings_popup_error_message),
                            getString(R.string.page_settings_popup_error_ok_button),
                            null,
                            false,
                            0,
                            (i, s) -> onBackPressed());
                });

    }

    private void playBanner (SAResponse response, AdFormat format, SettingsProvider provider) {
        DisplayActivity.setBackground(provider.getTransparentBgValue());
        DisplayActivity.setParentalGate(provider.getParentalGateValue());
        DisplayActivity.setFormat(format);
        DisplayActivity.setResponse(response);
        DisplayActivity.play(this);
    }

    private void playInterstitial (SAResponse response, SettingsProvider provider) {
        SAInterstitialAd.setParentalGate(provider.getParentalGateValue());
        SAInterstitialAd.setBackButton(provider.getBackButtonValue());
        SAInterstitialAd.setOrientation(
                provider.getLockToLandscapeValue() ?
                        SAOrientation.LANDSCAPE :
                        provider.getLockToPortraitValue() ?
                                SAOrientation.PORTRAIT : SAOrientation.ANY);
        SAInterstitialAd.setAd(response.ads.get(0));
        SAInterstitialAd.play(response.ads.get(0).placementId, this);
    }

    private void playVideo (SAResponse response, SettingsProvider provider) {
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
        SAVideoAd.setAd(response.ads.get(0));
        SAVideoAd.play(response.ads.get(0).placementId, this);
    }

    private void playAppWall () {
        //
    }
}
