package aademo.superawesome.tv.awesomeadsdemo.activities.main;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Placement;

class PlacementViewModel {

    private Placement placement;

    PlacementViewModel(Placement placement) {
        this.placement = placement;
    }

    Placement getPlacement() {
        return placement;
    }

    String getPlacementName() {
        if (placement.getName() != null) {
            return placement.getName();
        } else {
            return "N/A";
        }
    }

    String getPlacementID() {
        return "Placement: " + placement.getId();
    }

    String getPlacementSize() {
        return "Size: " + placement.getWidth() + "x" + placement.getHeight();
    }

    int getPlacementIcon () {

        AdFormat format = AdFormat.fromPlacement(placement);

        switch (format) {
            case unknown:
                return R.drawable.icon_placeholder;
            case smallbanner:
                return R.drawable.smallbanner;
            case banner:
                return R.drawable.banner;
            case smallleaderboard:
                return R.drawable.imac468x60;
            case leaderboard:
                return R.drawable.leaderboard;
            case pushdown:
                return R.drawable.imac970x90;
            case billboard:
                return R.drawable.imac970x250;
            case skinnysky:
                return R.drawable.imac120x600;
            case sky:
                return R.drawable.imac160x600;
            case mpu:
                return R.drawable.mpu;
            case doublempu:
                return R.drawable.imac300x600;
            case mobile_portrait_interstitial:
                return R.drawable.small_inter_port;
            case mobile_landscape_interstitial:
                return R.drawable.small_inter_land;
            case tablet_portrait_interstitial:
                return R.drawable.large_inter_port;
            case tablet_landscape_interstitial:
                return R.drawable.large_inter_land;
            case video:
                return R.drawable.video;
            case appwall:
                return R.drawable.appwall;
        }

        return R.drawable.icon_placeholder;
    }
}
