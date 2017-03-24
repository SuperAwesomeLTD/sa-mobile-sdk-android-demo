package aademo.superawesome.tv.awesomeadsdemo.aux;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SAResponse;

public enum AdFormat {
    unknown {
        @Override
        public String toString() {
            return "Unknown";
        }
    },
    smallbanner {
        @Override
        public String toString() {
            return "Mobile Small Leaderboard";
        }
    },
    normalbanner {
        @Override
        public String toString() {
            return "Mobile Leaderboard";
        }
    },
    bigbanner {
        @Override
        public String toString() {
            return "Tablet Leaderboard";
        }
    },
    mpu {
        @Override
        public String toString() {
            return "Tablet MPU";
        }
    },
    mobile_portrait_interstitial {
        @Override
        public String toString() {
            return "Mobile Interstitial Portrait";
        }
    },
    mobile_landscape_interstitial {
        @Override
        public String toString() {
            return "Mobile Interstitial Landscape";
        }
    },
    tablet_portrait_interstitial {
        @Override
        public String toString() {
            return "Tablet Interstitial Portrait";
        }
    },
    tablet_landscape_interstitial {
        @Override
        public String toString() {
            return "Tablet Interstitial Landscape";
        }
    },
    video {
        @Override
        public String toString() {
            return "Mobile Video";
        }
    },
    appwall {
        @Override
        public String toString() {
            return "App Wall";
        }
    };

    public static AdFormat fromInteger(int x) {
        switch(x) {
            case 0: return unknown;
            case 1: return smallbanner;
            case 2: return normalbanner;
            case 3: return bigbanner;
            case 4: return mpu;
            case 5: return mobile_portrait_interstitial;
            case 6: return mobile_landscape_interstitial;
            case 7: return tablet_portrait_interstitial;
            case 8: return tablet_landscape_interstitial;
            case 9: return video;
            case 10: return appwall;
        }
        return null;
    }

    public static AdFormat fromResponse (SAResponse response) {

        if (response == null) {
            return AdFormat.unknown;
        }

        if (response.format == SACreativeFormat.appwall) {
            return AdFormat.appwall;
        }
        else if (response.format == SACreativeFormat.video) {
            return AdFormat.video;
        }
        else if (response.format == SACreativeFormat.invalid) {
            return AdFormat.unknown;
        }
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

    public static AdFormat fromCreative (SACreative creative) {

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

    public static boolean isBannerType (AdFormat format) {
        return format == smallbanner ||
                format == bigbanner ||
                format == normalbanner ||
                format == mpu;
    }

    public static boolean isInterstitialType (AdFormat format) {
        return format == mobile_portrait_interstitial ||
                format == mobile_landscape_interstitial ||
                format == tablet_portrait_interstitial ||
                format == tablet_landscape_interstitial;
    }

    public static boolean isVideoType (AdFormat format) {
        return format == video;
    }

    public static boolean isAppWallType (AdFormat format) {
        return format == appwall;
    }

    public static boolean isUnknownType (AdFormat format) {
        return format == unknown;
    }
}
