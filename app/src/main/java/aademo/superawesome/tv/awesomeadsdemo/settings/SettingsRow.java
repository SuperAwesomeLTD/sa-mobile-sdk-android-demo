package aademo.superawesome.tv.awesomeadsdemo.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aademo.superawesome.tv.awesomeadsdemo.R;
import aademo.superawesome.tv.awesomeadsdemo.aux.GenericViewModelInterface;

/**
 * Created by gabriel.coman on 24/11/16.
 */
public class SettingsRow implements GenericViewModelInterface {

    private String item = null;

    public SettingsRow (String item) {
        this.item = item;
    }

    @Override
    public View representationAsRow(Context context, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.row_settings, parent, false);
        }

        TextView itemTextView = (TextView) v.findViewById(R.id.SettingsItemName);

        if (itemTextView != null) {
            itemTextView.setText(item != null ? item : context.getString(R.string.settings_row_item_default));
        }

        return v;
    }
}
