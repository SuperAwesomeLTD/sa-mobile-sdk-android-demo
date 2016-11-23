package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import aademo.superawesome.tv.awesomeadsdemo.R;

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

        DemoFormatsAdapter adapter = new DemoFormatsAdapter(getContext());

        ListView listView = (ListView) view.findViewById(R.id.DemoFormatsListView);
        listView.setAdapter(adapter);

        DemoFormatsSource source = new DemoFormatsSource();
        source.getDemoFormats().toList().subscribe(adapter::updateData);

        return view;
    }
}
