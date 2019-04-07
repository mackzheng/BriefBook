package com.cache.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * 一个简单的内存缓存工具。网络层暂时没有处理。
 */
public class SimpleImageLoader {

    private static SimpleImageLoader mLoader;

    private LruCache<String,Bitmap> mLrucache;

    public SimpleImageLoader() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);

        mLrucache =  new LruCache<String,Bitmap>(maxSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static SimpleImageLoader getInstance()
    {
        if(mLoader == null)
        {
            mLoader = new SimpleImageLoader();
        }
        return mLoader;
    }


    public void displayImage(ImageView view ,String url)
    {
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap!=null)
        {
            view.setImageBitmap(bitmap);
        }else
        {
            downLoadImage(view,url);
        }
    }



    private Bitmap getBitmapFromCache(String url) {
        return mLrucache.get(url);
    }

    private void putBitmapTocache(Bitmap bitmap,String url)
    {
        if(bitmap!=null)
        {
            mLrucache.put(url,bitmap);
        }
    }

    private void downLoadImage(ImageView view, String url) {
//        okhttp 保存
//        putBitmapTocache(bitmap,url);

    }
 }