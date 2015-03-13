
package com.suwonsmartapp.hello.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class ActivityExamActivity extends ActionBarActivity {

    private static final String TAG = ActivityExamActivity.class.getSimpleName();
    private static final int REQUEST_CODE_A = 0;
    private static final int REQUEST_CODE_B = 1;

    private Button mMoveBtn;
    private Button mDataMoveBtn;

    private EditText mDataEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_exam);

        mMoveBtn = (Button) findViewById(R.id.moveBtn);
        mDataMoveBtn = (Button) findViewById(R.id.dataMoveBtn);

        mDataEditText = (EditText) findViewById(R.id.dataEditText);

        mMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TargetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mDataMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TargetActivity.class);
                String value = mDataEditText.getText().toString();
                intent.putExtra("key", value);
                startActivityForResult(intent, REQUEST_CODE_B);
            }
        });

        findViewById(R.id.resultBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TargetActivity.class);
                startActivityForResult(intent, REQUEST_CODE_A);
            }
        });

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_A && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), data.getStringExtra("data"), Toast.LENGTH_SHORT)
                    .show();
        } else if (requestCode == REQUEST_CODE_B) {
            Toast.makeText(getApplicationContext(), "REQUEST CODE B", Toast.LENGTH_SHORT)
                    .show();
        }
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
