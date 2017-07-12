package aademo.superawesome.tv.awesomeadsdemo.activities.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.login.LoginActivity;
import aademo.superawesome.tv.awesomeadsdemo.activities.main.MainActivity;
import aademo.superawesome.tv.awesomeadsdemo.workers.UserWorker;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        UserWorker.isUserLoggedIn(this)
                .subscribe(aVoid -> startActivity(new Intent(IntroActivity.this, MainActivity.class)),
                        throwable -> startActivity(new Intent(IntroActivity.this, LoginActivity.class)));
    }
}
