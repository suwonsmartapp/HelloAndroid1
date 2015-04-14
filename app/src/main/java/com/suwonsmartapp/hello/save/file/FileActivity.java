
package com.suwonsmartapp.hello.save.file;

import com.suwonsmartapp.hello.R;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mFileNameEditText;
    private Button mInternalButton;
    private Button mExternalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        mFileNameEditText = (EditText) findViewById(R.id.et_filename);
        mInternalButton = (Button) findViewById(R.id.btn_save_internal);
        mExternalButton = (Button) findViewById(R.id.btn_save_external);

        mInternalButton.setOnClickListener(this);
        mExternalButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_internal:
                saveInternal();
                break;
            case R.id.btn_save_external:
                saveExternal();
                break;
        }
    }

    // /mnt/shell/emulated/0/Android/data/패키지명/files/
    private void saveExternal() {
        if (isExternalStorageWritable()) {
            Toast.makeText(getApplicationContext(), "path: " + getExternalFilesDir(null),
                    Toast.LENGTH_SHORT).show();
            File file = new File(getExternalFilesDir(null), mFileNameEditText.getText().toString());

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                // true = append
                FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write("append data");
                bufferWritter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // /data/data/패키지명/files/
    private void saveInternal() {
        File file = new File(getFilesDir(), mFileNameEditText.getText().toString());

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

}
