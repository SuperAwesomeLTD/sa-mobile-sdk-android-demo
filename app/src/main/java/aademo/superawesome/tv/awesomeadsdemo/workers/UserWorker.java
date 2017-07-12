package aademo.superawesome.tv.awesomeadsdemo.workers;

import android.content.Context;

import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.library.DataStore;
import aademo.superawesome.tv.awesomeadsdemo.library.models.AppData;
import aademo.superawesome.tv.awesomeadsdemo.library.models.Company;
import aademo.superawesome.tv.awesomeadsdemo.library.models.CompanyData;
import aademo.superawesome.tv.awesomeadsdemo.library.models.LogedUser;
import aademo.superawesome.tv.awesomeadsdemo.library.models.UserMetadata;
import aademo.superawesome.tv.awesomeadsdemo.library.models.UserProfile;
import aademo.superawesome.tv.awesomeadsdemo.library.network.NetworkOperation;
import aademo.superawesome.tv.awesomeadsdemo.library.network.NetworkRequest;
import aademo.superawesome.tv.awesomeadsdemo.library.network.NetworkTask;
import aademo.superawesome.tv.awesomeadsdemo.library.parse.ParseTask;
import rx.Single;
import rx.functions.Func1;

public class UserWorker {

    public static Single<Void> isUserLoggedIn (Context context) {

        return Single.create(subscriber -> {

            String jwtToken = DataStore.getShared().getJwtToken(context);
            UserMetadata metadata = UserMetadata.processMetadata(jwtToken);

            if (metadata != null && metadata.isValid()) {
                subscriber.onSuccess(null);
            } else {
                subscriber.onError(new Throwable());
            }
        });
    }

    public static Single<LogedUser> loginUser(Context context, String username, String password) {

        NetworkOperation operation = NetworkOperation.login(username, password);
        NetworkRequest request = new NetworkRequest(operation);
        NetworkTask task = new NetworkTask(context);
        return task.execute(request)
                .flatMap(rawData -> new ParseTask<>(LogedUser.class).execute(rawData))
                .doOnSuccess(logedUser -> DataStore.getShared().setJwtToken(context, logedUser.getToken()));
    }

    public static Single<UserProfile> getProfile(Context context, String token) {

        UserProfile profile = DataStore.getShared().getProfile();
        if (profile != null) {
            return Single.just(profile);
        } else {

            NetworkOperation operation = NetworkOperation.profile(token);
            NetworkRequest request = new NetworkRequest(operation);
            NetworkTask task = new NetworkTask(context);
            return task.execute(request)
                    .flatMap(s -> new ParseTask<>(UserProfile.class).execute(s))
                    .doOnSuccess(userProfile -> DataStore.getShared().setProfile(userProfile));
        }
    }

    public static Single<AppData> getApps(Context context, int companyId, String jwtToken) {

        NetworkOperation operation = NetworkOperation.getApps(companyId, jwtToken);
        NetworkRequest request = new NetworkRequest(operation);
        NetworkTask task = new NetworkTask(context);
        return task.execute(request)
                .flatMap(s -> new ParseTask<>(AppData.class).execute(s));

    }

    public static Single<Company> getCompany(Context context, int id, String jwtToken) {

        NetworkOperation operation = NetworkOperation.getCompany(id, jwtToken);
        NetworkRequest request = new NetworkRequest(operation);
        NetworkTask task = new NetworkTask(context);
        return task.execute(request)
                .flatMap(s -> new ParseTask<>(Company.class).execute(s));

    }

    public static Single<List<Company>> getCompanies(Context context, String jwtToken) {

        NetworkOperation operation = NetworkOperation.getCompanies(jwtToken);
        NetworkRequest request = new NetworkRequest(operation);
        NetworkTask task = new NetworkTask(context);
        return task.execute(request)
                .flatMap(s -> new ParseTask<>(CompanyData.class).execute(s))
                .map(CompanyData::getData)
                .doOnSuccess(companies -> DataStore.getShared().setCompanies(companies));
    }
}
