package com.suwonsmartapp.hello.multimedia;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();

    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_RESUME = "action_resume";
    public static final String ACTION_START = "action_start";
    public static final String ACTION_STOP_SERVICE = "action_stop_service";

    // 플레이어
    private MediaPlayer mMediaPlayer;

    private final IBinder mBinder = new MusicServiceBinder();

    public class MusicServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        String action = intent.getAction();
        switch (action) {
            case ACTION_START:
                startMusic(intent);
                break;
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumeMusic();
                break;
            case ACTION_STOP_SERVICE:
                pauseMusic();
                stopSelf();
                break;
        }

        return START_NOT_STICKY;
    }

    private Intent getBroadCastIntent() {
        return new Intent(MediaPlayerActivity.BROADCAST_START_MUSIC);
    }

    public void startMusic(Intent intent) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        Uri fileUri = intent.getParcelableExtra("uri");

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(MusicService.this, fileUri);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    public int getCurrentPosition() {
        if (mMediaPlayer == null) {
            return -1;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    public void setCurrentPosition(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

}
