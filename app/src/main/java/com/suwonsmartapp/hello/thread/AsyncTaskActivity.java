
package com.suwonsmartapp.hello.thread;

import com.suwonsmartapp.hello.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncTaskActivity extends ActionBarActivity implements View.OnClickListener {
    private TextView mProgressTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        mProgressTextView = (TextView) findViewById(R.id.tv_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        findViewById(R.id.btn_thread).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_thread:
                // AsyncTask 실행
                new ProgressBarTask().execute();
                break;
        }
    }

    /**
     * AsyncTask 사용 룰
     * 
     * <pre>
     * 1. AsyncTask 인스턴스 생성 (new ) 은 반드시 UI Thread 에서 한다
     * 2. .execute() 도 반드시 UI Thread 에서 한다
     * 3. onPreExecute(), onPostExecute(Result), doInBackground(Params...), onProgressUpdate(Progress...) 를 직접 호출 하지 않는다
     * 4. 실행은 한번만 할 수 있다
     * </pre>
     */
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
