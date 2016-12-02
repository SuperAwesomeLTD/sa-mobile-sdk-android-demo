package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

public class DemoFormatsViewModel {

    private int placementId = 0;
    private String imageSource = null;
    private String formatName = null;
    private String formatDetails = null;

    public DemoFormatsViewModel(int placementId, String imageSource, String formatName, String formatDetails) {
        this.placementId = placementId;
        this.imageSource = imageSource;
        this.formatName = formatName;
        this.formatDetails = formatDetails;
    }

    public int getPlacementId () {
        return placementId;
    }

    public String getImageSource () {
        return imageSource;
    }

    public String getFormatName () {
        return formatName;
    }

    public String getFormatDetails () {
        return formatDetails;
    }
}
