package aademo.superawesome.tv.awesomeadsdemo.activities.creatives.bitmap;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.HashMap;

import aademo.superawesome.tv.awesomeadsdemo.adaux.AdFormat;
import tv.superawesome.lib.samodelspace.saad.SACreative;

public class BitmapManager {

    private LocalBitmap localBitmap;
    private RemoteBitmap remoteBitmap;
    private VideoBitmap videoBitmap;
    private WebViewBitmap webViewBitmap;

    private static HashMap<Integer, Bitmap> loadedBitmaps = new HashMap<>();

    public BitmapManager () {
        localBitmap = new LocalBitmap();
        remoteBitmap = new RemoteBitmap();
        videoBitmap = new VideoBitmap();
        webViewBitmap = new WebViewBitmap();
    }

    public void getBitmap (Context context, int i, SACreative creative, Listener listener) {

        final AdFormat format = AdFormat.fromCreative(creative);
        final Bitmap local = localBitmap.getBitmap(context, format);

        switch (creative.format) {

            case image: {

                remoteBitmap.getBitmap(context, creative.details.image, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(i, bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(i, local);
                    }
                });

                break;
            }
            case video: {

                videoBitmap.getBitmap(context, creative.details.video, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(i, bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(i, local);
                    }
                });

                break;
            }
            case rich: {

                String html = "<iframe style='padding:0;border:0;' width='100%' height='100%' src='" + creative.details.url + "'></iframe>";

                webViewBitmap.getBitmap(context, html, creative.details.width, creative.details.height, new BitmapListener() {
                    @Override
                    public void gotBitmap(Bitmap bitmap) {
                        listener.gotBitmap(i, bitmap);
                    }

                    @Override
                    public void noBitmap() {
                        listener.gotBitmap(i, local);
                    }
                });

                break;
            }
            case tag:
            case appwall:
            case invalid: {
                listener.gotBitmap(i, local);
                break;
            }
        }
    }

    public static void getThumb (Context context, int i, SACreative creative, Listener listener) {

        BitmapManager manager = new BitmapManager();
        if (loadedBitmaps.containsKey(creative.id)) {
            listener.gotBitmap(i, loadedBitmaps.get(creative.id));
        } else {
            manager.getBitmap(context, i, creative, (i1, bitmap) -> {
                loadedBitmaps.put(creative.id, bitmap);
                listener.gotBitmap(i1, bitmap);
            });
        }

    }

    public static void getThumbnail (Context context, SACreative creative, Listener listener) {

        switch (creative.format) {

            case image: {
                Glide.with(context)
                        .load(creative.details.image)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(creative.details.width, creative.details.height) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                listener.gotBitmap(0, resource);
                            }
                        });
                break;
            }
            case video: {

                BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
                int microSecond = 6000000;// 6th second as an example
                VideoBitmapDecoder videoBitmapDecoder = new VideoBitmapDecoder(microSecond);
                FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(videoBitmapDecoder, bitmapPool, DecodeFormat.PREFER_ARGB_8888);
                Glide.with(context)
                        .load(creative.details.video)
                        .asBitmap()
                        .override(50,50)// Example
                        .videoDecoder(fileDescriptorBitmapDecoder)
                        .into(new SimpleTarget<Bitmap>(640, 480) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                listener.gotBitmap(0, resource);
                            }
                        });

                break;
            }
            case invalid:
            case rich:
            case tag:
            case appwall: {
                LocalBitmap localBitmap = new LocalBitmap();
                AdFormat format = AdFormat.fromCreative(creative);
                Bitmap bitmap = localBitmap.getBitmap(context, format);
                listener.gotBitmap(0, bitmap);
                break;
            }
        }

    }

    public interface Listener {
        void gotBitmap (int i, Bitmap bitmap);
    }

}
