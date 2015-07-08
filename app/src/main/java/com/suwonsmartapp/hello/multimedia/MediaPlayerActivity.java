package com.suwonsmartapp.hello.multimedia;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.suwonsmartapp.hello.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 챕터 9. 멀티미디어
 */
public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Chronometer.OnChronometerTickListener {

    private static final int REQUEST_CODE_AUDIO = 0;
    private static final int REQUEST_CODE_VIDEO = 1;
    private static final String TAG = MediaPlayerActivity.class.getSimpleName();

    private Button mBtnAudioFilePick;    // 파일 선택
    private Button mBtnVideoFilePick;    // 파일 선택
    private Button mBtnPause;       // 정지
    private Button mBtnResume;      // 재시작

    private TextView mTvArtist;
    private TextView mTvAlbum;
    private TextView mTvTitle;
    private Chronometer mPlayTime;
    private TextView mTvDuration;
    private SeekBar mSeekBar;
    private ImageView mIvAlbumArt;

    // 플레이어
    private MediaPlayer mMediaPlayer;

    private VideoView mVideoView;
    private long mStartTime = 0;
    private long mStopTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        // 레이아웃 초기화, 이벤트 연결
        init();


        // 다른 앱에서 호출 될 때의 처리
        if (getIntent() != null) {
            Uri uri = getIntent().getData();
            if (uri != null) {
                startMusic(uri);
            }
        }

        if (savedInstanceState != null) {
            mLastUri = savedInstanceState.getParcelable("uri");
            Log.d(TAG, "onCreate : " + mLastUri);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("uri", mLastUri);
        Log.d(TAG, "onSaveInstanceState : " + mLastUri);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 서비스에서 정보를 가져오기 위해 바인드
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mLastUri != null) {
            setMetaInfo(mLastUri);
        }
    }

    private void init() {
        mBtnAudioFilePick = (Button) findViewById(R.id.btn_audioFilePick);
        mBtnVideoFilePick = (Button) findViewById(R.id.btn_videoFilePick);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnResume = (Button) findViewById(R.id.btn_resume);
        mVideoView = (VideoView) findViewById(R.id.videoView);

        mTvArtist = (TextView) findViewById(R.id.tv_artist);
        mTvAlbum = (TextView) findViewById(R.id.tv_album);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mPlayTime = (Chronometer) findViewById(R.id.chronometer_play_time);
        mTvDuration = (TextView) findViewById(R.id.tv_duration);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar_play_time);
        mIvAlbumArt = (ImageView) findViewById(R.id.iv_albumArt);

        mBtnAudioFilePick.setOnClickListener(this);
        mBtnVideoFilePick.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnResume.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mPlayTime.setOnChronometerTickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.btn_audioFilePick:
                // FileChooser 사용
                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("audio/*");
                startActivityForResult(Intent.createChooser(i, "파일선택..."), REQUEST_CODE_AUDIO);
                break;
            case R.id.btn_videoFilePick:
                // FileChooser 사용
                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("video/*");
                startActivityForResult(Intent.createChooser(i, "파일선택..."), REQUEST_CODE_VIDEO);
                break;
            case R.id.btn_pause:
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                }
                if (mService != null) {
                    Log.d(TAG, "서비스에서 가져온 값 : " + mService.getCurrentPosition());
                }
                startService(getMusicServiceIntent(MusicService.ACTION_PAUSE));
                mPlayTime.stop();
                mStopTime = SystemClock.elapsedRealtime();
                break;
            case R.id.btn_resume:
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
                startService(getMusicServiceIntent(MusicService.ACTION_RESUME));
                mPlayTime.setBase(SystemClock.elapsedRealtime() - mStopTime + mStartTime);
                mPlayTime.start();
                break;
        }
    }

    private Intent getMusicServiceIntent(String action) {
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        intent.setAction(action);
        return intent;
    }

    private Uri mLastUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AUDIO && resultCode == RESULT_OK) {
            // Audio
            Uri fileUri = data.getData();
            mLastUri = fileUri;

            // 정보 셋팅
            setMetaInfo(fileUri);

            //　음악 재생
            startMusic(fileUri);
        } else if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK) {
            // Video
            Uri fileUri = data.getData();

            mIvAlbumArt.setVisibility(View.INVISIBLE);
            mVideoView.setVisibility(View.VISIBLE);

            mVideoView.setVideoURI(fileUri);
            mVideoView.start();

            mBtnVideoFilePick.setText(fileUri.getPath());
        }
    }

    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("mm:ss");

    private void setMetaInfo(Uri fileUri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getApplicationContext(), fileUri);
        mTvAlbum.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        mTvTitle.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        mTvArtist.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        mTvDuration.setText(mTimeFormat.format(new Date(Long.valueOf(duration))));
        mSeekBar.setMax(Integer.valueOf(duration));

        // SeekBar 갱신
        mHandler.post(mUpdateSeekBarRunnable);

        // Notification 에 표시할 RemoteView
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentView.setTextViewText(R.id.notification_album, mTvAlbum.getText());
        contentView.setTextViewText(R.id.notification_title, mTvTitle.getText());
        contentView.setTextViewText(R.id.notification_artist, mTvArtist.getText());

        // 앨범 사진
        byte data[] = retriever.getEmbeddedPicture();
        if (null != data) {
            mVideoView.setVisibility(View.INVISIBLE);
            mIvAlbumArt.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            mIvAlbumArt.setImageBitmap(bitmap);

            contentView.setImageViewBitmap(R.id.notification_image, bitmap);
        }

//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication());

        Intent intent = new Intent(getApplicationContext(), MediaPlayerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_NO_CREATE);

//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
//                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setContent(contentView);
        NotificationManagerCompat.from(getApplicationContext()).notify(0, builder.build());
    }

    private void startMusic(Uri fileUri) {
//        if (mMediaPlayer != null) {
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//
//        try {
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setDataSource(getApplicationContext(), fileUri);
//            mMediaPlayer.prepare();
//            mMediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 음악 재생
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        intent.setAction(MusicService.ACTION_START);
        intent.putExtra("uri", fileUri);
        startService(intent);

        mBtnAudioFilePick.setText(fileUri.getPath());

//        Uri albumUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, fileUri);
//
//        Cursor cursor = getContentResolver().query(fileUri,
//                null,
//                null,
//                null,
//                null);
//        cursor.moveToFirst();
//        mTvArtist.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST)));
//        mTvAlbum.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM)));
//        mTvTitle.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
//        cursor.close();

        mPlayTime.setBase(mStartTime = SystemClock.elapsedRealtime());
        mPlayTime.start();
    }

    private MusicService mService;
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.MusicServiceBinder binder = (MusicService.MusicServiceBinder) service;
            mService = binder.getService();
            mBound = true;
            Log.d(TAG, "MusicService connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            // Service 가 의도된 상황이 아닌데 종료 될 경우에만 호출 됨
            Log.d(TAG, "MusicService disconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(getApplicationContext(), "볼륨 다운", Toast.LENGTH_SHORT).show();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(getApplicationContext(), "볼륨 업", Toast.LENGTH_SHORT).show();
        }

        return super.onKeyDown(keyCode, event);
    }


    public static final String BROADCAST_START_MUSIC = "BROADCAST_START_MUSIC";

    private Handler mHandler = new Handler();

    private Runnable mUpdateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBound && mService != null) {
                mSeekBar.setProgress(mService.getCurrentPosition());
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mService != null) {
            mService.setCurrentPosition(seekBar.getProgress());
            mPlayTime.setBase(mStartTime - seekBar.getProgress());
            mPlayTime.start();
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        if (chronometer.getText().equals(mTvDuration.getText())) {
            mPlayTime.stop();
        }
    }
}
