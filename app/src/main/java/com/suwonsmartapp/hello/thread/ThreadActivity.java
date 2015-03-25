
package com.suwonsmartapp.hello.thread;

import com.suwonsmartapp.hello.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Thread 는 한번만 사용할 수 있다. Thread 는 UI 변경을 할 수 없다. UI 변경은 Handler 를 통해 해야 한다.
 */
public class ThreadActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView mProgressTextView;
    private ProgressBar mProgressBar;
    private Handler mProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mProgressBar.getProgress() < mProgressBar.getMax()) {
                mProgressBar.incrementProgressBy(5);
                mProgressTextView.setText("진행률 : " + mProgressBar.getProgress());
                mProgressHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
    };
    private TextView mProgressTextView2;
    private ProgressBar mProgressBar2;
    private Handler mProgressHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // UI 업데이트
            mProgressBar2.setProgress(msg.what);
            mProgressTextView2.setText("진행률 : " + mProgressBar2.getProgress());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        mProgressTextView = (TextView) findViewById(R.id.tv_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        mProgressTextView2 = (TextView) findViewById(R.id.tv_progress2);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progress2);

        // 쓰레드
        findViewById(R.id.btn_thread).setOnClickListener(this);

        // toast
        findViewById(R.id.btn_toast).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_thread:
                // 위쪽 ProgressBar. 0.5초에 5 씩 증가하는 쓰레드
                // mProgressBar.setProgress(0);
                // Thread thread = new Thread(new Runnable() {
                // @Override
                // public void run() {
                // // 계산
                //
                // // UI 업데이트
                // mProgressHandler.sendEmptyMessage(0);
                // }
                // });
                // thread.start();
                new ProgressBarTask().execute();

                // 아래쪽 ProgressBar. 0.1초에 1 씩 증가하는 쓰레드
                mProgressBar2.setProgress(0);
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 계산
                        int progress = 0;
                        while (mProgressBar.getProgress() < mProgressBar.getMax()) {
                            progress += 1;
                            // UI 업데이트
                            mProgressHandler2.sendEmptyMessage(progress);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread2.start();
                break;
            case 2:
                // Handler 없이 run() 안에서 UI 변경을 하면 죽는다
                mProgressBar.setProgress(0);
                new Thread() {
                    @Override
                    public void run() {
                        // 죽는 경우 : mProgressTextView.setText("");

                        // 안 죽는 경우
                        mProgressHandler.sendEmptyMessage(0);
                    }
                }.start();
                break;
            case 3:
                // Handler 없이 Activity 에서 UI 를 변경할 수 있는 방법 (Activity 에서만 사용 가능)
                mProgressBar.setProgress(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressHandler.sendEmptyMessage(0);
                    }
                });
                break;
            case 4:
                // 안 죽는다
                mProgressBar.setProgress(0);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressHandler.sendEmptyMessage(0);
                    }
                });
                break;
            case R.id.btn_toast:
                Toast.makeText(getApplicationContext(), "토스트", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class ProgressBarTask extends AsyncTask<Void, Integer, Void> {
        private int progress;

        // 쓰레드가 수행 되기 전 초기화 등
        @Override
        protected void onPreExecute() {
            progress = 0;
            mProgressBar.setProgress(progress);
            Log.d(ProgressBarTask.class.getSimpleName(), "onPreExecute");
        }

        // background 에서 수행할 내용 (Thread 에서 수행할 내용)
        @Override
        protected Void doInBackground(Void... params) {
            // Background 작업 조건
            while (mProgressBar.getProgress() < mProgressBar.getMax()) {
                Log.d(ProgressBarTask.class.getSimpleName(), "doInBackground");
                progress += 5;

                mProgressBar.setProgress(progress);

                // onProgressUpdate 가 수행 되도록 하는 메소드
                publishProgress(progress);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // UI 업데이트 (Handler 에서 수행할 내용)
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressTextView.setText("진행률 : " + values[0]);

            Log.d(ProgressBarTask.class.getSimpleName(), "onProgressUpdate");
        }

    }
}
