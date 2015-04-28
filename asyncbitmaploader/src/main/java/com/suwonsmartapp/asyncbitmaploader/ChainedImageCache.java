package com.suwonsmartapp.asyncbitmaploader;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sol on 2015-04-07.
 */
public class ChainedImageCache implements ImageCache {

    private List<ImageCache> chain;

    public ChainedImageCache(List<ImageCache> chain) {
        this.chain = chain;
    }

    @Override
    public void addBitmap(String key, Bitmap bitmap) {
        for (ImageCache cache : chain) {
            cache.addBitmap(key, bitmap);
        }
    }

    @Override
    public void addBitmap(String key, File bitmapFile) {
        for (ImageCache cache : chain) {
            cache.addBitmap(key, bitmapFile);
        }
    }

    @Override
    public final Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        List<ImageCache> previousCaches = new ArrayList<ImageCache>();
        for (ImageCache cache : chain) {
            bitmap = cache.getBitmap(key);
            if (bitmap != null) {
                break;
            }
            previousCaches.add(cache);
        }
        if (bitmap == null)
            return null;

        if (!previousCaches.isEmpty()) {
            for (ImageCache cache : previousCaches) {
                cache.addBitmap(key, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public final void clear() {
        for (ImageCache cache : chain) {
            cache.clear();
        }
    }

}
