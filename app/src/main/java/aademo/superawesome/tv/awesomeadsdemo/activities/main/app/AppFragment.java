package aademo.superawesome.tv.awesomeadsdemo.activities.main.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.CreativesActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.profile.ProfileActivity;
import aademo.superawesome.tv.awesomeadsdemo.library.DataStore;
import aademo.superawesome.tv.awesomeadsdemo.library.models.App;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Placement;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;

public class AppFragment extends Fragment {

    private RxDataSource dataSource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app, container, false);

        ListView listView = (ListView) view.findViewById(R.id.AppAndPlacementsListView);

        //
        // customise
        dataSource = RxDataSource.create(getContext())
                .bindTo(listView)
                .customiseRow(R.layout.row_app, App.class, (integer, view1, app) -> {
                    ((TextView) view1.findViewById(R.id.ItemTitle)).setText(app.getName());
                })
                .customiseRow(R.layout.row_placement, PlacementViewModel.class, (integer, view12, placement) -> {

                    ((TextView) view12.findViewById(R.id.ItemTitle)).setText(placement.getPlacementName());
                    ((TextView) view12.findViewById(R.id.ItemPlacementId)).setText(placement.getPlacementID());
                    ((TextView) view12.findViewById(R.id.ItemDetails)).setText(placement.getPlacementSize());
                    ((ImageView) view12.findViewById(R.id.ItemIcon)).setImageDrawable(placement.getPlacementIcon(getActivity()));

                })
                .onRowClick(R.layout.row_placement, (Action2<Integer, PlacementViewModel>) (integer, placement) -> {
                    startCreativesActivity(placement.getPlacement().getId());
                });

        //
        // load data
        loadData();

        return view;
    }

    public void loadData () {

        String token = DataStore.getShared().getJwtToken(getContext());

        UserWorker.getProfile(getContext(), token)
                .flatMap(userProfile -> UserWorker.getApps(getContext(), userProfile.getCompanyId(), token))
                .map(appData -> {

                    List<Object> result = new ArrayList<>();

                    for (App app : appData.getData()) {
                        result.add(app);
                        for (Placement p : app.getPlacements()) {
                            result.add(new PlacementViewModel(p));
                        }
                    }

                    return result;
                })
                .subscribe(objects -> dataSource.update(objects), throwable -> {});
    }

    private void startCreativesActivity(int placementId) {
        Intent creatives = new Intent(getActivity(), CreativesActivity.class);
        creatives.putExtra(getString(R.string.k_intent_pid), placementId);
        getActivity().startActivity(creatives);
    }
}
