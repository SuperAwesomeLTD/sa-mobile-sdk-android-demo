package aademo.superawesome.tv.awesomeadsdemo.activities.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.companies.CompaniesActivity;
import aademo.superawesome.tv.awesomeadsdemo.library.DataStore;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;

public class ProfileActivity extends BaseActivity {

    private RxDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.ProfileToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ListView listView = (ListView) findViewById(R.id.ProfileListView);

        //
        // customise
        dataSource = RxDataSource.create(this)
                .bindTo(listView)
                .customiseRow(R.layout.row_profile, ProfileViewModel.class, (i, view, model) -> {

                    view.setBackgroundColor(i % 2 == 0 ? Color.WHITE : 0xFFF7F7F7);

                    TextView itemLabel = (TextView) view.findViewById(R.id.ItemLabel);
                    TextView itemValue = (TextView) view.findViewById(R.id.ItemValue);

                    itemLabel.setText(model.fieldName);
                    itemValue.setText(model.fieldValue);
                    itemValue.setTextColor(model.isActive ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.colorBlack));

                })
                .onRowClick(R.layout.row_profile, (Action2<Integer, ProfileViewModel>) (integer, profileViewModel) -> {

                    if (profileViewModel.isActive) {
                        gotoCompanies();
                    }
                });

        //
        // load data
        loadData();
    }

    private void loadData () {

        String token = DataStore.getShared().getJwtToken(this);

        UserWorker.getProfile(this, token)
                .subscribe(profile -> {

                    List<ProfileViewModel> models = new ArrayList<>();
                    models.add(new ProfileViewModel(getString(R.string.page_profile_value_username), profile.getUsername(), false));
                    models.add(new ProfileViewModel(getString(R.string.page_profile_value_email), profile.getEmail(), false));
                    models.add(new ProfileViewModel(getString(R.string.page_profile_value_phone), profile.getPhoneNumber(), false));

                    UserWorker.getCompany(ProfileActivity.this, profile.getCompanyId(), token)
                            .subscribe(company -> {

                                models.add(new ProfileViewModel(getString(R.string.page_profile_value_company), company.getName(), profile.canImpersonate()));

                                dataSource.update(models);

                            }, throwable -> {
                                // do nothing
                            });
                }, throwable -> {
                    // do nothing
                });

    }

    private void gotoCompanies () {
        startActivityForResult(new Intent(this, CompaniesActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData();
    }
}

class ProfileViewModel {

    String fieldName;
    String fieldValue;
    boolean isActive;

    ProfileViewModel(String name, String value, boolean isActive) {
        this.fieldName = name;
        this.fieldValue = value;
        this.isActive = isActive;
    }
}
