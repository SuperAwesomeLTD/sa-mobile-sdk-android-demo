package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.text.TextUtils;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;

public class CreativesViewModel implements Comparable {

    private static final String cdnUrl = "https://s3-eu-west-1.amazonaws.com/beta-ads-video-transcoded-thumbnails/";
    private SACreative creative;
    private AdFormat mFormat;
    private String remoteUrl;
    private int localUrl;

    CreativesViewModel(SACreative creative) {

        this.creative = creative;
        mFormat = AdFormat.fromCreative(creative);

        switch (creative.format) {
            case image: {
                remoteUrl = creative.details.image;
                break;
            }
            case video: {
                String videoUrl = creative.details.video;
                if (videoUrl != null) {
                    String[] parts = videoUrl.split("/");
                    if (parts.length > 0) {
                        String startName = parts[parts.length - 1].replace(".mp4", "-low-00001.jpg");
                        remoteUrl = cdnUrl + startName;
                    }
                }
                break;
            }
            default:break;
        }

        switch (mFormat) {
            case unknown:
                localUrl = R.drawable.icon_placeholder;
                break;
            case smallbanner:
                localUrl = R.drawable.smallbanner;
                break;
            case banner:
                localUrl = R.drawable.banner;
                break;
            case smallleaderboard:
                localUrl = R.drawable.imac468x60;
                break;
            case leaderboard:
                localUrl = R.drawable.leaderboard;
                break;
            case pushdown:
                localUrl = R.drawable.imac970x90;
                break;
            case billboard:
                localUrl = R.drawable.imac970x250;
                break;
            case skinnysky:
                localUrl = R.drawable.imac120x600;
                break;
            case sky:
                localUrl = R.drawable.imac160x600;
                break;
            case mpu:
                localUrl = R.drawable.mpu;
                break;
            case doublempu:
                localUrl = R.drawable.imac300x600;
                break;
            case mobile_portrait_interstitial:
                localUrl = R.drawable.small_inter_port;
                break;
            case mobile_landscape_interstitial:
                localUrl = R.drawable.small_inter_land;
                break;
            case tablet_portrait_interstitial:
                localUrl = R.drawable.large_inter_port;
                break;
            case tablet_landscape_interstitial:
                localUrl = R.drawable.large_inter_land;
                break;
            case video:
                localUrl = R.drawable.video;
                break;
            case appwall:
                localUrl = R.drawable.appwall;
                break;
        }
    }

    SACreative getCreative() {
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

    String getRemoteUrl () {
        return remoteUrl;
    }

    int getLocalUrl () {
        return localUrl;
    }

    String getOSTarget() {
        return "System: " + (creative.osTarget.size() == 0 ? "All" : TextUtils.join(",", creative.osTarget));
    }

    @Override
    public int compareTo(Object o) {
        CreativesViewModel om = (CreativesViewModel)o;
        return creative.name.compareToIgnoreCase(om.creative.name);
    }
}
