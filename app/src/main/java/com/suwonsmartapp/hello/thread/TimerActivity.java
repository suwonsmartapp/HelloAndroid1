
package com.suwonsmartapp.hello.thread;

import com.suwonsmartapp.hello.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends ActionBarActivity {

    long mTimeInMilliseconds = 0L;
    long mTimeSwapBuff = 0L;
    long mUpdatedTime = 0L;
    private Button mStartButton;
    private Button mPauseButton;
    private TextView mTimerValue;
    private long mStartTime = 0L;
    private Handler mCustomHandler = new Handler();
    protected Runnable mUpdateTimerThread = new Runnable() {

        @Override
        public void run() {
            mTimeInMilliseconds = SystemClock.uptimeMillis() - mStartTime;

            mUpdatedTime = mTimeSwapBuff + mTimeInMilliseconds;

            int secs = (int) (mUpdatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (mUpdatedTime % 1000);
            mTimerValue.setText(String.format("%02d", mins) + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            mCustomHandler.postDelayed(this, 0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimerValue = (TextView) findViewById(R.id.timerValue);

        mStartButton = (Button) findViewById(R.id.startButton);
        mPauseButton = (Button) findViewById(R.id.pauseButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mStartTime = SystemClock.uptimeMillis();
                mCustomHandler.postDelayed(mUpdateTimerThread, 0);
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTimeSwapBuff += mTimeInMilliseconds;
                mCustomHandler.removeCallbacks(mUpdateTimerThread);
            }
        });
    }

}
