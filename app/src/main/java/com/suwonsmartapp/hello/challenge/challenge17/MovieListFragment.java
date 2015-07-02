
package com.suwonsmartapp.hello.challenge.challenge17;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suwonsmartapp.abl.AsyncBitmapLoader;
import com.suwonsmartapp.hello.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by junsuk on 15. 6. 3..
 */
public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int SPAN_COUNT = 2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER,
        STAGGERED_LAYOUT_MANAGER
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 가로
            setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
        } else {
            // 세로
            setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        }

        // 로더 초기화
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // 커서 로더 생성
        // 모든 Video 데이터 취득
        return new CursorLoader(getActivity(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        MyAdapter adapter = new MyAdapter(getActivity(), data);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll
        // position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                break;
            case STAGGERED_LAYOUT_MANAGER:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements
            AsyncBitmapLoader.BitmapLoadListener {
        private final Context mContext;

        private SimpleDateFormat mFormat;
        private Cursor mData;
        private LayoutInflater mInflater;

        private AsyncBitmapLoader mBitmapLoader;

        public MyAdapter(Context context, Cursor data) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mData = data;

            mFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);

            // 이미지 로딩
            mBitmapLoader = new AsyncBitmapLoader(context);
            mBitmapLoader.setBitmapLoadListener(this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.listview_item_movie, parent, false);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    EventBus.getDefault().post(new ButtonClickEvent(1));
////                    EventBus.getDefault().post(new MyCustomEvent());
//                    EventBus.getDefault().post(1000);
//                }
//            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            mData.moveToPosition(position);
            Cursor cursor = mData;

            // cursor 에서 데이터를 가져옴
            final String title = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
            final String date = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));

            // 화면에 설정
            holder.title.setText(title);
            String dateText = mFormat.format(new Date(Long.valueOf(date)));
            holder.date.setText(dateText);

            // 이미지 로드
            mBitmapLoader.loadBitmap(position, holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mData.getCount();
        }

        @Override
        public long getItemId(int position) {
            mData.moveToPosition(position);
            Cursor cursor = mData;
            return cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        }

        public Bitmap getThumbnail(int position) {
            long id = getItemId(position);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(
                    mContext.getContentResolver(), id, MediaStore.Video.Thumbnails.MINI_KIND,
                    options);

            return thumbnail;
        }

        @Override
        public Bitmap getBitmap(int position) {
            return getThumbnail(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView title;
            TextView date;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
                title = (TextView) itemView.findViewById(R.id.tv_title);
                date = (TextView) itemView.findViewById(R.id.tv_date);
            }
        }
    }

}
