package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericAdapter;

/**
 * Created by gabriel.coman on 24/11/16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.SettingsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        GenericAdapter adapter = new GenericAdapter(this);

        ListView listView = (ListView) findViewById(R.id.SettingsListView);
        listView.setAdapter(adapter);

        SettingsSource source = new SettingsSource();
        source.getSettings().toList().subscribe(adapter::updateData);

    }
}
