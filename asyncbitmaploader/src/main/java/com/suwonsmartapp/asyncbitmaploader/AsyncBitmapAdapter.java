
package com.suwonsmartapp.asyncbitmaploader;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by sol on 2015-04-28.
 */
public abstract class AsyncBitmapAdapter extends BaseAdapter {

    private static final String TAG = AsyncBitmapAdapter.class.getSimpleName();
    private final Context mContext;

    private AsyncBitmapLoader mAsyncBitmapLoader;

    public AsyncBitmapAdapter(Context context, AsyncBitmapLoader asyncBitmapLoader) {
        mAsyncBitmapLoader = asyncBitmapLoader;
        mContext = context;
    }

    protected void loadImage(int position, ImageView view) {
        if (mAsyncBitmapLoader != null) {
            mAsyncBitmapLoader.loadBitmap(position, view);
        }
    }

}
