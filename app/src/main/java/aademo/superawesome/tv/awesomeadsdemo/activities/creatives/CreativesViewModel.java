package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.graphics.Bitmap;

import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;

public class CreativesViewModel {

    private static final String cdnUrl = "https://s3-eu-west-1.amazonaws.com/beta-ads-video-transcoded-thumbnails/";
    private SACreative creative;
    private AdFormat mFormat;
    private Bitmap bitmap;
    private String bitmapUrl;

    CreativesViewModel(SACreative creative) {

        this.creative = creative;
        mFormat = AdFormat.fromCreative(creative);

        switch (creative.format) {
            case image: {
                bitmapUrl = creative.details.image;
                break;
            }
            case video: {
                String videoUrl = creative.details.video;
                if (videoUrl != null) {
                    String[] parts = videoUrl.split("/");
                    if (parts.length > 0) {
                        String name = parts[parts.length - 1].replace(".mp4", "-low-00001.jpg");
                        bitmapUrl = cdnUrl + name;
                    }
                }
                break;
            }
            case invalid:
            case rich:
            case tag:
            case appwall: {
                // do nothing
                break;
            }
        }
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBitmapUrl () {
        return bitmapUrl;
    }
}
