package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.aux.ViewModel;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public class DemoFormatsSource {

    public Observable<ViewModel> getDemoFormats () {
        return Observable.create(new Observable.OnSubscribe<ViewModel>() {
            @Override
            public void call(Subscriber<? super ViewModel> subscriber) {

                // add data
                List<ViewModel> data = new ArrayList<>();
                data.add(new DemoFormatsRow("smallbanner", "Mobile Small Leaderboard", "Size: 300x50"));
                data.add(new DemoFormatsRow("banner", "Mobile Leaderboard", "Size: 320x50"));
                data.add(new DemoFormatsRow("leaderboard", "Tablet Leaderboard", "Size: 728x90"));
                data.add(new DemoFormatsRow("mpu", "Tablet MPU", "Size: 300x250"));
                data.add(new DemoFormatsRow("small_inter_port", "Mobile Interstitial Portrait", "Size: 320x480"));
                data.add(new DemoFormatsRow("small_inter_land", "Mobile Interstitial Landscape", "Size: 480x320"));
                data.add(new DemoFormatsRow("large_inter_port", "Tablet Interstitial Portrait", "Size: 768x1024"));
                data.add(new DemoFormatsRow("large_inter_land", "Tablet Interstitial Landscape", "Size: 1024x768"));
                data.add(new DemoFormatsRow("video", "Mobile Video", "Size: 600x480"));

                // emmit
                for (ViewModel vm : data) {
                    subscriber.onNext(vm);
                }
                // complete
                subscriber.onCompleted();

            }
        });
    }

}
