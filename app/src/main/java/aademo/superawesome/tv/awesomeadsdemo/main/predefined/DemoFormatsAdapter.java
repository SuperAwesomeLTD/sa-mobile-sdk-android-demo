package aademo.superawesome.tv.awesomeadsdemo.main.predefined;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.aux.GenericAdapter;
import aademo.superawesome.tv.awesomeadsdemo.aux.ViewModel;

/**
 * Created by gabriel.coman on 23/11/16.
 */
public class DemoFormatsAdapter extends ArrayAdapter<ViewModel> implements GenericAdapter {

    private List<ViewModel> formats = new ArrayList<>();

    public DemoFormatsAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public void updateData(List<ViewModel> newData) {
        formats = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return formats.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return formats.get(position).representationAsRow(getContext(), convertView);
    }
}
