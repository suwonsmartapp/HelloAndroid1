
package com.suwonsmartapp.hello.thread;

import com.suwonsmartapp.hello.R;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                new LoginTask().execute();
                break;
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            // 로그인 form 감추기
            findViewById(R.id.login_form).setVisibility(View.INVISIBLE);
            // 로그인 progress 보이기
            findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
                publishProgress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            findViewById(R.id.login_progress).setVisibility(View.GONE);
            findViewById(R.id.login_form).setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("접속 되었습니다");
            builder.setNegativeButton("닫기", null);
            builder.show();
        }
    }
}
