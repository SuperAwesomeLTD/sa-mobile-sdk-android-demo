package aademo.superawesome.tv.awesomeadsdemo.activities.main.demo;

class DemoPlacementViewModel {

    private int placementId = 0;
    private String imageSource = null;
    private String formatName = null;
    private String formatDetails = null;

    DemoPlacementViewModel(int placementId, String imageSource, String formatName, String formatDetails) {
        this.placementId = placementId;
        this.imageSource = imageSource;
        this.formatName = formatName;
        this.formatDetails = formatDetails;
    }

    int getPlacementId() {
        return placementId;
    }

    String getImageSource() {
        return imageSource;
    }

    String getFormatName() {
        return formatName;
    }

    String getFormatDetails() {
        return formatDetails;
    }
}
