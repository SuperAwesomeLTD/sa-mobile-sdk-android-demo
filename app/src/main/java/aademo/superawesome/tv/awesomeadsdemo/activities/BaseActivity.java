package aademo.superawesome.tv.awesomeadsdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import rx.Single;

public class BaseActivity extends AppCompatActivity {

    public void finishOK () {
        setResult(RESULT_OK);
        finish();
    }

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

    public Single<Map<String, Object>> getExtras (String[] keys) {

        return Single.create(subscriber -> {

            Bundle bundle = BaseActivity.this.getIntent().getExtras();
            if (bundle != null) {

                Map<String, Object> result = new HashMap<>();

                for (String key : keys) {
                    Object extra = bundle.get(key);
                    if (extra != null) {
                        result.put(key, extra);
                    }
                }

                subscriber.onSuccess(result);

            } else {
                subscriber.onError(new Throwable());
            }

        });

    }

}
