
package com.suwonsmartapp.hello.save.preference;

import com.suwonsmartapp.hello.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * http://developer.android.com/training/basics/data-storage/shared-preferences.
 * html 저장 위치 : data/data/패키지명/Shared_prefs/어쩌구저쩌구.xml <?xml version='1.0'
 * encoding='utf-8' standalone='yes' ?> <map> <boolean name="backup"
 * value="true" /> </map>
 */
public class SharedPreferenceActivity extends ActionBarActivity implements
        CompoundButton.OnCheckedChangeListener {

    private static final String KEY_BACKUP = "backup";

    private CheckBox mBackupCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        mBackupCheckBox = (CheckBox) findViewById(R.id.cb_backup);
        mBackupCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean backup = sharedPref.getBoolean(KEY_BACKUP, false);

        mBackupCheckBox.setChecked(backup);
    }

    private void setBackup(boolean set) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_BACKUP, set);
        editor.apply();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setBackup(isChecked);
    }
}
