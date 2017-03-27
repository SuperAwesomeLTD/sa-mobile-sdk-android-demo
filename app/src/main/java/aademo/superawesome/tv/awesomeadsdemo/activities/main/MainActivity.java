package aademo.superawesome.tv.awesomeadsdemo.activities.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.demo.DemoPlacementFragment;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.user.UserPlacementFragment;
import aademo.superawesome.tv.awesomeadsdemo.loginaux.LoginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        List<String> tabs = Arrays.asList(getString(R.string.page_user_title), getString(R.string.page_demo_title));
        List<Fragment> fragments = Arrays.asList(new UserPlacementFragment(), new DemoPlacementFragment());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ActionLogout) {

            LoginManager.getManager().logout(this);
            finish();

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
