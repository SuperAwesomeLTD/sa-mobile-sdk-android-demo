package aademo.superawesome.tv.awesomeadsdemo.activities.main.demo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import rx.Observable;

public class DemoPlacementProvider {

    public Observable<DemoPlacementViewModel> getModels (Context context) {

        List<DemoPlacementViewModel> data = new ArrayList<>();
        data.add(new DemoPlacementViewModel(
                30472,
                "smallbanner",
                context.getString(R.string.page_demo_table_row_1_title),
                context.getString(R.string.page_demo_table_row_1_details)
        ));
        data.add(new DemoPlacementViewModel(
                30471,
                "banner",
                context.getString(R.string.page_demo_table_row_2_title),
                context.getString(R.string.page_demo_table_row_2_details)
        ));
        data.add(new DemoPlacementViewModel(
                30475,
                "leaderboard",
                context.getString(R.string.page_demo_table_row_3_title),
                context.getString(R.string.page_demo_table_row_3_details)
        ));
        data.add(new DemoPlacementViewModel(
                30476,
                "mpu",
                context.getString(R.string.page_demo_table_row_4_title),
                context.getString(R.string.page_demo_table_row_4_details)
        ));
        data.add(new DemoPlacementViewModel(
                30473,
                "small_inter_port",
                context.getString(R.string.page_demo_table_row_5_title),
                context.getString(R.string.page_demo_table_row_5_details)
        ));
        data.add(new DemoPlacementViewModel(
                30474,
                "small_inter_land",
                context.getString(R.string.page_demo_table_row_6_title),
                context.getString(R.string.page_demo_table_row_6_details)
        ));
        data.add(new DemoPlacementViewModel(
                30477,
                "large_inter_port",
                context.getString(R.string.page_demo_table_row_7_title),
                context.getString(R.string.page_demo_table_row_7_details)
        ));
        data.add(new DemoPlacementViewModel(
                30478,
                "large_inter_land",
                context.getString(R.string.page_demo_table_row_8_title),
                context.getString(R.string.page_demo_table_row_8_details)
        ));
        data.add(new DemoPlacementViewModel(
                30479,
                "video",
                context.getString(R.string.page_demo_table_row_9_title),
                context.getString(R.string.page_demo_table_row_9_details)
        ));

        return Observable.create(subscriber -> {

            for (DemoPlacementViewModel vm : data) {
                subscriber.onNext(vm);
            }
            subscriber.onCompleted();

        });
    }

}
