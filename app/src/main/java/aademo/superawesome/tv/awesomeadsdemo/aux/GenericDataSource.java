package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by gabriel.coman on 01/12/2016.
 */

public class GenericDataSource <T, B extends GenericRow> {

    private ListView listView = null;
    private Context context = null;
    private GenericAdapter<B> adapter = null;
    private List<T> data = null;
    private int rowId = 0x0;

    private Func1<T, B> objToVMFunc;
    private Action1<List<B>> subscribeFunc;

    /**
     * Main data source constructor
     * @param context the context the data source is in
     */
    public GenericDataSource (@NonNull Context context) {
        // init the data (just so that it's never null)
        this.data = new ArrayList<>();

        // copy a reference to the context
        this.context = context;

        // create the adapter
        this.adapter = new GenericAdapter<B>(this.context);
    }


    /**
     * This method binds a ListView to the data source
     * @param listView a source list view to bind data to
     * @param rowId an layout ID for a row to render on screen
     * @return the current data source instance (to allow it to chain)
     */
    public GenericDataSource <T, B> bindTo (ListView listView, int rowId) {

        // copy the row id
        this.rowId = rowId;

        // copy the list view as a "listView";
        // needed for some adapter method
        this.listView = listView;

        // set the list view adapter
        this.listView.setAdapter(adapter);

        // return current instance
        return this;
    }

    /**
     * This method is a shorthand for updating the data the first time the source gets created
     * and all other methods get called
     * @param data the new data set
     * @return the current data source instance (to allow it to chain)
     */
    public GenericDataSource <T, B> withDataSet(@NonNull List<T> data) {
        // call to update data set
        updateDataSet(data);

        // return current instance
        return this;
    }

    /**
     * This method actually performs an update on the data set
     * @param data the new data set
     * @return the current data source instance (to allow it to chain)
     */
    public GenericDataSource <T, B> updateDataSet(@NonNull List<T> data) {
        // save the new data
        this.data = data;

        // if these haven't been yet defined, just do nothing
        if (objToVMFunc == null || subscribeFunc == null) {
            return this;
        }

        // create an observable from the new data source,
        // - map the result using the "objToVMFunc" mapping function defined in the "match" method
        //  (that takes a basic "Object" instance and should return a non-null "GenericRow"
        //  that's actually going to be used by the adapter to re-create the table)
        // - create a new list observer from the mapping result
        // - subscribe to the new observer with a "subscribeFunc" parameter.
        //  ("subscribeFunc" is again defined in the "match" method and should just perform
        //  an update of the adapter)
        Observable.from(this.data).map(objToVMFunc).toList().subscribe(subscribeFunc);

        // return current instance
        return this;
    }

    /**
     * This method allows the user to specify how he wants to transform his "Object" type
     * row into an actual View that should be used by the adapter to populate the table.
     * In this method all the usual things needed to customize a custom ListView cell should
     * be done
     * @param func the function that will map the Object to a View; It also needs a reference to
     *             the current GenericRow that's being parsed
     * @return the current data source instance (to allow it to chain)
     */
    public GenericDataSource <T, B> match(Func2<T, B, View> func) {

        // instantiate "objToVMFunc"
        objToVMFunc = o -> {
            B vm = (B) new GenericRow(context, rowId, listView);
            vm.setHolderView(func.call(o, vm));
            return vm;
        };

        // instantiate the subscriber function
        subscribeFunc = genericViewModels -> {
            adapter.updateData(genericViewModels);
            adapter.reloadTable();
        };

        // update data, if it hasn't been already
        updateDataSet(data);

        // return current instance
        return this;
    }

    /**
     * This method allows defines what happens when a user clicks on a cell in the table
     * @param action this action will return the position clicked and the associated row model for it
     * @return the current data source instance (to allow it to chain)
     */
    public GenericDataSource <T, B> onRowClick (Action2<Integer, T> action) {

        // call item clicks on the list view
        // then subscribe to it, get the current data object and call the defined action
        RxAdapterView.itemClicks(listView)
                .subscribe(position -> {
                    T obj = data.get(position);
                    action.call(position, obj);
                });

        // return current instance
        return this;
    }


    public GenericDataSource <T, B> onRowViewClick (int subId, Action2<View, T> action) {

        if (action == null) {
            return this;
        }

        Observable.from(adapter.getData())
                .toList()
                .subscribe(bs -> {

                    for (int i = 0; i < bs.size(); i++) {
                        T viewModel = data.get(i);
                        View holder = bs.get(i).getHolderView();
                        View clickable = holder.findViewById(subId);

                        if (clickable != null) {
                            RxView.clicks(clickable)
                                    .subscribe(aVoid -> {
                                        action.call(clickable, viewModel);
                                    });
                        }
                    }

                });

        // return current instance
        return this;
    }

    /**
     * Get the current data item at a certain position
     * @param position the position I want the item for
     * @return the data item, or null
     */
    public T getItem (int position) {
        if (position < data.size()) {
            return data.get(position);
        } else {
            return null;
        }
    }
}
