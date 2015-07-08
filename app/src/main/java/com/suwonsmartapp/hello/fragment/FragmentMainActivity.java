package com.suwonsmartapp.hello.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.suwonsmartapp.hello.R;

public class FragmentMainActivity extends ActionBarActivity implements UpFragment.OnImageChangeListener {

    private UpFragment mFragmentUp;
    private DownFragment mFragmentDown;

    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fragment_main);

        // Android 기본 제공하는 FragmentManager
//        getFragmentManager();

        // v4 용 FragmentManager
        // Fragment는 View가 아니므로 findViewById 로 가져올 수 없다
        mFragmentUp = (UpFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_up);
        mFragmentDown = (DownFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_down);

        mDualPane = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        mFragmentUp.setOnImageChangeListener(this);

    }

    @Override
    public void onImageChanged() {
//        Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_SHORT).show();
        if (mDualPane) {
            mFragmentDown.setChangeImage(R.drawable.pic300_7);
        } else {
            Intent intent = new Intent(getApplicationContext(), DownFragmentActivity.class);
            startActivity(intent);
        }
    }
}
