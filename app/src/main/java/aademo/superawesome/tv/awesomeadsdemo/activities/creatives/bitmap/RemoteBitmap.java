package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteBitmap {

    private Target target = null;

    public void getBitmap (Context context, String src, BitmapListener listener) {

        (new Thread(() -> {

            try {
                Bitmap bitmap = getBitmapFromURL(src);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);
                ((Activity) context).runOnUiThread(() -> listener.gotBitmap(scaled));
            } catch (Exception e) {
                ((Activity) context).runOnUiThread(listener::noBitmap);
            }

        })).start();

    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }

}
