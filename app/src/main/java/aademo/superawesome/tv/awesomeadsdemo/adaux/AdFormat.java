package aademo.superawesome.tv.awesomeadsdemo.adaux;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public enum AdFormat {
    unknown,
    smallbanner,
    normalbanner,
    bigbanner,
    mpu,
    interstitial,
    video,
    gamewall;

    public static AdFormat fromInteger(int x) {
        switch(x) {
            case 0: return unknown;
            case 1: return smallbanner;
            case 2: return normalbanner;
            case 3: return bigbanner;
            case 4: return mpu;
            case 5: return interstitial;
            case 6: return video;
            case 7: return gamewall;
        }
        return null;
    }
}
