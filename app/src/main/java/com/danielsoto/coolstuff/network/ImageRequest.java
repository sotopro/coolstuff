package com.danielsoto.coolstuff.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.danielsoto.coolstuff.application.CoolStuffApplication;

public class ImageRequest {
    private static ImageRequest instance = null;
    private final Context context;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final int maxByteSize;

    private ImageRequest(){
        context = CoolStuffApplication.getAppContext();
        this.requestQueue = Volley.newRequestQueue(context);
        this.requestQueue.start();
        this.maxByteSize = calculateMaxByteSize();
        this.imageLoader = new ImageLoader(
                requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> lruCache =
                            new LruCache<String, Bitmap>(maxByteSize){
                                @Override
                                protected int sizeOf(String url, Bitmap bitmap) {
                                    return bitmap.getByteCount();
                                }
                            };
                    @Override
                    public synchronized  Bitmap getBitmap(String url) {return  lruCache.get(url);}

                    @Override
                    public synchronized void putBitmap(String url, Bitmap bitmap) { lruCache.put(url, bitmap);}
                });
    }

    public static ImageRequest getInstance() {
        if(instance == null) {
            instance = new ImageRequest();
        }
        return instance;
    }

    public void setImageFromUrl(NetworkImageView networkImageView, String url) {
        networkImageView.setImageUrl(url, imageLoader);
    }

    private int calculateMaxByteSize(){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
        return screenBytes * 3;
    }
}
