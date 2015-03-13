
package com.suwonsmartapp.hello.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class TargetActivity extends ActionBarActivity {

    private static final String TAG = TargetActivity.class.getSimpleName();
    private EditText mTelephoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);


        // 내가 호출된 Intent 객체
        Intent intent = getIntent();

        // 호출한 Activity 에서 넘어온 데이터를 받는 방법
        if (intent != null) {
            String value = intent.getStringExtra("key");
            if (TextUtils.isEmpty(value) == false) {
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }
        }

        findViewById(R.id.moveBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityExamActivity.class);
                startActivity(intent);
            }
        });

        // 호출한 Activity에 돌려줄 데이터를 set 하고 finish() 하는 버튼 이벤트
        findViewById(R.id.finishBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", "smartapp");

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        // 전화걸기
        mTelephoneNumberEditText = (EditText)findViewById(R.id.telEditText);
        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = mTelephoneNumberEditText.getText().toString();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tel)));
            }
        });


        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
