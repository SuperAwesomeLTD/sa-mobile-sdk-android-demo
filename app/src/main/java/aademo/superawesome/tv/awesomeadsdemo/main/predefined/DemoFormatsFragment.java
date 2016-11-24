package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jakewharton.rxbinding.widget.RxAdapterView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdPreload;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericAdapter;
import aademo.superawesome.tv.awesomeadsdemo.settings.SettingsActivity;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAProgressDialog;

/**
 * Created by gabriel.coman on 16/11/16.
 */
public class DemoFormatsFragment extends Fragment {

    public DemoFormatsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demoformats, container, false);

        final AdPreload preload = new AdPreload(getContext());

        GenericAdapter adapter = new GenericAdapter(getContext());

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);
        listView.setAdapter(adapter);

        DemoFormatsSource source = new DemoFormatsSource();
        source.getDemoFormats().toList().subscribe(adapter::updateData);

        RxAdapterView.itemClicks(listView).subscribe(pos -> {
            int pid = ((DemoFormatsRow) adapter.getItem(pos)).getPlacementId();

            preload.loadAd(pid, true).
                    doOnSubscribe(() -> SAProgressDialog.getInstance().showProgress(getContext())).
                    doOnCompleted(() -> SAProgressDialog.getInstance().hideProgress()).
                    doOnError(throwable -> SAProgressDialog.getInstance().hideProgress()).
                    subscribe(format -> startActivity(pid, format), throwable -> loadErrorPopup());

        });

        return view;
    }

    public void startActivity (int placementId, AdFormat format) {
        Intent settings = new Intent(getActivity(), SettingsActivity.class);
        settings.putExtra(getString(R.string.k_intent_pid), placementId);
        settings.putExtra(getString(R.string.k_intent_format), format.ordinal());
        getActivity().startActivity(settings);
    }

    public void loadErrorPopup () {
        SAAlert.getInstance().show(
                getContext(),
                getString(R.string.fragment_demo_error_popup_title),
                getString(R.string.fragment_demo_error_popup_message),
                getString(R.string.fragment_demo_error_popup_btn),
                null,
                false,
                0,
                null);
    }
}
