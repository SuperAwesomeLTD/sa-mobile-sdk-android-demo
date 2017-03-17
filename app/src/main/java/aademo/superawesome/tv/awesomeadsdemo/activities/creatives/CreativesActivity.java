package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdRx;
import gabrielcoman.com.rxdatasource.RxDataSource;

public class CreativesActivity extends BaseActivity {

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

        ListView creativeListView = (ListView) findViewById(R.id.CreativesListView);

        getIntExtras(getString(R.string.k_intent_pid))
                .subscribe(placementId -> {

                    AdRx.loadCreative(CreativesActivity.this, placementId)
                            .map(CreativesViewModel::new)
                            .toList()
                            .subscribe(viewModels -> {

                                RxDataSource.create(CreativesActivity.this)
                                        .bindTo(creativeListView)
                                        .customiseRow(R.layout.row_creatives, CreativesViewModel.class, (view, model) -> {

                                            ((TextView) view.findViewById(R.id.CreativeName)).setText(model.getCreativeName());
                                            ((TextView) view.findViewById(R.id.CreativeFormat)).setText(model.getCreativeFormat());
                                            ((TextView) view.findViewById(R.id.CreativeSource)).setText(model.getCreativeSource());

                                            ImageView icon = (ImageView) view.findViewById(R.id.CreativeIcon);

                                            model.getIconBitmap(CreativesActivity.this, icon::setImageBitmap);

                                        })
                                        .update(viewModels);

                            }, throwable -> {
                                // error
                            });


                });

    }
}
