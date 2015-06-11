package com.suwonsmartapp.hello.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 환경설정 및 구현방법
 *
 * Android Studio 1.3 preview 이상에서
 *
 * 1. SDK Manager 에서 Android NDK 를 다운로드 한다
 * 2. app 에서 New -> Folder - JNI Folder (c 폴더가 생김, src/main/jni)
 * 3. Android.mk, Application.mk, hello-jni.c 를 복사 한다. (/ndk-bundle/samples/hello-jni 참고)
 * 4. Android.mk 를 수정
 * 5. JNI를 통해 실행할 메소드에는 native 키워드를 붙인다
 * 6. c 쪽에 정확한 경로로 함수를 정의하고 구현한다. return 은 앞에 j를 붙인다
 *
 * 컴파일
 * 1. build.gradle 에 JNI library 폴더를 지정해 준다
 * 2. 터미널을 열고 Android.mk 파일이 있는 곳에서 ndk-build (so 파일이 생기는 것 확인)
 * 3. Android 를 빌드한다
 */
public class JNIActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText(getString());

        setContentView(textView);

        // Java
        long start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < 100000000; i++) {
            if (i % 2 == 0) {
                sum += i;
            } else {
                sum -= i;
            }
        }
        long end = System.currentTimeMillis();
        Log.d("JNI", "JAVA : " + (end - start) + "ms, result: " + sum);

        // C
        start = System.currentTimeMillis();
        long result = calc();
        end = System.currentTimeMillis();
        Log.d("JNI", "C : " + (end - start) + "ms, result: " + result);
    }

    // hello-jni.c 에 구현이 되어 있음
    public native String getString();

    public native long calc();

    // 외부 라이브러리를 로드 할 때는 항상 static 으로 로드 해야 됨
    static {
        System.loadLibrary("hello-jni");
    }
}
