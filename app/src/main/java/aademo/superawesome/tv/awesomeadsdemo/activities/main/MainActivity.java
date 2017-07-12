package aademo.superawesome.tv.awesomeadsdemo.activities.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.app.AppFragment;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.demo.DemoPlacementFragment;
import aademo.superawesome.tv.awesomeadsdemo.activities.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private AppFragment appFragment;
    private DemoPlacementFragment demoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        List<String> tabs = Arrays.asList(getString(R.string.page_app_title), getString(R.string.page_demo_title));
        appFragment = new AppFragment();
        demoFragment = new DemoPlacementFragment();
        List<Fragment> fragments = Arrays.asList(appFragment, demoFragment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        for (short i = 0; i < fragments.size(); i++){
            adapter.addFragment(fragments.get(i), tabs.get(i));
        }
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void startProfileActivity (View view) {
        startActivityForResult(new Intent(this, ProfileActivity.class), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        appFragment.loadData();
    }
}
