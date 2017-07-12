package aademo.superawesome.tv.awesomeadsdemo.activities.main.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.settings.SettingsActivity;
import aademo.superawesome.tv.awesomeadsdemo.aux.AdRx;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SALoadScreen;

public class DemoPlacementFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);

        DemoPlacementProvider provider = new DemoPlacementProvider();
        provider.getModels(getContext())
                .toList()
                .subscribe(viewModels -> {

                    RxDataSource.create(getContext())
                            .bindTo(listView)
                            .customiseRow(R.layout.row_demoplacement, DemoPlacementViewModel.class, (i, view1, viewModel) -> {

                                 view1.setBackgroundColor(i % 2 == 0 ? 0xFFF7F7F7 : Color.WHITE);

                                Context context = getContext();
                                Resources resources = container.getResources();
                                int resourceId = resources.getIdentifier(viewModel.getImageSource(), "drawable", context.getPackageName());
                                Drawable drawable = resources.getDrawable(resourceId);

                                ((ImageView) view1.findViewById(R.id.DemoFormatsIcon)).setImageDrawable(drawable);
                                ((TextView) view1.findViewById(R.id.DemoFormatsTitle)).setText(viewModel.getFormatName());
                                ((TextView) view1.findViewById(R.id.DemoFormatsDetails)).setText(viewModel.getFormatDetails());

                            })
                            .onRowClick(R.layout.row_demoplacement, (Action2<Integer, DemoPlacementViewModel>) (integer, demoPlacementViewModel) -> {
                                loadAd(demoPlacementViewModel.getPlacementId());
                            })
                            .update(viewModels);

                });

        return view;
    }

    private void loadAd (int placementId) {

        SALoadScreen.getInstance().show(getActivity());

        AdRx.loadTestAd(getContext(), placementId)
                .subscribe(ad -> {

                    SALoadScreen.getInstance().hide();

                    Intent settings = new Intent(getActivity(), SettingsActivity.class);
                    settings.putExtra(getActivity().getString(R.string.k_intent_ad), ad.writeToJson().toString());
                    getActivity().startActivity(settings);

                }, throwable -> {
                    SALoadScreen.getInstance().hide();
                    showError();
                });
    }

    private void showError () {
        SAAlert.getInstance().show(
                getActivity(),
                getString(R.string.page_demo_popup_error_title),
                getString(R.string.page_demo_popup_error_message),
                getString(R.string.page_demo_popup_error_ok_button),
                null,
                false,
                0,
                null);
    }

}
