package com.suwonsmartapp.hello.multimedia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.suwonsmartapp.hello.R;

import java.io.IOException;

/**
 * 챕터 9. 멀티미디어
 */
public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnFilePick;    // 파일 선택
    private Button mBtnPause;       // 정지
    private Button mBtnResume;      // 재시작

    // 플레이어
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        // 레이아웃 초기화, 이벤트 연결
        init();
    }

    private void init() {
        mBtnFilePick = (Button) findViewById(R.id.btn_filePick);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnResume = (Button) findViewById(R.id.btn_resume);

        mBtnFilePick.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnResume.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filePick:
                // FileChooser 사용
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("audio/*");
                startActivityForResult(Intent.createChooser(i, "파일선택..."), 0);
                break;
            case R.id.btn_pause:
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                }
                break;
            case R.id.btn_resume:
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri fileUri = data.getData();
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(getApplicationContext(), fileUri);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

                mBtnFilePick.setText(fileUri.getPath());
            } catch (IOException e) {
                // IOException 이 났을 때 다른 익셉션을 발생시켜서 죽인다
                throw new RuntimeException("io exception");
            }
        }
    }
}
