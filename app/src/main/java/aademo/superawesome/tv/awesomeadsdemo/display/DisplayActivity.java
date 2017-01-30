package aademo.superawesome.tv.awesomeadsdemo.display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdRx;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterface;

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
        SABannerAd normalAndSmallBanner = (SABannerAd) findViewById(R.id.Banner1);
        SABannerAd bigBanner = (SABannerAd) findViewById(R.id.Banner2);
        SABannerAd mpuBanner = (SABannerAd) findViewById(R.id.Banner3);

        // create a publish subject
        PublishSubject<AdFormat> publishSubject = PublishSubject.create();

        publishSubject
                .asObservable()
                .subscribe(adFormat1 -> {

                    switch (adFormat1) {

                        case smallbanner: case normalbanner: {

                            normalAndSmallBanner.setParentalGate(pg);
                            normalAndSmallBanner.setColor(bg);
                            normalAndSmallBanner.setTestMode(test);

                            AdRx.loadBanner(normalAndSmallBanner, placementId)
                                    .filter(saEvent -> saEvent == SAEvent.adLoaded)
                                    .subscribe(saEvent -> normalAndSmallBanner.play(DisplayActivity.this), new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            Log.d("SuperAwesome", throwable.toString());
                                        }
                                    });

                            break;
                        }
                        case bigbanner: {

                            bigBanner.setParentalGate(pg);
                            bigBanner.setColor(bg);
                            bigBanner.setTestMode(test);

                            AdRx.loadBanner(bigBanner, placementId)
                                    .filter(saEvent -> saEvent == SAEvent.adLoaded)
                                    .subscribe(saEvent -> bigBanner.play(DisplayActivity.this));

                            break;
                        }
                        case mpu: {

                            mpuBanner.setParentalGate(pg);
                            mpuBanner.setColor(bg);
                            mpuBanner.setTestMode(test);

                            AdRx.loadBanner(mpuBanner, placementId)
                                    .filter(saEvent -> saEvent == SAEvent.adLoaded)
                                    .subscribe(saEvent -> mpuBanner.play(DisplayActivity.this));

                            break;
                        }
                        default:break;
                    }

                });

        publishSubject.onNext(adFormat);
    }
}
