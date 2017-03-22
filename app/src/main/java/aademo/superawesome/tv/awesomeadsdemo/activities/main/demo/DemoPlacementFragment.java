package aademo.superawesome.tv.awesomeadsdemo.activities.main.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdRx;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;
import tv.superawesome.lib.sautils.SAAlert;

public class DemoPlacementFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demoplacement, container, false);

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);

        DemoPlacementProvider provider = new DemoPlacementProvider();
        provider.getModels(getContext())
                .toList()
                .subscribe(viewModels -> {

                    RxDataSource.create(getContext())
                            .bindTo(listView)
                            .customiseRow(R.layout.row_demoplacement, DemoPlacementViewModel.class, (view1, viewModel) -> {

                                Context context = getContext();
                                Resources resources = container.getResources();
                                int resourceId = resources.getIdentifier(viewModel.getImageSource(), "drawable", context.getPackageName());
                                Drawable drawable = resources.getDrawable(resourceId);

                                ((ImageView) view1.findViewById(R.id.DemoFormatsIcon)).setImageDrawable(drawable);
                                ((TextView) view1.findViewById(R.id.DemoFormatsTitle)).setText(viewModel.getFormatName());
                                ((TextView) view1.findViewById(R.id.DemoFormatsDetails)).setText(viewModel.getFormatDetails());

                            })
                            .onRowClick(R.layout.row_demoplacement, new Action2<Integer, DemoPlacementViewModel>() {
                                @Override
                                public void call(Integer integer, DemoPlacementViewModel viewModel) {
                                    loadAd(viewModel.getPlacementId());
                                }
                            })
                            .update(viewModels);

                });

        return view;
    }

    private void loadAd (int placementId) {
        AdRx.loadTestAd(getContext(), placementId)
                .subscribe(ad -> {

                    Intent settings = new Intent(getActivity(), SettingsActivity.class);
                    settings.putExtra(getActivity().getString(R.string.k_intent_ad), ad.writeToJson().toString());
                    getActivity().startActivity(settings);

                }, throwable -> {
                    showError();
                });
    }

    private void showError () {
        SAAlert.getInstance().show(
                getActivity(),
                getString(R.string.page_user_popup_error_title),
                getString(R.string.page_user_popup_error_message),
                getString(R.string.page_user_popup_error_ok_button),
                null,
                false,
                0,
                null);
    }

}
