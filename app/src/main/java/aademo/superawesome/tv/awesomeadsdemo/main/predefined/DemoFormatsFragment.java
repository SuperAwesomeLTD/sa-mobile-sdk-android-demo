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
import aademo.superawesome.tv.awesomeadsdemo.settings.SettingsActivity;
import gabrielcoman.com.rxdatasource.RxDataSource;

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
        data.add(new DemoFormatsViewModel(
                30472,
                "smallbanner",
                getString(R.string.page_demo_table_row_1_title),
                getString(R.string.page_demo_table_row_1_details)
        ));
        data.add(new DemoFormatsViewModel(
                30471,
                "banner",
                getString(R.string.page_demo_table_row_2_title),
                getString(R.string.page_demo_table_row_2_details)
        ));
        data.add(new DemoFormatsViewModel(
                30475,
                "leaderboard",
                getString(R.string.page_demo_table_row_3_title),
                getString(R.string.page_demo_table_row_3_details)
        ));
        data.add(new DemoFormatsViewModel(
                30476,
                "mpu",
                getString(R.string.page_demo_table_row_4_title),
                getString(R.string.page_demo_table_row_4_details)
        ));
        data.add(new DemoFormatsViewModel(
                30473,
                "small_inter_port",
                getString(R.string.page_demo_table_row_5_title),
                getString(R.string.page_demo_table_row_5_details)
        ));
        data.add(new DemoFormatsViewModel(
                30474,
                "small_inter_land",
                getString(R.string.page_demo_table_row_6_title),
                getString(R.string.page_demo_table_row_6_details)
        ));
        data.add(new DemoFormatsViewModel(
                30477,
                "large_inter_port",
                getString(R.string.page_demo_table_row_7_title),
                getString(R.string.page_demo_table_row_7_details)
        ));
        data.add(new DemoFormatsViewModel(
                30478,
                "large_inter_land",
                getString(R.string.page_demo_table_row_8_title),
                getString(R.string.page_demo_table_row_8_details)
        ));
        data.add(new DemoFormatsViewModel(
                30479,
                "video",
                getString(R.string.page_demo_table_row_9_title),
                getString(R.string.page_demo_table_row_9_details)
        ));

        RxDataSource.from(getContext(), data)
                .bindTo(listView)
                .customiseRow(R.layout.row_demoformats, DemoFormatsViewModel.class, (viewModel, holderView) -> {

                    Context context = getContext();
                    ImageView iconImageView = (ImageView) holderView.findViewById(R.id.DemoFormatsIcon);
                    Resources resources = container.getResources();
                    final int resourceId = resources.getIdentifier(viewModel.getImageSource(), "drawable", context.getPackageName());
                    iconImageView.setImageDrawable(resources.getDrawable(resourceId));

                    TextView nameTextView = (TextView) holderView.findViewById(R.id.DemoFormatsTitle);
                    nameTextView.setText(viewModel.getFormatName() != null ? viewModel.getFormatName() : context.getString(R.string.page_demo_table_row_def_title));

                    TextView detailsTextView = (TextView) holderView.findViewById(R.id.DemoFormatsDetails);
                    detailsTextView.setText(viewModel.getFormatDetails() != null ? viewModel.getFormatDetails() : context.getString(R.string.page_demo_table_row_def_details));

                })
                .onRowClick(R.layout.row_demoformats, (integer, viewModel) -> startActivity(viewModel.getPlacementId()))
                .update();

        return view;
    }

    public void startActivity (int placementId) {
        Intent settings = new Intent(getActivity(), SettingsActivity.class);
        settings.putExtra(getString(R.string.k_intent_pid), placementId);
        settings.putExtra(getString(R.string.k_intent_test), true);
        getActivity().startActivity(settings);
    }
}
