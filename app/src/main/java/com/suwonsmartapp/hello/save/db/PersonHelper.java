
package com.suwonsmartapp.hello.save.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by junsuk on 15. 4. 15..
 */
public class PersonHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Person.db";
    private static final String TABLE_NAME = "Person";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    public PersonHelper(Context context) {
        // 부모의 생성자 호출
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // DB 가 없을 때 DB 생성 하는 부분
    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT);
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_EMAIL
                + " TEXT);");
    }

    // DB 구조 변경 되었을 때, 처리
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Person 데이터를 DB에 삽입
     * @param person 이름, 이메일 정보
     * @return 삽입 된 id 값
     */
    public long insert(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        // db.execSQL("INSERT INTO Person (name, email) VALUES ('오준석', 'a811219@gmail.com')");
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_EMAIL, person.getEmail());
        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    /**
     * 모든 데이타를 Cursor 로 받음
     * @return _id, name, email
     */
    public Cursor selectAll() {
        // SELECT * FROM Person;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Person", null);
        return c;
    }

    /**
     * id 값에 해당하는 data를 삭제
     * @param id _id
     * @return 삭제 된 data 수
     */
    public int delete(long id) {
        // DELETE FROM Person WHERE _id = pk;
        SQLiteDatabase db = getWritableDatabase();

        int deletedRowCount = db.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
        Toast.makeText(mContext, "deletedRowCount : " + deletedRowCount, Toast.LENGTH_SHORT).show();
        return deletedRowCount;
    }

    public int update(long id, Person person) {
        // UPDATE Person SET name='person.name', email='person.email' WHERE _id = id;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, person.getName());
        contentValues.put(COLUMN_EMAIL, person.getEmail());

        int updatedCount = db.update(TABLE_NAME, contentValues, BaseColumns._ID + " = " + id, null);
        return updatedCount;
    }


}
