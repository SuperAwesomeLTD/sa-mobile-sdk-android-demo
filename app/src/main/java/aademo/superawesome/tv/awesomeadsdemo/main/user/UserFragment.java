package aademo.superawesome.tv.awesomeadsdemo.main.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdAux;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import rx.Observable;
import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.SASession;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAProgressDialog;

/**
 * Created by gabriel.coman on 16/11/16.
 */
public class UserFragment extends Fragment {

    private UserModel current = null;

    public UserFragment () {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        EditText placementIdEditText = (EditText) view.findViewById(R.id.UserPlacementId);
        Button findOutMoreButton = (Button) view.findViewById(R.id.UserFindOutMore);
        Button nextButton = (Button) view.findViewById(R.id.UserNext);

        RxView.clicks(findOutMoreButton).subscribe(aVoid -> findOutMorePopup());

        RxTextView.textChanges(placementIdEditText).
                map(charSequence -> charSequence.toString().trim()).
                map(UserModel::new).
                doOnNext(userModel -> current = userModel).
                map(UserModel::isValid).
                subscribe(nextButton::setEnabled);

        RxView.clicks(nextButton).subscribe(aVoid -> {

            loadAd(current.getPlacementId()).
                    doOnSubscribe(() -> SAProgressDialog.getInstance().showProgress(getContext())).
                    doOnCompleted(() -> SAProgressDialog.getInstance().hideProgress()).
                    doOnError(throwable -> SAProgressDialog.getInstance().hideProgress()).
                    subscribe(adFormat -> {
                        Log.d("SuperAwesome", "Final format is " + adFormat);
                    }, throwable -> {
                        loadErrorPopup();
                    });
        });

        return view;
    }

    private Observable<AdFormat> loadAd (int placementId) {

        final SASession session = new SASession(getContext());
        final SALoader loader = new SALoader(getContext());
        final AdAux adAux = new AdAux();

        return Observable.create((Observable.OnSubscribe<AdFormat>) subscriber -> {
            loader.loadAd(placementId, session, saResponse -> {
                AdFormat format = adAux.determineAdType(saResponse);
                if (format == AdFormat.unknown) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onNext(format);
                    subscriber.onCompleted();
                }
            });
        });
    }

    private void findOutMorePopup () {
        SAAlert.getInstance().show(
                getContext(),
                getString(R.string.fragment_user_more_popup_title),
                getString(R.string.fragment_user_more_popup_message),
                getString(R.string.fragment_user_more_popup_btn),
                null,
                false,
                0,
                null);
    }

    public void loadErrorPopup () {
        SAAlert.getInstance().show(
                getContext(),
                getString(R.string.fragment_user_error_popup_title),
                getString(R.string.fragment_user_error_popup_message),
                getString(R.string.fragment_user_error_popup_btn),
                null,
                false,
                0,
                null);
    }
}
