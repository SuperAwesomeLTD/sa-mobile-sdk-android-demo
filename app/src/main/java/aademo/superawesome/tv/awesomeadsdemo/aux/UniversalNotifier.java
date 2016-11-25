package aademo.superawesome.tv.awesomeadsdemo.aux;

import rx.Observable;
import rx.subjects.PublishSubject;

public class UniversalNotifier {

    private static PublishSubject<String> changeObservable = PublishSubject.create();;

    public static void postNotification (String notification) {
        changeObservable.onNext(notification);
    }

    public static PublishSubject<String> getObservable() {
        return changeObservable;
    }

    public static Observable<String> observe(String filer) {
        return getObservable().filter(s -> s.equals(filer));
    }
}

