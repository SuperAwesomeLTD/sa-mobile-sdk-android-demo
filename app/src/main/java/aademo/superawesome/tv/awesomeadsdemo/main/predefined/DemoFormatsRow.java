package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericViewModelInterface;

public class DemoFormatsRow implements GenericViewModelInterface {

    private int placementId = 0;
    private String imageSource = null;
    private String formatName = null;
    private String formatDetails = null;

    public DemoFormatsRow(int placementId, String imageSource, String formatName, String formatDetails) {
        this.placementId = placementId;
        this.imageSource = imageSource;
        this.formatName = formatName;
        this.formatDetails = formatDetails;
    }

    @Override
    public View representationAsRow(Context context, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.row_demoformats, parent, false);
        }

        ImageView iconImageView = (ImageView) v.findViewById(R.id.DemoFormatsIcon);
        TextView nameTextView = (TextView) v.findViewById(R.id.DemoFormatsTitle);
        TextView detailsTextView = (TextView) v.findViewById(R.id.DemoFormatsDetails);

        if (iconImageView != null) {
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(imageSource, "drawable", context.getPackageName());
            iconImageView.setImageDrawable(resources.getDrawable(resourceId));
        }
        if (nameTextView != null) {
            nameTextView.setText(formatName != null ? formatName : context.getString(R.string.demo_row_title_default));
        }
        if (detailsTextView != null) {
            detailsTextView.setText(formatDetails != null ? formatDetails : context.getString(R.string.demo_row_details_default));
        }

        return v;
    }

    public int getPlacementId () {
        return placementId;
    }
}
