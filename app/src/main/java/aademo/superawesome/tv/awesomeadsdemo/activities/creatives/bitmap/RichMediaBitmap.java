package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RichMediaBitmap extends WebViewClient {

    private WebView webView;
    private Handler handler;
    private int width = 0;
    private int height = 0;
    private int scaledW = 0;
    private int scaledH = 0;
    private BitmapListener listener;

    public void getBitmap (Context context, String url, String content, int width, int height, BitmapListener listener) {

        // get all needed params
        this.width = width;
        this.height = height;
        this.scaledW = width / 4;
        this.scaledH = height / 4;
        this.listener = listener;

        // get the html
        String base = "<html><header><style>html, body, div { margin: 0px; padding: 0px; width: 100%; height: 100%; }</style></header><body>_CONTENT_</body></html>";
        String html = base.replace("_CONTENT_", content);

        // for Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }

        // start the web view
        webView = new WebView(context);
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        webView.setInitialScale(100);
        webView.setWebViewClient(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        webView.layout(0, 0, width, height);
        webView.loadDataWithBaseURL(url, html, "text/html", "UTF-8", null);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        handler = new Handler();
        handler.postDelayed(() -> {
            try {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                view.draw(new Canvas(bitmap));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
                listener.gotBitmap(scaled);
            } catch (Exception e) {
                listener.noBitmap();
            }

        }, 1000);
    }
}
