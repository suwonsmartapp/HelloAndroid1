
package com.suwonsmartapp.hello.save.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by junsuk on 15. 4. 15..
 */
public class PersonHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Person.db";
    private static final int DATABASE_VERSION = 1;

    public PersonHelper(Context context) {
        // 부모의 생성자 호출
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // DB 가 없을 때 DB 생성 하는 부분
    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name
        // TEXT, email TEXT);
        db.execSQL("CREATE TABLE Person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT);");
    }

    // DB 구조 변경 되었을 때, 처리
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
