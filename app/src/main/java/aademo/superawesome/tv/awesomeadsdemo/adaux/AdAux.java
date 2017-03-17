package aademo.superawesome.tv.awesomeadsdemo.adaux;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SAResponse;

public class AdAux {

    public AdFormat determineAdType (SAResponse response) {

        // major error case
        if (response == null) {
            return AdFormat.unknown;
        }

        // appwall just reverts back to appwall
        if (response.format == SACreativeFormat.appwall) {
            return AdFormat.appwall;
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

                if (width == 300 && height == 50) {
                    return AdFormat.smallbanner;
                }
                if (width == 320 && height == 50) {
                    return AdFormat.normalbanner;
                }
                if (width == 728 && height == 90) {
                    return AdFormat.bigbanner;
                }
                if (width == 300 && height == 250) {
                    return AdFormat.mpu;
                }
                if ((width == 320 && height == 480) || (width == 480 && height == 320)) {
                    return AdFormat.mobile_portrait_interstitial;
                }
                if ((width == 768 && height == 1024) || (width == 1024 && height == 768)) {
                    return AdFormat.mobile_portrait_interstitial;
                }

                return AdFormat.unknown;
            } else {
                return AdFormat.unknown;
            }
        }
    }

    public AdFormat determineCreativeFormat (SACreative creative) {

        if (creative == null) {
            return AdFormat.unknown;
        } else {

            switch (creative.format) {
                case invalid: return AdFormat.unknown;
                case video: return AdFormat.video;
                case appwall: return AdFormat.appwall;
                case image:
                case tag:
                case rich: {

                    if (creative.details.format.contains("video")) {
                        return AdFormat.video;
                    } else {

                        int width = creative.details.width;
                        int height = creative.details.height;

                        if (width == 300 && height == 50) {
                            return AdFormat.smallbanner;
                        }
                        if (width == 320 && height == 50) {
                            return AdFormat.normalbanner;
                        }
                        if (width == 728 && height == 90) {
                            return AdFormat.bigbanner;
                        }
                        if (width == 300 && height == 250) {
                            return AdFormat.mpu;
                        }
                        if (width == 320 && height == 480) {
                            return AdFormat.mobile_portrait_interstitial;
                        }
                        if (width == 480 && height == 320) {
                            return AdFormat.mobile_landscape_interstitial;
                        }
                        if (width == 768 && height == 1024) {
                            return AdFormat.tablet_portrait_interstitial;
                        }
                        if (width == 1024 && height == 768) {
                            return AdFormat.tablet_landscape_interstitial;
                        }

                        return AdFormat.unknown;
                    }
                }
                default: return AdFormat.unknown;

            }
        }
    }
}
