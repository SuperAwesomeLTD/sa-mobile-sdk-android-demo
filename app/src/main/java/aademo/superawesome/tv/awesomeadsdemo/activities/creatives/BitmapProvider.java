package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.BitmapListener;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.LocalBitmap;
import aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap.RichMediaBitmap;
import tv.superawesome.lib.samodelspace.saad.SACreative;

public class BitmapProvider {

    private Target target;
    private RichMediaBitmap richMediaBitmap;

    public BitmapProvider () {
        richMediaBitmap = new RichMediaBitmap();
    }

    public void getBitmap (Context context, int pos, CreativesViewModel model, Listener listener) {

        SACreative creative = model.getCreative();
        Bitmap def = LocalBitmap.getBitmap(context, model.getFormat());

        switch (creative.format) {
            case image:
            case video: {

                target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        listener.gotBitmap(pos, bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d("SuperAwesome", "Failed bitmap for pos " + pos);
                        listener.gotBitmap(pos, def);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.with(context).load(model.getBitmapUrl()).into(target);

                break;
            }
            case rich: {

                String html = "<iframe style='padding:0;border:0;' width='100%' height='100%' src='" + creative.details.url + "'></iframe>";

                richMediaBitmap.getBitmap(context, creative.details.base, html, creative.details.width, creative.details.height, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(pos, bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(pos, def);
                    }
                });

                break;
            }
            case tag:
            case appwall:
            case invalid: {
                listener.gotBitmap(pos, def);
                break;
            }
        }

    }

    Bitmap getPlaceholder(Context context) {
        String drawable = "icon_placeholder";
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(drawable, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(res, resourceId);
    }

    interface Listener {
        void gotBitmap (int position, Bitmap bitmap);
    }

}
