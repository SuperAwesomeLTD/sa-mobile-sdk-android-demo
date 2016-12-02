package aademo.superawesome.tv.awesomeadsdemo.main.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.settings.SettingsActivity;
import tv.superawesome.lib.sautils.SAAlert;

public class UserFragment extends Fragment {

    private UserModel current = null;

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

        RxView.clicks(nextButton).
                subscribe(aVoid -> startActivity(current.getPlacementId()));

        return view;
    }

    private void startActivity (int placementId) {
        Intent settings = new Intent(getActivity(), SettingsActivity.class);
        settings.putExtra(getString(R.string.k_intent_pid), placementId);
        settings.putExtra(getString(R.string.k_intent_test), false);
        getActivity().startActivity(settings);
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
}
