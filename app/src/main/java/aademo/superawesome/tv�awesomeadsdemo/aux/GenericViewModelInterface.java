package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface GenericViewModelInterface {
    View representationAsRow (Context context, View convertView, ViewGroup parent);
}
