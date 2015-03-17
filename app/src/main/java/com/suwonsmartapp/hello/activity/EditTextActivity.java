
package com.suwonsmartapp.hello.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.suwonsmartapp.hello.R;

/**
 * page 185
 */
public class EditTextActivity extends ActionBarActivity {

    private static final String TAG = EditTextActivity.class.getSimpleName();
    private EditText mInputEditText;
    private EditText mOutputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        mInputEditText = (EditText) findViewById(R.id.input);
        mOutputEditText = (EditText) findViewById(R.id.output);

        // EditText에 입력이 될 때 마다 호출 되는 인터페이스 메소드
        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                Log.d(TAG, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                Log.d(TAG, "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged");

                mOutputEditText.setText(s);


//                String sss = "222";
//                sss.getBytes().length;
            }
        });

//        mInputEditText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP
//                        && keyCode == KeyEvent.KEYCODE_ENTER) {
//                    textChange();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    /**
     * mInputEditText 의 내용을 mOutputEditText 에 set
     */
    private void textChange() {
        mOutputEditText.setText(mInputEditText.getText());
    }
}
