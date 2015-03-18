
package com.suwonsmartapp.hello.challenge.challenge06;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.suwonsmartapp.hello.R;

public class Mission06MainActivity extends ActionBarActivity {

    private Button mShowAddressBtn;
    private Button mShowWebViewBtn;

    private LinearLayout mAddressLayout;
    private EditText mAddressEditText;

    private WebView mWebView;

    private Animation mTranslationAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission06_main);

        // ActionBar 안 보이게 하기
        // getActionBar().hide();
        getSupportActionBar().hide();

        mShowAddressBtn = (Button) findViewById(R.id.showAddressBtn);
        mShowWebViewBtn = (Button) findViewById(R.id.showWebViewBtn);
        mAddressLayout = (LinearLayout) findViewById(R.id.addressLayout);
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);
        mWebView = (WebView) findViewById(R.id.webview);

        // 페이지 이동 시 새로운 창이 아닌 현재 webView 에 표시
        mWebView.setWebViewClient(new WebViewClient());

        // Animation 객체 생성
        mTranslationAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.translation);
        mShowWebViewBtn.setAnimation(mTranslationAnimation);

        mShowAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddressLayout.getVisibility() == View.VISIBLE) {
                    mAddressLayout.setVisibility(View.GONE);
                } else if (mAddressLayout.getVisibility() == View.GONE) {
                    mAddressLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mShowWebViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(mAddressEditText.getText().toString());
            }
        });
    }

}
