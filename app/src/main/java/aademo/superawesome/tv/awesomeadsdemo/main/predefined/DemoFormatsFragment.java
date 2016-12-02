package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericDataSource;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericRow;
import aademo.superawesome.tv.awesomeadsdemo.settings.SettingsActivity;

public class DemoFormatsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demoformats, container, false);

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);

        List<DemoFormatsViewModel> data = new ArrayList<>();
        data.add(new DemoFormatsViewModel(30472, "smallbanner", "Mobile Small Leaderboard", "Size: 300x50"));
        data.add(new DemoFormatsViewModel(30471, "banner", "Mobile Leaderboard", "Size: 320x50"));
        data.add(new DemoFormatsViewModel(30475, "leaderboard", "Tablet Leaderboard", "Size: 728x90"));
        data.add(new DemoFormatsViewModel(30476, "mpu", "Tablet MPU", "Size: 300x250"));
        data.add(new DemoFormatsViewModel(30473, "small_inter_port", "Mobile Interstitial Portrait", "Size: 320x480"));
        data.add(new DemoFormatsViewModel(30474, "small_inter_land", "Mobile Interstitial Landscape", "Size: 480x320"));
        data.add(new DemoFormatsViewModel(30477, "large_inter_port", "Tablet Interstitial Portrait", "Size: 768x1024"));
        data.add(new DemoFormatsViewModel(30478, "large_inter_land", "Tablet Interstitial Landscape", "Size: 1024x768"));
        data.add(new DemoFormatsViewModel(30479, "video", "Mobile Video", "Size: 600x480"));

        GenericDataSource <DemoFormatsViewModel, GenericRow> source = new GenericDataSource<>(getContext());
        source.bindTo(listView, R.layout.row_demoformats)
                .withDataSet(data)
                .match((viewModel, row) -> {

                    Context context = getContext();
                    View v = row.getHolderView();

                    ImageView iconImageView = (ImageView) v.findViewById(R.id.DemoFormatsIcon);
                    TextView nameTextView = (TextView) v.findViewById(R.id.DemoFormatsTitle);
                    TextView detailsTextView = (TextView) v.findViewById(R.id.DemoFormatsDetails);

                    if (iconImageView != null) {
                        Resources resources = container.getResources();
                        final int resourceId = resources.getIdentifier(viewModel.getImageSource(), "drawable", context.getPackageName());
                        iconImageView.setImageDrawable(resources.getDrawable(resourceId));
                    }
                    if (nameTextView != null) {
                        nameTextView.setText(viewModel.getFormatName() != null ? viewModel.getFormatName() : context.getString(R.string.demo_row_title_default));
                    }
                    if (detailsTextView != null) {
                        detailsTextView.setText(viewModel.getFormatDetails() != null ? viewModel.getFormatDetails() : context.getString(R.string.demo_row_details_default));
                    }

                    return v;
                })
                .onRowClick((integer, demoFormatsRow) -> {
                    startActivity(demoFormatsRow.getPlacementId());
                });

//        ((GenericJimmySource<DemoFormatsViewModel>)GenericJimmySource.fromData(data))
//                .bindTo(getContext(), listView, R.layout.row_demoformats)
//                .match(new Func2<DemoFormatsViewModel, GenericRow, View>() {
//                    @Override
//                    public View call(DemoFormatsViewModel demoFormatsViewModel, GenericRow genericRow) {
//                        return null;
//                    }
//                });

        return view;
    }

    public void startActivity (int placementId) {
        Intent settings = new Intent(getActivity(), SettingsActivity.class);
        settings.putExtra(getString(R.string.k_intent_pid), placementId);
        settings.putExtra(getString(R.string.k_intent_test), true);
        getActivity().startActivity(settings);
    }
}
