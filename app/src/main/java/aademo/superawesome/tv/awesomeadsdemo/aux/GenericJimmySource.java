package aademo.superawesome.tv.awesomeadsdemo.aux;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by gabriel.coman on 01/12/2016.
 */

public class GenericJimmySource <T> extends Observable {

    private ListView listView = null;
    private int rowId = 0;

    private Context context = null;

    private Func1<T, GenericRow> objToVMFunc;
    private Action1<List<GenericRow>> subscribeFunc;

    private GenericAdapter<GenericRow> adapter = null;

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected GenericJimmySource(OnSubscribe f) {
        super(f);
    }
    
    public static <T> GenericJimmySource fromData (List<T> data) {
        return new GenericJimmySource(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {

                for (T d : data) {
                    subscriber.onNext(d);
                }
                subscriber.onCompleted();
            }
        });
    }

    public GenericJimmySource <T> bindTo (Context context, ListView listView, int rowId) {
        this.context = context;
        this.listView = listView;
        this.rowId = rowId;
        return this;
    }

    public GenericJimmySource <T> match (Func2<T, GenericRow, View> func) {

        objToVMFunc = o -> {
            GenericRow vm = new GenericRow(context, rowId, listView);
            vm.setHolderView(func.call(o, vm));
            return vm;
        };

        subscribeFunc = genericViewModels -> {
            adapter.updateData(genericViewModels);
        };

        return this;
    }


}
