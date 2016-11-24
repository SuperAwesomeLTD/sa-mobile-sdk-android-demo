package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.aux.GenericViewModelInterface;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public class DemoFormatsSource {

    public Observable<GenericViewModelInterface> getDemoFormats () {
        return Observable.create(new Observable.OnSubscribe<GenericViewModelInterface>() {
            @Override
            public void call(Subscriber<? super GenericViewModelInterface> subscriber) {

                // add data
                List<GenericViewModelInterface> data = new ArrayList<>();
                data.add(new DemoFormatsRow(30476, "smallbanner", "Mobile Small Leaderboard", "Size: 300x50"));
                data.add(new DemoFormatsRow(30471, "banner", "Mobile Leaderboard", "Size: 320x50"));
                data.add(new DemoFormatsRow(30475, "leaderboard", "Tablet Leaderboard", "Size: 728x90"));
                data.add(new DemoFormatsRow(30472, "mpu", "Tablet MPU", "Size: 300x250"));
                data.add(new DemoFormatsRow(30473, "small_inter_port", "Mobile Interstitial Portrait", "Size: 320x480"));
                data.add(new DemoFormatsRow(30474, "small_inter_land", "Mobile Interstitial Landscape", "Size: 480x320"));
                data.add(new DemoFormatsRow(30477, "large_inter_port", "Tablet Interstitial Portrait", "Size: 768x1024"));
                data.add(new DemoFormatsRow(30478, "large_inter_land", "Tablet Interstitial Landscape", "Size: 1024x768"));
                data.add(new DemoFormatsRow(30479, "video", "Mobile Video", "Size: 600x480"));

                // emmit
                for (GenericViewModelInterface vm : data) {
                    subscriber.onNext(vm);
                }
                // complete
                subscriber.onCompleted();

            }
        });
    }

}
