package aademo.superawesome.tv.awesomeadsdemo.activities.companies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.BaseActivity;
import aademo.superawesome.tv.awesomeadsdemo.library.DataStore;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Company;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;
import butterknife.BindView;
import butterknife.ButterKnife;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;

public class CompaniesActivity extends BaseActivity {

    private RxDataSource dataSource;

    @BindView(R.id.CompanySearch) EditText editText;
    @BindView(R.id.CompaniesListView) ListView listView;
    @BindView(R.id.CompaniesToolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        //
        // customise
        dataSource = RxDataSource.create(this)
                .bindTo(listView)
                .customiseRow(R.layout.row_company, Company.class, (i, view, company) -> {

                    view.setBackgroundColor(i % 2 == 0 ? Color.WHITE : 0xFFF7F7F7);

                    TextView companyName = (TextView) view.findViewById(R.id.CompanyName);
                    companyName.setText(company.getName());

                })
                .onRowClick(R.layout.row_company, (Action2<Integer, Company>) (i, company) -> {

                    //
                    // make update
                    DataStore.getShared().getProfile().setCompanyId(company.getId());

                    //
                    // finish!
                    finishOK();
                });

        //
        // local search
        RxTextView.textChanges(editText)
                .map(charSequence -> charSequence.toString().trim())
                .map(s -> {

                    if (s.isEmpty()) {
                        return DataStore.getShared().getCompanies();
                    } else {
                        List<Company> filtered = new ArrayList<>();

                        for (Company c : DataStore.getShared().getCompanies()) {
                            if (c.getName().toUpperCase().contains(s.toUpperCase())) {
                                filtered.add(c);
                            }
                        }

                        return filtered;
                    }
                })
                .subscribe(companies -> dataSource.update(companies));

        //
        // load data
        loadData();
    }

    private void loadData () {

        String token = DataStore.getShared().getJwtToken(this);

        UserWorker.getCompanies(this, token)
                .subscribe(companies -> dataSource.update(companies), throwable -> {});

    }
}
