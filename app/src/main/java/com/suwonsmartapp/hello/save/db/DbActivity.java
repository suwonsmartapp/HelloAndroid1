
package com.suwonsmartapp.hello.save.db;

import com.suwonsmartapp.hello.R;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DbActivity extends ActionBarActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private Button mSubmitBtn;
    private ListView mPersonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        mNameEditText = (EditText) findViewById(R.id.et_name);
        mEmailEditText = (EditText) findViewById(R.id.et_email);
        mSubmitBtn = (Button) findViewById(R.id.btn_submit);
        mPersonListView = (ListView) findViewById(R.id.lv_person);

        PersonHelper dbHelper = new PersonHelper(getApplicationContext());
        // DB가 없을 때 dbHelper.onCreate 가 호출되며 DB가 생성 됨
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // dbHelper.getReadableDatabase();

        // db.execSQL("INSERT INTO Person (name, email) VALUES ('오준석', 'a811219@gmail.com')");
        ContentValues values = new ContentValues();
        values.put("name", "오준석");
        values.put("email", "a811219@gmail.com");
        long id = db.insert("Person", null, values);

        Toast.makeText(getApplicationContext(), "insert id : " + id, Toast.LENGTH_SHORT).show();
    }

}
