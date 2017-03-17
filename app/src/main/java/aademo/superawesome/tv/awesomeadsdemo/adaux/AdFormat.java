package aademo.superawesome.tv.awesomeadsdemo.adaux;

import java.util.TooManyListenersException;

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
}
