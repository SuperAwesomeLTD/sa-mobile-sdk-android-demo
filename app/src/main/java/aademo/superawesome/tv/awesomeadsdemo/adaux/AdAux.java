package aademo.superawesome.tv.awesomeadsdemo.adaux;

import tv.superawesome.lib.samodelspace.SAAd;
import tv.superawesome.lib.samodelspace.SACreativeFormat;
import tv.superawesome.lib.samodelspace.SAResponse;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public class AdAux {

    public AdFormat determineAdType (SAResponse response) {

        // major error case
        if (response == null) {
            return AdFormat.unknown;
        }

        // gamewall just reverts back to gamewall
        if (response.format == SACreativeFormat.gamewall) {
            return AdFormat.gamewall;
        }
        // video just reverts back to video
        else if (response.format == SACreativeFormat.video) {
            return AdFormat.video;
        }
        // invalid does the same
        else if (response.format == SACreativeFormat.invalid) {
            return AdFormat.unknown;
        }
        // image, rich media and tag
        else {
            if (response.ads.size() > 0) {
                // get the first ad
                SAAd ad = response.ads.get(0);
                int width = ad.creative.details.width;
                int height = ad.creative.details.height;

                if ((width == 300 && height == 50) || (width == 320 && height == 50) || (width == 300 && height == 250)) {
                    return AdFormat.banner;
                }
                if ((width == 320 && height == 480) || (width == 480 && height == 320)) {
                    return AdFormat.interstitial;
                }
                if ((width == 768 && height == 1024) || (width == 1024 && height == 768)) {
                    return AdFormat.interstitial;
                }

                return AdFormat.unknown;
            } else {
                return AdFormat.unknown;
            }
        }
    }

}
