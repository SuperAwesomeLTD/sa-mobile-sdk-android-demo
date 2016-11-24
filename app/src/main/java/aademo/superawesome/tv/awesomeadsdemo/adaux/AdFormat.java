package aademo.superawesome.tv.awesomeadsdemo.adaux;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public enum AdFormat {
    unknown,
    banner,
    interstitial,
    video,
    gamewall;

    public static AdFormat fromInteger(int x) {
        switch(x) {
            case 0: return unknown;
            case 1: return banner;
            case 2: return interstitial;
            case 3: return video;
            case 4: return gamewall;
        }
        return null;
    }
}
