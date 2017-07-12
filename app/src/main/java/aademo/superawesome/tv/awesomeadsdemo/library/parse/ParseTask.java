package aademo.superawesome.tv.awesomeadsdemo.library.parse;

import com.google.gson.Gson;

import aademo.superawesome.tv.awesomeadsdemo.library.Task;
import rx.Single;

public class ParseTask <T> implements Task <String, T, Single<T>> {

    private Class<T> type;

    public ParseTask(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Single<T> execute(String input) {

        return Single.create(subscriber -> {

            Gson gson = new Gson();
            T value = gson.fromJson(input, type);

            if (value != null) {
                subscriber.onSuccess(value);
            } else {
                subscriber.onError(new Throwable());
            }
        });
    }
}
