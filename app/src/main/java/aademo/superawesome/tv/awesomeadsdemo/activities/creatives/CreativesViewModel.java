package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.graphics.Bitmap;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;

public class CreativesViewModel {

    private SACreative creative;
    private AdFormat mFormat;
    private Bitmap bitmap;

    CreativesViewModel(SACreative creative) {
        this.creative = creative;
        mFormat = AdFormat.fromCreative(creative);
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
}
