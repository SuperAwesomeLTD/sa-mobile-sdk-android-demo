package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdAux;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.sautils.SAUtils;

class CreativesViewModel {

    private SACreative creative;
    private AdFormat mFormat;
    private CreativeAux aux;

    CreativesViewModel(SACreative creative) {
        this.creative = creative;
        AdAux adAux = new AdAux();
        mFormat = adAux.determineCreativeFormat(creative);
        aux = new CreativeAux();
    }

    public SACreative getCreative() {
        return creative;
    }

    public AdFormat getFormat() {
        return mFormat;
    }

    String getCreativeName() {
        return creative.name;
    }

    String getCreativeFormat () {
        return "Format: " + mFormat.toString();
    }

    String getCreativeSource () {
        String source = "Source: ";
        switch (creative.format) {
            case invalid: source += "Unknown"; break;
            case image: source += "Image"; break;
            case video: source += "MP4 Video"; break;
            case rich: source += "Rich Media"; break;
            case tag: source += "3rd Party Tag"; break;
            case appwall: source += "App Wall"; break;
        }
        return source;
    }

    void getIconBitmap (Context context, CreativeAux.Listener listener) {

        switch (creative.format) {
            case image: {
                aux.getUrlBitmap(context, creative.details.image, mFormat, listener);
                break;
            }
            case rich: {
                aux.getRichMediaBitmap(
                        context,
                        creative.details.url,
                        creative.details.width,
                        creative.details.height,
                        creative.details.width / 4,
                        creative.details.height/ 4,
                        listener);
                break;
            }
            case tag: {
                aux.getTagBitmap(
                        context,
                        creative.details.tag,
                        creative.details.width,
                        creative.details.height,
                        creative.details.width / 4,
                        creative.details.height/ 4,
                        listener);
                break;
            }
            case video:
            case appwall: {
                aux.getLocalBitmap(context, mFormat, listener);
                break;
            }
            case invalid: {
                listener.gotBitmap(null);
                break;
            }
        }

    }
}
