
package com.suwonsmartapp.asyncbitmaploader;

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.ImageView;

/**
 * Created by junsuk on 2015-04-28.
 */
public abstract class AsyncBitmapAdapter extends CursorAdapter {

    private AsyncBitmapLoader mAsyncBitmapLoader;

    public AsyncBitmapAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mAsyncBitmapLoader = new AsyncBitmapLoader(context);
        mAsyncBitmapLoader.setBitmapLoadListener(getAsyncBitmapLoadListener());
    }

    protected abstract AsyncBitmapLoader.BitmapLoadListener getAsyncBitmapLoadListener();

    protected void loadImage(int position, ImageView view) {
        mAsyncBitmapLoader.loadBitmap(position, view);
    }

}
