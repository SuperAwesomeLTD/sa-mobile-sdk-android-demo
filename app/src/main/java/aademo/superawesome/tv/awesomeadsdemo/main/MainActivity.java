package aademo.superawesome.tv.awesomeadsdemo.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.main.predefined.DemoFormatsFragment;
import aademo.superawesome.tv.awesomeadsdemo.main.user.UserFragment;

public class MainActivity extends AppCompatActivity {

    // private vars
    private List<Fragment> fragments = Arrays.asList(new UserFragment(), new DemoFormatsFragment());
    private List<String> tabs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tabs = Arrays.asList(getString(R.string.tab_user),  getString(R.string.tab_demo));

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
}
