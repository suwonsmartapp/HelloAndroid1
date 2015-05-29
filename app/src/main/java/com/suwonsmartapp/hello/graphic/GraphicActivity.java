package com.suwonsmartapp.hello.graphic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.suwonsmartapp.hello.R;

public class GraphicActivity extends ActionBarActivity implements MusicPlayerView.OnPlayerButtonClickListener {

    private MusicPlayerView mMusicPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        mMusicPlayerView = (MusicPlayerView)findViewById(R.id.musicPlayer);
        mMusicPlayerView.setOnPlayerButtonClickListener(this);
    }

    @Override
    public void onAudioPlayButtonClicked() {
        // FileChooser 사용
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(Intent.createChooser(i, "파일선택..."), MusicPlayerView.REQUEST_CODE_AUDIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MusicPlayerView.REQUEST_CODE_AUDIO && resultCode == RESULT_OK) {
            // Audio
            Uri fileUri = data.getData();

            //　음악 재생
            mMusicPlayerView.startMusic(fileUri);
        }
    }
}
