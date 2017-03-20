package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;

public class LocalBitmap {

    public Bitmap getBitmap (Context context, AdFormat format) {

        String drawable;

        switch (format) {
            case smallbanner:
                drawable = "smallbanner";
                break;
            case normalbanner:
                drawable = "banner";
                break;
            case bigbanner:
                drawable = "leaderboard";
                break;
            case mpu:
                drawable = "mpu";
                break;
            case mobile_portrait_interstitial:
                drawable = "small_inter_port";
                break;
            case mobile_landscape_interstitial:
                drawable = "small_inter_land";
                break;
            case tablet_portrait_interstitial:
                drawable = "large_inter_port";
                break;
            case tablet_landscape_interstitial:
                drawable = "large_inter_land";
                break;
            case video:
                drawable = "video";
                break;
            case appwall:
                drawable = "appwall";
                break;
            case unknown:
            default:
                drawable = "icon_placeholder";
                break;
        }

        Resources res = context.getResources();
        int resourceId = res.getIdentifier(drawable, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(res, resourceId);
    }

}
