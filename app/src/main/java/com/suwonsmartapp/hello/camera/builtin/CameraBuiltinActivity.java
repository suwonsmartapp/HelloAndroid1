package com.suwonsmartapp.hello.camera.builtin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

import java.io.IOException;

// <uses-permission android:name="android.permission.CAMERA" /> 매니페스트 추가 해야 됨
// 1. 카메라 프리뷰 보기 위해 SurfaceView 를 써야만 됨. 고속 렌더링이 가능 View
// 2. SurfaceView 의 holder 에 Callback 을 연결. SurfaceHolder.Callback 구현
// 3. Camera 객체를 각가의 콜백에서 사용
public class CameraBuiltinActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private SurfaceView mPreview;
    private Button mBtnShutter;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_builtin);

        mPreview = (SurfaceView) findViewById(R.id.surfaceView);
        mBtnShutter = (Button) findViewById(R.id.btn_shutter);

        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mBtnShutter.setOnClickListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 카메라 메모리 해제
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onClick(View v) {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // 1차원 배열 data를 2차원 data로 변환해서 Bitmap 으로 리턴
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, null);

                // 사진폴더에 저장. 파일 이름은 자동으로 부여 됨
                String savedImageUri = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", null);

                // 저장된 파일의 uri 를 취함
                Uri uri = Uri.parse(savedImageUri);

                // Media Scan
                // 사진 앱 들의 db를 갱신하기 위해서 (사진 앱을 실행했을 때 이 파일이 바로 검색 되도록)
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

                Toast.makeText(CameraBuiltinActivity.this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();

                // 다시 프리뷰를 작동
                camera.startPreview();
            }
        });
    }


    // 참고
    //
    // 1. SurfaceView 상속
    // 2. SurfaceHolder.Callback 구현
    // 3. 생성자에서 getHolder().addCallback(this)
    public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private Camera mCamera;

        public CameraSurfaceView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = Camera.open();

            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 카메라 메모리 해제
            mCamera.release();
            mCamera = null;
        }
    }
}
