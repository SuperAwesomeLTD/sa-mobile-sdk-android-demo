package aademo.superawesome.tv.awesomeadsdemo.aux;

import aademo.superawesome.tv.awesomeadsdemo.library.models.Placement;
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
    banner {
        @Override
        public String toString() {
            return "Mobile Leaderboard";
        }
    },
    smallleaderboard {
        @Override
        public String toString() {
            return "Small Leaderboard";
        }
    },
    leaderboard {
        @Override
        public String toString() {
            return "Tablet Leaderboard";
        }
    },
    pushdown {
        @Override
        public String toString() {
            return "Push Down";
        }
    },
    billboard {
        @Override
        public String toString() {
            return "Billboard";
        }
    },
    skinnysky {
        @Override
        public String toString() {
            return "Skinny Skyscraper";
        }
    },
    sky {
        @Override
        public String toString() {
            return "Skyscraper";
        }
    },
    mpu {
        @Override
        public String toString() {
            return "Tablet MPU";
        }
    },
    doublempu {
        @Override
        public String toString() {
            return "Double MPU";
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
            case 2: return banner;
            case 3: return smallleaderboard;
            case 4: return leaderboard;
            case 5: return pushdown;
            case 6: return billboard;
            case 7: return skinnysky;
            case 8: return sky;
            case 9: return mpu;
            case 10: return doublempu;
            case 11: return mobile_portrait_interstitial;
            case 12: return mobile_landscape_interstitial;
            case 13: return tablet_portrait_interstitial;
            case 14: return tablet_landscape_interstitial;
            case 15: return video;
            case 16: return appwall;
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

                if (width == 300 && height == 50) return smallbanner;
                if (width == 320 && height == 50) return banner;

                if (width == 468 && height == 60) return smallleaderboard;
                if (width == 728 && height == 90) return leaderboard;
                if (width == 970 && height == 90) return pushdown;
                if (width == 970 && height == 250) return billboard;

                if (width == 120 && height == 600) return skinnysky;
                if (width == 160 && height == 600) return sky;

                if (width == 300 && height == 250) return mpu;
                if (width == 300 && height == 600) return doublempu;

                if (width == 320 && height == 480) return mobile_portrait_interstitial;
                if (width == 400 && height == 600) return mobile_portrait_interstitial;
                if (width == 768 && height == 1024) return tablet_portrait_interstitial;
                if (width == 480 && height == 320) return mobile_landscape_interstitial;
                if (width == 600 && height == 400) return mobile_landscape_interstitial;
                if (width == 1024 && height == 768) return tablet_landscape_interstitial;
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

                        if (width == 300 && height == 50) return smallbanner;
                        if (width == 320 && height == 50) return banner;

                        if (width == 468 && height == 60) return smallleaderboard;
                        if (width == 728 && height == 90) return leaderboard;
                        if (width == 970 && height == 90) return pushdown;
                        if (width == 970 && height == 250) return billboard;

                        if (width == 120 && height == 600) return skinnysky;
                        if (width == 160 && height == 600) return sky;

                        if (width == 300 && height == 250) return mpu;
                        if (width == 300 && height == 600) return doublempu;

                        if (width == 320 && height == 480) return mobile_portrait_interstitial;
                        if (width == 400 && height == 600) return mobile_portrait_interstitial;
                        if (width == 768 && height == 1024) return tablet_portrait_interstitial;
                        if (width == 480 && height == 320) return mobile_landscape_interstitial;
                        if (width == 600 && height == 400) return mobile_landscape_interstitial;
                        if (width == 1024 && height == 768) return tablet_landscape_interstitial;
                        return AdFormat.unknown;
                    }
                }
                default: return AdFormat.unknown;

            }
        }
    }

    public static AdFormat fromPlacement(Placement placement) {

        if (placement.getFormat() != null && placement.getFormat().equals("video")) {
            return video;
        } else if (placement.getFormat() != null) {

            int width = placement.getWidth();
            int height = placement.getHeight();

            if (width == 300 && height == 50) return smallbanner;
            if (width == 320 && height == 50) return banner;

            if (width == 468 && height == 60) return smallleaderboard;
            if (width == 728 && height == 90) return leaderboard;
            if (width == 970 && height == 90) return pushdown;
            if (width == 970 && height == 250) return billboard;

            if (width == 120 && height == 600) return skinnysky;
            if (width == 160 && height == 600) return sky;

            if (width == 300 && height == 250) return mpu;
            if (width == 300 && height == 600) return doublempu;

            if (width == 320 && height == 480) return mobile_portrait_interstitial;
            if (width == 400 && height == 600) return mobile_portrait_interstitial;
            if (width == 768 && height == 1024) return tablet_portrait_interstitial;
            if (width == 480 && height == 320) return mobile_landscape_interstitial;
            if (width == 600 && height == 400) return mobile_landscape_interstitial;
            if (width == 1024 && height == 768) return tablet_landscape_interstitial;
            return AdFormat.unknown;
        } else {
            return AdFormat.unknown;
        }
    }

    public boolean isBannerType () {
        return this == smallbanner ||
                this == banner ||
                this == leaderboard ||
                this == smallleaderboard ||
                this == pushdown ||
                this == billboard ||
                this == skinnysky ||
                this == sky ||
                this == mpu ||
                this == doublempu;
    }

    public boolean isInterstitialType () {
        return this == mobile_portrait_interstitial ||
                this == mobile_landscape_interstitial ||
                this == tablet_portrait_interstitial ||
                this == tablet_landscape_interstitial;
    }

    public boolean isVideoType () {
        return this == video;
    }

    public boolean isAppWallType () {
        return this == appwall;
    }

    public boolean isUnknownType () {
        return this == unknown;
    }
}
