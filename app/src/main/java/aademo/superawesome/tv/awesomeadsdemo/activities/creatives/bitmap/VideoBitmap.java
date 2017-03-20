package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.HashMap;

public class VideoBitmap {

    public void getBitmap (Context context, String url, BitmapListener listener) {

        (new Thread(() -> {
            try {
                Bitmap bitmap = getVideoFrameFromUrl(url);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);
                Log.d("SuperAwesome", "Bitmap " + url + " ==> " + scaled.getWidth() + ", " + scaled.getHeight());
                ((Activity) context).runOnUiThread(() -> listener.gotBitmap(scaled));
            } catch (Throwable throwable) {
                listener.noBitmap();
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
