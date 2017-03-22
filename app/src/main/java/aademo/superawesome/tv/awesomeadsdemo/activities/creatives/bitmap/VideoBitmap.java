package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

public class VideoBitmap {

    private Target target;

    public void getBitmap (Context context, String url, BitmapListener listener) {

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };



        (new Thread(() -> {
            try {
                Bitmap bitmap = getVideoFrameFromUrl(url);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);
                ((Activity) context).runOnUiThread(() -> listener.gotBitmap(scaled));
            } catch (Throwable throwable) {
                ((Activity) context).runOnUiThread(listener::noBitmap);
            }
        })).start();

    }

    private static Bitmap getVideoFrameFromUrl (String url) throws Throwable {

        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;

        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(url, new HashMap<>());
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)  {
            throw new Throwable();
        }
        finally  {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }

        return bitmap;
    }
}
