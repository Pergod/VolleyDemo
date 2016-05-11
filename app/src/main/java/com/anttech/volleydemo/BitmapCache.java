package com.anttech.volleydemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Hyper on 2016/5/11.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private int size=10*1024*1024;
    private LruCache<String,Bitmap> cache=new LruCache<>(size);

    @Override
    public Bitmap getBitmap(String s) {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        cache.put(s,bitmap);
    }
}
