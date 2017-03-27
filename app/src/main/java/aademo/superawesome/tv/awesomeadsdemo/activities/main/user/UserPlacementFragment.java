package aademo.superawesome.tv.awesomeadsdemo.activities.main.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.CreativesActivity;
import aademo.superawesome.tv.awesomeadsdemo.aux.DbAux;
import gabrielcoman.com.rxdatasource.RxDataSource;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import tv.superawesome.lib.sautils.SAAlert;

public class UserPlacementFragment extends Fragment {

    private UserPlacementModel current = null;
    private PublishSubject<List<UserHistory>> subject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userplacement, container, false);

        EditText placementIdEditText = (EditText) view.findViewById(R.id.UserPlacementId);
        // Button findOutMoreButton = (Button) view.findViewById(R.id.UserFindOutMore);
        Button nextButton = (Button) view.findViewById(R.id.UserNext);
        ListView history = (ListView) view.findViewById(R.id.History);

        // RxView.clicks(findOutMoreButton).subscribe(aVoid -> findOutMorePopup());

        RxTextView.textChanges(placementIdEditText).
                map(charSequence -> charSequence.toString().trim()).
                map(UserPlacementModel::new).
                doOnNext(userModel -> current = userModel).
                map(UserPlacementModel::isValid).
                subscribe(nextButton::setEnabled);

        RxView.clicks(nextButton).
                subscribe(aVoid -> {

                    // save to DB
                    DbAux.savePlacementToHistory(getContext(), new UserHistory(current.getPlacementId()));

                    // start activity
                    startActivity(current.getPlacementId());
                });

        subject = PublishSubject.create();
        subject.asObservable()
                .startWith(DbAux.getPlacementsFromHistory(getContext()))
                .map(histories -> {
                    List<UserHistoryViewModel> result = new ArrayList<>();
                    for (UserHistory h : histories) {
                        result.add(new UserHistoryViewModel(h));
                    }
                    return result;
                })
                .subscribe(placements -> {

                    RxDataSource.create(getContext())
                            .bindTo(history)
                            .customiseRow(R.layout.row_history, UserHistoryViewModel.class, (view1, model) -> {

                                ((TextView) view1.findViewById(R.id.PlacementId)).setText(model.getPlacementString());
                                ((TextView) view1.findViewById(R.id.LoadingDate)).setText(model.getDate());

                            })
                            .onRowClick(R.layout.row_history, new Action2<Integer, UserHistoryViewModel>() {
                                @Override
                                public void call(Integer position, UserHistoryViewModel model) {

                                    startActivity(model.getPlacementId());

                                }
                            })
                            .update(placements);

                });

        return view;
    }

    private void startActivity (int placementId) {
        Intent creatives = new Intent(getActivity(), CreativesActivity.class);
        creatives.putExtra(getString(R.string.k_intent_pid), placementId);
        getActivity().startActivity(creatives);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (subject != null) {
            subject.onNext(DbAux.getPlacementsFromHistory(getContext()));
        }
    }

    private void findOutMorePopup () {
        SAAlert.getInstance().show(
                getContext(),
                getString(R.string.page_user_popup_more_title),
                getString(R.string.page_user_popup_more_message),
                getString(R.string.page_user_popup_more_ok_button),
                null,
                false,
                0,
                null);
    }
}
