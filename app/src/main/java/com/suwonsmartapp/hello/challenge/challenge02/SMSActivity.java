
package com.suwonsmartapp.hello.challenge.challenge02;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class SMSActivity extends ActionBarActivity {

    private EditText mMessageEditText;
    private TextView mByteTextView;
    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);

        mByteTextView = (TextView) findViewById(R.id.byteTextView);
        mSendBtn = (Button) findViewById(R.id.sendBtn);

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().getBytes().length;
                mByteTextView.setText(length + " / 80 바이트");
            }
        });

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mMessageEditText.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
