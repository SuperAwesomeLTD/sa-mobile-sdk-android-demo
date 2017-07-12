package aademo.superawesome.tv.awesomeadsdemo.activities.main.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import aademo.superawesome.tv.awesomeadsdemo.R;
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
        return "Size: " + placement.getWidth() + "x" + placement.getWidth();
    }

    Drawable getPlacementIcon(Activity activity) {

        int icon = R.drawable.icon_placeholder;
        int width = placement.getWidth();
        int height = placement.getHeight();

        if (placement.getFormat() != null && placement.getFormat().equals("video")) {
            icon = R.drawable.video;
        }
        else if (placement.getFormat() != null) {

            if (width == 300 && height == 50) {
                icon = R.drawable.smallbanner;
            }
            if (width == 320 && height == 50) {
                icon = R.drawable.banner;
            }
            if (width == 728 && height == 90) {
                icon = R.drawable.leaderboard;
            }
            if (width == 300 && height == 250) {
                icon = R.drawable.mpu;
            }
            if (width == 320 && height == 480) {
                icon = R.drawable.small_inter_port;
            }
            if (width == 480 && height == 320) {
                icon = R.drawable.small_inter_land;
            }
            if (width == 768 && height == 1024) {
                icon = R.drawable.large_inter_port;
            }
            if (width == 1024 && height == 768) {
                icon = R.drawable.large_inter_land;
            }
            if (width == 120 && height == 600) {
                icon = R.drawable.imac120x600;
            }
            if (width == 160 && height == 600) {
                icon = R.drawable.imac160x600;
            }
            if (width == 300 && height == 600) {
                icon = R.drawable.imac300x600;
            }
            if (width == 468 && height == 60) {
                icon = R.drawable.imac468x60;
            }
            if (width == 970 && height == 90) {
                icon = R.drawable.imac970x90;
            }
            if (width == 970 && height == 250) {
                icon = R.drawable.imac970x250;
            }
        }

        return activity.getResources().getDrawable(icon);
    }
}
