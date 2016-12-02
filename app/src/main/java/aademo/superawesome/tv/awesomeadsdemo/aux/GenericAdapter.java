package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class GenericAdapter <T extends GenericRow> extends ArrayAdapter<T>{

    private List<T> data = new ArrayList<>();

    public GenericAdapter(Context context) {
        super(context, 0);
    }

    void updateData(List<T> newData) {
        data = newData;
    }

    void reloadTable () {
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return data.get(position).getHolderView();
    }

    public List<T> getData() {
        return data;
    }
}
