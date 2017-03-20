package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Context;
import android.graphics.Bitmap;

import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.BitmapListener;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.LocalBitmap;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.RemoteBitmap;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.VideoBitmap;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.WebViewBitmap;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdAux;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;

class CreativesViewModel {

    private SACreative creative;
    private AdFormat mFormat;
    private LocalBitmap localBitmap;
    private RemoteBitmap remoteBitmap;
    private VideoBitmap videoBitmap;
    private WebViewBitmap webViewBitmap;

    CreativesViewModel(SACreative creative) {
        this.creative = creative;
        AdAux adAux = new AdAux();
        mFormat = adAux.determineCreativeFormat(creative);
        localBitmap = new LocalBitmap();
        remoteBitmap = new RemoteBitmap();
        videoBitmap = new VideoBitmap();
        webViewBitmap = new WebViewBitmap();
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

                remoteBitmap.getBitmap(context, creative.details.image, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(localBitmap.getBitmap(context, mFormat));
                    }
                });
                break;
            }
            case rich: {

                String html = "<iframe style='padding:0;border:0;' width='100%' height='100%' src='" + creative.details.url + "'></iframe>";

                webViewBitmap.getBitmap(context, html, creative.details.width, creative.details.height, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(localBitmap.getBitmap(context, mFormat));
                    }
                });

                break;
            }
            case tag: {

                webViewBitmap.getBitmap(context, creative.details.tag, creative.details.width, creative.details.height, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(localBitmap.getBitmap(context, mFormat));
                    }
                });

                break;
            }
            case video:{

                videoBitmap.getBitmap(context, creative.details.video, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(localBitmap.getBitmap(context, mFormat));
                    }
                });

                break;
            }
            case invalid:
            case appwall: {
                listener.gotBitmap(localBitmap.getBitmap(context, mFormat));
                break;
            }
        }
    }

    interface Listener {
        void gotBitmap (Bitmap bitmap);
    }
}
