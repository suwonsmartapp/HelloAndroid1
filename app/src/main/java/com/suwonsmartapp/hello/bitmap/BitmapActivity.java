
package com.suwonsmartapp.hello.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.suwonsmartapp.hello.R;

public class BitmapActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mImageView = (ImageView) findViewById(R.id.iv_image);

        // 색 채우기
        findViewById(R.id.btn_fill_color).setOnClickListener(this);
        // 이미지 로드
        findViewById(R.id.btn_image_load).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fill_color:
                fillColor();
                break;

            case R.id.btn_image_load:
                imageLoad();
                break;
        }

    }

    private void imageLoad() {
        // 리소스 로드
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dmyo_2jpg_);

        // 스케일
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 300, 150, true);

        mImageView.setImageBitmap(scaled);
    }

    private void fillColor() {
        Bitmap bitmap = Bitmap.createBitmap(500, 700, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                bitmap.setPixel(x, y, Color.RED);
            }
        }

        mImageView.setImageBitmap(bitmap);
    }
}
