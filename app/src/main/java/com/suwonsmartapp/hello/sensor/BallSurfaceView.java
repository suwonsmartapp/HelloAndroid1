
package com.suwonsmartapp.hello.sensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.suwonsmartapp.hello.R;

public class BallSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Rect mRect;

    private Drawable mDrawable;

    private Ball mBall;

    private SensorManager mSensorManager;
    private float mPitch;
    private float mRoll;

    private SurfaceHolder mHolder;
    private Thread mThread;
    private Sensor mSensor;

    public BallSurfaceView(Context context) {
        super(context);
        initialize(context);
    }

    public BallSurfaceView(
            Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    public BallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        mDrawable = context.getResources().getDrawable(
                R.drawable.ball);

        mPitch = 0;
        mRoll = 0;

        mSensorManager = (SensorManager) context.getSystemService(
                Context.SENSOR_SERVICE);

        mSensorManager.registerListener(
                new SensorEventListener() {
                    @Override
                    public void onAccuracyChanged(
                            Sensor sensor, int accuracy) {
                    }

                    @Override
                    public void onSensorChanged(
                            SensorEvent event) {
                        mPitch = event.values[SensorManager.DATA_Y];
                        mRoll = event.values[SensorManager.DATA_Z];
                    }
                },
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFixedSize(getWidth(), getHeight());

    }

    public void surfaceChanged(
            SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        mThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread = null;
    }

    public void run() {
        Canvas canvas = null;
        Paint p = new Paint();
        p.setColor(Color.WHITE);

        while (mThread != null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }

            try {
                canvas = mHolder.lockCanvas();

                if (canvas != null) {
                    canvas.drawRect(0, 0, getWidth(), getHeight(), p);

                    if (mBall == null) {
                        mRect = new Rect(0, 0, getWidth(), getHeight());

                        mBall = new Ball(mDrawable, mRect);
                    }

                    mBall.move(mPitch, mRoll);
                    mBall.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
