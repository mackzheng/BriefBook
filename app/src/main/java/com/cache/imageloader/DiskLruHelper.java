package com.cache.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.cache.disklrucache.DiskLruCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * https://github.com/q422013/ImageLoader
 * https://github.com/nostra13/Android-Universal-Image-Loader
 */
public class DiskLruHelper {

    private static final String DIR_NAME = "diskCache";

    private static final int MAX_COUNT = 5 * 1024 * 1024;
    private static final int DEFAULT_APP_VERSION = 1;

    private static final String TAG = DiskLruHelper.class.getSimpleName();

    private DiskLruCache mDiskLruCache;

    public DiskLruHelper(Context context) throws IOException {
        mDiskLruCache = generateCache(context, DIR_NAME, MAX_COUNT);

    }

    public DiskLruHelper(Context context, String dirName) throws IOException {
        mDiskLruCache = generateCache(context, dirName, MAX_COUNT);

    }

    public DiskLruHelper(Context context, String dirName, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dirName, maxCount);

    }

    public DiskLruHelper(Context context, File dir, int maxCount) throws IOException {
        mDiskLruCache = generateCache(context, dir, maxCount);

    }

    public DiskLruHelper(File dir) throws IOException {
        mDiskLruCache = generateCache(null, dir, MAX_COUNT);
    }


    public DiskLruHelper(Context context, File dir) throws IOException {
        mDiskLruCache = generateCache(context, dir, MAX_COUNT);
    }

    private DiskLruCache generateCache(Context context, String dirName, int maxCount) throws IOException {
        DiskLruCache diskLruCache = DiskLruCache.open(
                getDiskCacheDir(context, dirName),
                Util.getAppVersion(context),
                DEFAULT_APP_VERSION,
                maxCount);
        return diskLruCache;
    }


    private DiskLruCache generateCache(Context context, File dir, int maxCount) throws IOException {

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("not a directory or does not exists.");
        }

        int appVersion = context == null ? DEFAULT_APP_VERSION : Util.getAppVersion(context);

        DiskLruCache diskLruCache = DiskLruCache.open(dir, appVersion, DEFAULT_APP_VERSION, maxCount);
        return diskLruCache;
    }

    public String getAsString(String key) {
        InputStream inputStream = null;
        try {
            //write READ
            inputStream = get(key);
            if (inputStream == null) return null;
            StringBuilder sb = new StringBuilder();
            int len = 0;
            byte[] buf = new byte[128];
            while ((len = inputStream.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            return sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }
        return null;
    }

//    public void put(String key, Bitmap bitmap) {
//        put(key, Util.bitmap2Bytes(bitmap));
//    }

    public Bitmap getAsBitmap(String key) {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return Util.bytes2Bitmap(bytes);

    }

    public byte[] getAsBytes(String key) {
        byte[] res = null;
        InputStream is = get(key);
        if (is == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[256];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public void put(String key, JSONObject jsonObject) {
        put(key, jsonObject.toString());
    }

    public JSONObject getAsJson(String key) {
        String val = getAsString(key);
        try {
            if (val != null)
                return new JSONObject(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public InputStream get(String key) {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(Util.hashKeyForDisk(key));

            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(String key, String value) {
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try {
            edit = editor(key);
            if (edit == null) return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(value);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();

            try {
                edit.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DiskLruCache.Editor editor(String key) throws IOException {
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        return editor;
    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}
