package aademo.superawesome.tv.awesomeadsdemo.activities.display;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import butterknife.BindView;
import butterknife.ButterKnife;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.sdk.views.SABannerAd;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R.id.DisplayToolbar) Toolbar toolbar;
    @BindView(R.id.Banner1) SABannerAd normalAndSmallBanner;
    @BindView(R.id.Banner2) SABannerAd bigBanner;
    @BindView(R.id.Banner3) SABannerAd mpuBanner;

    private static SAResponse response;
    private static boolean parentalGate;
    private static boolean background;
    private static AdFormat format;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        switch (format) {

            case smallbanner:
            case banner: {
                normalAndSmallBanner.setParentalGate(parentalGate);
                normalAndSmallBanner.setColor(background);
                normalAndSmallBanner.setAd(response.ads.get(0));
                normalAndSmallBanner.play(this);
                break;
            }
            case smallleaderboard:
            case leaderboard:
            case pushdown:
            case billboard:{
                bigBanner.setParentalGate(parentalGate);
                bigBanner.setColor(background);
                bigBanner.setAd(response.ads.get(0));
                bigBanner.play(this);
                break;
            }
            case skinnysky:
            case sky:
            case mpu:
            case doublempu:{
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
