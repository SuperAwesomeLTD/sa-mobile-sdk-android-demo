package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.settings.SettingsActivity;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdRx;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sautils.SAAlert;

public class CreativesActivity extends BaseActivity {

    private CreativesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatives);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.CreativesToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.CreativesRecyclerView);

        getIntExtras(getString(R.string.k_intent_pid))
                .subscribe(placementId -> {

                    AdRx.loadCreative(CreativesActivity.this, placementId)
                            .map(CreativesViewModel::new)
                            .toList()
                            .subscribe(viewModels -> {

                                adapter = new CreativesAdapter(CreativesActivity.this, viewModels);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);

                                adapter.notifyDataSetChanged();

                                adapter.getItemClicks()
                                        .filter(viewModel -> !viewModel.getFormat().isUnknownType())
                                        .map(CreativesViewModel::getCreative)
                                        .map(creative -> {
                                            SAAd ad = new SAAd();
                                            ad.placementId = placementId;
                                            ad.creative = creative;
                                            ad.lineItemId = 10000;
                                            if (ad.creative.format == SACreativeFormat.tag && ad.creative.details.format.equals("video")) {
                                                ad.creative.format = SACreativeFormat.video;
                                                ad.creative.details.vast = ad.creative.details.tag;
                                            }
                                            return ad;
                                        })
                                        .map(saAd -> saAd.writeToJson().toString())
                                        .subscribe(this::startSettingsActivityWithAd);

                                adapter.getItemClicks()
                                        .map(CreativesViewModel::getFormat)
                                        .filter(AdFormat::isUnknownType)
                                        .subscribe(this::unsupportedFormatError);


                            }, this::loadAdError);

                });

    }

    private void startSettingsActivityWithAd (String jsonData) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settingsIntent.putExtra(getString(R.string.k_intent_ad), jsonData);
        startActivity(settingsIntent);
    }

    private void loadAdError(Throwable error) {
        SAAlert.getInstance().show(
                CreativesActivity.this,
                getString(R.string.page_creatives_popup_error_load_title),
                getString(R.string.page_creatives_popup_error_load_message),
                getString(R.string.page_creatives_popup_error_load_ok_button),
                null,
                false,
                0,
                (i, s) -> onBackPressed());
    }

    private void unsupportedFormatError(AdFormat format) {
        SAAlert.getInstance().show(
                CreativesActivity.this,
                getString(R.string.page_creatives_popup_error_format_title),
                getString(R.string.page_creatives_popup_error_format_message),
                getString(R.string.page_creatives_popup_error_format_ok_button),
                null,
                false,
                0,
                null);
    }
}
