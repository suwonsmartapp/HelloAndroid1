
package com.suwonsmartapp.hello.challenge.challenge04;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.suwonsmartapp.hello.R;

public class SubActivity extends ActionBarActivity {

    Button mCustomerBtn;
    Button mSalesBtn;
    Button mMerchantBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mCustomerBtn = (Button) findViewById(R.id.customerBtn);
        mSalesBtn = (Button) findViewById(R.id.salesBtn);
        mMerchantBtn = (Button) findViewById(R.id.merchantBtn);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog((Button) v);
            }
        };

        mCustomerBtn.setOnClickListener(onClickListener);
        mSalesBtn.setOnClickListener(onClickListener);
        mMerchantBtn.setOnClickListener(onClickListener);
    }

    private void showDialog(Button v) {
        final String title = v.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity.this);
        builder.setTitle(title);
        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SubActivity.this, title + "에서 닫혔음", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        builder.show();
    }

}
