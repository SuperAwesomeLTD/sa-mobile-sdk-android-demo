package aademo.superawesome.tv.awesomeadsdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rx.Single;

public class BaseActivity extends AppCompatActivity {

    public Single<String> getStringExtras (String key) {
        return Single.create(singleSubscriber -> {

            Bundle bundle = BaseActivity.this.getIntent().getExtras();
            if (bundle != null) {
                String extra = bundle.getString(key);
                if (extra != null) {
                    singleSubscriber.onSuccess(extra);
                }
            }

        });
    }

    public Single<Integer> getIntExtras (String key) {
        return Single.create(singleSubscriber -> {

            Bundle bundle = BaseActivity.this.getIntent().getExtras();
            if (bundle != null) {
                Integer extra = bundle.getInt(key);
                singleSubscriber.onSuccess(extra);
            } else {
                singleSubscriber.onError(new Throwable());
            }
        });
    }

}
