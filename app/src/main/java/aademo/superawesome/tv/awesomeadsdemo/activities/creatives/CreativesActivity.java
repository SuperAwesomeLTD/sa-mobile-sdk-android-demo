package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.settings.SettingsActivity;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdRx;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sautils.SAAlert;

public class CreativesActivity extends BaseActivity {

    private List<SACreative> originals = new ArrayList<>();
    private CreativesAdapter adapter;

    @BindView(R.id.CreativesRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.CreativesSearch) EditText editText;
    @BindView(R.id.CreativesToolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatives);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getIntExtras(getString(R.string.k_intent_pid))
                .subscribe(placementId -> {

                    AdRx.loadCreative(CreativesActivity.this, placementId)
                            .toList()
                            .subscribe(saCreatives -> {

                                //
                                // copy
                                originals = saCreatives;

                                //
                                // transform to view models
                                List<CreativesViewModel> models = search(null, originals);

                                adapter = new CreativesAdapter(CreativesActivity.this, models);

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

                                RxTextView.textChanges(editText)
                                        .map(charSequence -> charSequence.toString().trim())
                                        .subscribe(searchTerm -> {
                                            List<CreativesViewModel> newModels = search(searchTerm, originals);
                                            adapter.updateData(newModels);
                                        }, throwable -> {
                                            // abc
                                        });

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

    private List<CreativesViewModel> search (String searchTerm, List<SACreative> original) {

        List<CreativesViewModel> models = new ArrayList<>();

        for (SACreative creative : original) {
            if (searchTerm == null || searchTerm.isEmpty()) {
                models.add(new CreativesViewModel(creative));
            } else {
                if (creative.name.toLowerCase().contains(searchTerm.toLowerCase())) {
                    models.add(new CreativesViewModel(creative));
                }
            }
        }

        Collections.sort(models);

        return models;
    }
}
