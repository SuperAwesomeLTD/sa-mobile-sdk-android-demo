package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;

public class CreativeAux {

    private WebView webView;
    private Target target = null;
    private Handler handler = null;

    private void getWebViewBitmap (Context context, String content, int width, int height, int scaledW, int scaledH,  Listener listener) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }

        webView = new WebView(context);
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        webView.setInitialScale(100);

        String baseHtml = "<html><header><style>html, body, div { margin: 0px; padding: 0px; width: 100%; height: 100%; }</style></header><body>_CONTENT_</body></html>";
        String fullHtml = baseHtml.replace("_CONTENT_", content);

        handler = new Handler();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        final Canvas c = new Canvas(bitmap);
                        view.draw(c);
                        final Bitmap scaled = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
                        listener.gotBitmap(scaled);
                    }
                }, 1000);
            }

        });

        webView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        webView.layout(0, 0, width, height);
        webView.loadDataWithBaseURL(null, fullHtml, "text/html", "UTF-8", null);

    }

    void getRichMediaBitmap(Context context, String url, int width, int height, int scaledW, int scaledH, Listener listener) {
        String iframeHtml = "<iframe style='padding:0;border:0;' width='100%' height='100%' src='" + url + "'></iframe>";
        getWebViewBitmap(context, iframeHtml, width, height, scaledW, scaledH, listener);
    }

    public void getTagBitmap (Context context, String tag, int width, int height, int scaledW, int scaledH,  Listener listener) {
        String tagString = tag;
        tagString = tagString.replace("[keywords]", "");
        tagString = tagString.replace("[timestamp]", "");
        tagString = tagString.replace("target=\"_blank\"", "");
        tagString = tagString.replace("“", "\"");
        tagString = tagString.replace("\n", "");
        getWebViewBitmap(context, tagString, width, height, scaledW, scaledH, listener);
    }

    void getUrlBitmap(Context context, String url, AdFormat format, Listener listener) {

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.gotBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                getLocalBitmap(context, format, listener);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                getLocalBitmap(context, format, listener);
            }
        };

        Picasso.with(context).load(url).into(target);
    }

    void getLocalBitmap (Context context, AdFormat mFormat, Listener listener) {

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

    interface Listener {
        void gotBitmap (Bitmap bitmap);
    }

}
