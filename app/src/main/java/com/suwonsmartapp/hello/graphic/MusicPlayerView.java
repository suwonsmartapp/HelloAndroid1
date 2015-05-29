package com.suwonsmartapp.hello.graphic;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.suwonsmartapp.hello.R;

import java.io.IOException;

/**
 * Created by junsuk on 15. 5. 29..
 */
public class MusicPlayerView extends LinearLayout implements View.OnClickListener {

    public static final int REQUEST_CODE_AUDIO = 0;
    public static final int REQUEST_CODE_VIDEO = 1;

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

    private Context mContext;

    public MusicPlayerView(Context context) {
        this(context, null);
    }

    public MusicPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.activity_media_player, this);

        // 레이아웃 초기화, 이벤트 연결
        init();
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
//        mBtnVideoFilePick.setOnClickListener(this);
//        mBtnPause.setOnClickListener(this);
//        mBtnResume.setOnClickListener(this);
//        mSeekBar.setOnSeekBarChangeListener(this);
//        mPlayTime.setOnChronometerTickListener(this);
    }

    public void startMusic(Uri fileUri) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mContext, fileUri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onAudioPlayButtonClicked();    // 외부에 콜백이 호출 됨
        }
    }

    private OnPlayerButtonClickListener mListener;

    public void setOnPlayerButtonClickListener(OnPlayerButtonClickListener listener) {
        mListener = listener;
    }

    public interface OnPlayerButtonClickListener {
        public void onAudioPlayButtonClicked();     // call back
    }

    // ViewGroup 의 위치 결정
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    // ViewGroup 의 크기를 결정
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
