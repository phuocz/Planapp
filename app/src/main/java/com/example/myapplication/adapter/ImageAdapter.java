package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Network;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ImageAdapter {
    public static ImageAdapter imageAdapter;
    public BasicNetwork network;
    public RequestQueue requestQueue;
    public ImageLoader imageLoader;
    public Cache cache;
    public static Context context;

    LruCache<String, Bitmap> LRUCACHE=new LruCache<String, Bitmap>(30);

    private ImageAdapter(Context context){
        this.context=context;
        this.requestQueue=RequestQueueFunction();
        imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return LRUCACHE.get(URL);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                LRUCACHE.put(url,bitmap);
            }
        });
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }

    public static ImageAdapter getInstance(Context SynchronizedContext){
        if(imageAdapter==null){
            imageAdapter=new ImageAdapter(SynchronizedContext);
        }
        return imageAdapter;
    }
    private RequestQueue RequestQueueFunction() {
        if(requestQueue==null){
            cache=new DiskBasedCache(context.getCacheDir());
            network=new BasicNetwork(new HurlStack());
            requestQueue=new RequestQueue(cache,network);
            requestQueue.start();

        }
        return requestQueue;
    }
}
