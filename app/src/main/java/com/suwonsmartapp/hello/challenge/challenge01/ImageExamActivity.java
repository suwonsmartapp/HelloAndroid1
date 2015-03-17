
package com.suwonsmartapp.hello.challenge.challenge01;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.suwonsmartapp.hello.R;

/**
 * page 271
 */
public class ImageExamActivity extends ActionBarActivity {

    private ImageView mImage1;
    private ImageView mImage2;

    private Button mImgDownBtn;
    private Button mImgUpBtn;

    private BitmapDrawable mBitmap;

    private View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_exam);

        mImgDownBtn = (Button) findViewById(R.id.btn_img_down);
        mImgUpBtn = (Button) findViewById(R.id.btn_img_up);

        mImage1 = (ImageView) findViewById(R.id.img_1);
        mImage2 = (ImageView) findViewById(R.id.img_2);

        mBitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.pic300_7);

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(v.getId());
            }
        };

        mImgDownBtn.setOnClickListener(listener);
        mImgUpBtn.setOnClickListener(listener);

    }

    private void changeImage(int viewId) {
        if (viewId == R.id.btn_img_down) {
            mImage2.setImageDrawable(mBitmap);
            mImage1.setImageDrawable(null);
        } else if (viewId == R.id.btn_img_up) {
            mImage1.setImageDrawable(mBitmap);
            mImage2.setImageDrawable(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_exam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
