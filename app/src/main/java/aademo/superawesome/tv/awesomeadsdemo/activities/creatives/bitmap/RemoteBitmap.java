package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class RemoteBitmap {

    private Target target = null;

    public void getBitmap (Context context, String url, BitmapListener listener) {

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.gotBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                listener.noBitmap();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                listener.noBitmap();
            }
        };

        Picasso.with(context).load(url).into(target);

    }

}
