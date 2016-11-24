package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gabriel.coman on 16/11/16.
 */
public interface GenericViewModelInterface {
    View representationAsRow (Context context, View convertView, ViewGroup parent);
}
