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
    private Target target = null;
    private WebView webView;

    CreativesViewModel(SACreative creative) {
        this.creative = creative;
        AdAux adAux = new AdAux();
        mFormat = adAux.determineCreativeFormat(creative);
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

    void getIconBitmap (Context context, Listener listener) {

        switch (creative.format) {
            case image: {

                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        listener.gotBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        getLocalBitmap(context, listener);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        getLocalBitmap(context, listener);
                    }
                };

                Picasso.with(context).load(creative.details.image).into(target);

                break;
            }
            case rich: {

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        WebView.enableSlowWholeDocumentDraw();
                    }

                    final float scale =  SAUtils.getScaleFactor((Activity)context);

                    final int width = Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ? (int) (scale * creative.details.width) : creative.details.width;
                    final int height = Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ? (int) (scale * creative.details.height) : creative.details.height;
                    final int scaledWidth = creative.details.width / 4 ;
                    final  int scaledHeight = creative.details.height / 4;

                    Log.d("SuperAwesome", "For " + creative.name + " ==> (" + width + ", " + height + "); (" + scaledWidth + ", " + scaledHeight + ")");

                    webView = new WebView(context);
                    webView.setDrawingCacheEnabled(true);
                    webView.buildDrawingCache();
                    webView.setInitialScale(100);

                    String baseHtml = "<html><header><style>html, body, div { margin: 0px; padding: 0px; width: 100%; height: 100%; }</style></header><body>_CONTENT_</body></html>";
                    String iframeHtml = "<iframe style='padding:0;border:0;' width='100%' height='100%' src='" + creative.details.url + "'></iframe>";
                    String fullHtml = baseHtml.replace("_CONTENT_", iframeHtml);

                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(final WebView view, String url) {
                            super.onPageFinished(view, url);

                            Log.d("SuperAwesome", "FINISHED!!!");

                            final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
                            executor.schedule(() -> {

                                Log.d("SuperAwesome", "FINISHED 2!!!");

                                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                                final Canvas c = new Canvas(bitmap);
                                view.draw(c);
                                final Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 120, 80, true);

                                ((Activity)context).runOnUiThread(() -> listener.gotBitmap(scaled));

                            }, 1, TimeUnit.SECONDS);
                        }

                    });

                    webView.layout(0, 0, width, height);
                    webView.loadDataWithBaseURL(null, fullHtml, "text/html", "UTF-8", null);

                } catch (Exception ex) {
                    getIconBitmap(context, listener);
                }

                break;
            }
            case video:
            case tag:
            case appwall: {
                getLocalBitmap(context, listener);
                break;
            }
            case invalid: {
                listener.gotBitmap(null);
                break;
            }
        }

    }

    private void getLocalBitmap (Context context, Listener listener) {

        String drawable = null;

        switch (mFormat) {
            case unknown:
                drawable = null;
                break;
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
        }

        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(drawable, "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        listener.gotBitmap(bitmap);

    }

    public interface Listener {
        void gotBitmap (Bitmap bitmap);
    }
}
