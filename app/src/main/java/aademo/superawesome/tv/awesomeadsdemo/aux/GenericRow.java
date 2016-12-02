package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabriel.coman on 01/12/2016.
 */

public class GenericRow {

    private View holderView = null;

    GenericRow(Context context, int rowId, ViewGroup parent) {
        if (holderView == null) {
            holderView = LayoutInflater.from(context).inflate(rowId, parent, false);
        }
    }

    public View getHolderView () {
        return holderView;
    }

    void setHolderView(View v) {
        holderView = v;
    }

}
