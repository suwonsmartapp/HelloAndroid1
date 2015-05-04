
package com.suwonsmartapp.hello.challenge.challenge17;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class MovieListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private MovieAdapter mAdapter;

    private ListView mMovieListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mMovieListView = (ListView) findViewById(R.id.lv_movies);

        // 아답터 셋팅
        mAdapter = new MovieAdapter(getApplicationContext(), null, true);
        mMovieListView.setAdapter(mAdapter);
        mMovieListView.setOnItemClickListener(this);

        // 로더 초기화
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // 커서 로더 생성
        // 모든 Video 데이터 취득
        return new CursorLoader(getApplicationContext(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // 화면 갱신
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 로더 파괴
        getSupportLoaderManager().destroyLoader(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)mAdapter.getItem(position);
        String dataUri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(dataUri), "video/*");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "지원되는 프로그램 없음", Toast.LENGTH_SHORT).show();
        }
    }
}
