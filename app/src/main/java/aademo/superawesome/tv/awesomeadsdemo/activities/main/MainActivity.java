package aademo.superawesome.tv.awesomeadsdemo.activities.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.companies.CompaniesActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.CreativesActivity;
import aademo.superawesome.tv.awesomeadsdemo.library.DataStore;
import aademo.superawesome.tv.awesomeadsdemo.library.models.App;
import aademo.superawesome.tv.awesomeadsdemo.library.models.AppData;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Company;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Placement;
import aademo.superawesome.tv.awesomeadsdemo.library.models.UserProfile;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;
import butterknife.BindView;
import butterknife.ButterKnife;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private final String EMPTY_SEARCH = "";
    private AppData appData = null;
    private RxDataSource dataSource;

    @BindView(R.id.AppAndPlacementsListView) ListView listView;
    @BindView(R.id.AppsPlacementSearch) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataSource = RxDataSource.create(this)
                .bindTo(listView)
                .customiseRow(R.layout.row_app, App.class, (integer, view1, app) -> {
                    ((TextView) view1.findViewById(R.id.ItemTitle)).setText(app.getName());
                })
                .customiseRow(R.layout.row_placement, PlacementViewModel.class, (integer, view12, placement) -> {

                    ((TextView) view12.findViewById(R.id.ItemTitle)).setText(placement.getPlacementName());
                    ((TextView) view12.findViewById(R.id.ItemPlacementId)).setText(placement.getPlacementID());
                    ((TextView) view12.findViewById(R.id.ItemDetails)).setText(placement.getPlacementSize());
                    ((ImageView) view12.findViewById(R.id.ItemIcon)).setImageDrawable(placement.getPlacementIcon(MainActivity.this));

                })
                .onRowClick(R.layout.row_placement, (Action2<Integer, PlacementViewModel>) (integer, placement) -> {
                    startCreativesActivity(placement.getPlacement().getId());
                });

        RxTextView.textChanges(editText)
                .map(charSequence -> charSequence.toString().trim())
                .map(searchTerm -> search(searchTerm, appData))
                .subscribe(viewModels -> dataSource.update(viewModels), throwable -> {});

        loadData();
    }

    public void startCompaniesActivity (View view) {
        editText.setText(EMPTY_SEARCH);
        startActivityForResult(new Intent(this, CompaniesActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData();
    }

    private void startCreativesActivity(int placementId) {
        Intent creatives = new Intent(this, CreativesActivity.class);
        creatives.putExtra(getString(R.string.k_intent_pid), placementId);
        startActivity(creatives);
    }

    public void loadData () {

        String token = DataStore.getShared().getJwtToken(this);

        UserWorker.getProfile(this, token)
                .flatMap(userProfile -> UserWorker.getApps(this, userProfile.getCompanyId(), token))
                .doOnSuccess(appData -> MainActivity.this.appData = appData)
                .map(appData -> search(null, appData))
                .subscribe(viewModels -> dataSource.update(viewModels), throwable -> {});
    }

    private List<Object> search(String searchTerm, AppData appData) {

        List<Object> result = new ArrayList<>();

        if (appData == null) {
            return result;
        }

        for (App app : appData.getData()) {

            List<PlacementViewModel> viewModels = new ArrayList<>();

            for (Placement p : app.getPlacements()) {

                if (searchTerm == null || searchTerm.isEmpty()) {
                    viewModels.add(new PlacementViewModel(p));
                } else {
                    String searchItem = p.getName() + "_" + p.getId();
                    if (searchItem.toLowerCase().contains(searchTerm.toLowerCase())) {
                        viewModels.add(new PlacementViewModel(p));
                    }
                }
            }

            if (viewModels.size() > 0) {
                result.add(app);
                result.addAll(viewModels);
            }
        }

        return  result;
    }
}
