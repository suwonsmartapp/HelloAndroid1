
package com.suwonsmartapp.hello.challenge.challenge17;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suwonsmartapp.asyncbitmaploader.AsyncBitmapAdapter;
import com.suwonsmartapp.asyncbitmaploader.AsyncBitmapLoader;
import com.suwonsmartapp.hello.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by junsuk on 15. 4. 28..
 */
public class MovieAdapter extends AsyncBitmapAdapter {

    private final Context mContext;

    private SimpleDateFormat mFormat;

    @Override
    protected AsyncBitmapLoader.BitmapLoadListener getAsyncBitmapLoadListener() {
        AsyncBitmapLoader.BitmapLoadListener loadListener = new AsyncBitmapLoader.BitmapLoadListener() {
            @Override
            public Bitmap getBitmap(int position) {
//                Cursor c = (Cursor)getItem(position);
//                long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                long id = getItemId(position);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;

                Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(
                        mContext.getContentResolver(), id, MediaStore.Video.Thumbnails.MINI_KIND,
                        options);

                return thumbnail;
            }
        };
        return loadListener;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView date;
    }

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mContext = context;

        mFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_movie,
                    null);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);

            convertView.setTag(holder);
        } else {
            // View 를 재이용
            holder = (ViewHolder) convertView.getTag();
        }

        Cursor cursor = (Cursor)getItem(position);

        // cursor 에서 데이터를 가져옴
        final String title = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
        final String date = cursor.getString(cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));

        // 화면에 설정
        loadImage(position, holder.imageView);
        holder.title.setText(title);
        String dateText = mFormat.format(new Date(Long.valueOf(date)));
        holder.date.setText(dateText);

        return convertView;
    }
}
