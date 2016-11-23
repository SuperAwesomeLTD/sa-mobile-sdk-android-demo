package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

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
import aademo.superawesome.tv.awesomeadsdemo.adaux.AdPreload;
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

        DemoFormatsAdapter adapter = new DemoFormatsAdapter(getContext());

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);
        listView.setAdapter(adapter);

        DemoFormatsSource source = new DemoFormatsSource();
        source.getDemoFormats().toList().subscribe(adapter::updateData);

        RxAdapterView.itemClicks(listView).subscribe(pos -> {
            int placementId = ((DemoFormatsRow) adapter.getItem(pos)).getPlacementId();

            preload.loadAd(placementId, true).
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
