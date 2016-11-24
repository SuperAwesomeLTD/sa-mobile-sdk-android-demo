package aademo.superawesome.tv.awesomeadsdemo.settings;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.aux.GenericViewModelInterface;
import rx.Observable;

/**
 * Created by gabriel.coman on 24/11/16.
 */
public class SettingsSource {

    public Observable<GenericViewModelInterface> getSettings () {

        return Observable.create((Observable.OnSubscribe<GenericViewModelInterface>) subscriber -> {

            List<GenericViewModelInterface> data = new ArrayList<>();
            data.add(new SettingsRow("Parental gate enabled"));
            data.add(new SettingsRow("Transparent background color"));
            data.add(new SettingsRow("Enable back button"));
            data.add(new SettingsRow("Lock to portrait"));
            data.add(new SettingsRow("Lock to landscape"));
            data.add(new SettingsRow("Enable close button"));
            data.add(new SettingsRow("Enable auto-close"));
            data.add(new SettingsRow("Enable small click button"));

            // emmit
            for (GenericViewModelInterface vm : data) {
                subscriber.onNext(vm);
            }
            // complete
            subscriber.onCompleted();

        });
    }

}
