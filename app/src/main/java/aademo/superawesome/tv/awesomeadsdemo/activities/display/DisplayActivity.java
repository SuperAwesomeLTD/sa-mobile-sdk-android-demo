package aademo.superawesome.tv.awesomeadsdemo.activities.display;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdRx;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.sdk.views.SABannerAd;
import tv.superawesome.sdk.views.SAEvent;
import tv.superawesome.sdk.views.SAInterstitialAd;

public class DisplayActivity extends AppCompatActivity {

    private static SAResponse response;
    private static boolean parentalGate;
    private static boolean background;
    private static AdFormat format;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.DisplayToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        SABannerAd normalAndSmallBanner = (SABannerAd) findViewById(R.id.Banner1);
        SABannerAd bigBanner = (SABannerAd) findViewById(R.id.Banner2);
        SABannerAd mpuBanner = (SABannerAd) findViewById(R.id.Banner3);

        switch (format) {

            case smallbanner:
            case normalbanner: {
                normalAndSmallBanner.setParentalGate(parentalGate);
                normalAndSmallBanner.setColor(background);
                normalAndSmallBanner.setAd(response.ads.get(0));
                normalAndSmallBanner.play(this);
                break;
            }
            case bigbanner: {
                bigBanner.setParentalGate(parentalGate);
                bigBanner.setColor(background);
                bigBanner.setAd(response.ads.get(0));
                bigBanner.play(this);
                break;
            }
            case mpu: {
                mpuBanner.setParentalGate(parentalGate);
                mpuBanner.setColor(background);
                mpuBanner.setAd(response.ads.get(0));
                mpuBanner.play(this);
                break;
            }
            default:
                // do nothing
                break;
        }
    }

    public static void setResponse (SAResponse r) {
        response = r;
    }

    public static void setParentalGate (boolean pg) {
        parentalGate = pg;
    }

    public static void setBackground (boolean bg) {
        background = bg;
    }

    public static void setFormat (AdFormat fm) {
        format = fm;
    }

    public static void play (Context context) {
        Intent intent = new Intent(context, DisplayActivity.class);
        context.startActivity(intent);
    }
}
