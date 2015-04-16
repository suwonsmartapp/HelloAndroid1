
package com.suwonsmartapp.hello.challenge.challenge07_08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by junsuk on 15. 4. 15..
 */
public class CalendarDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Calendar.db";
    public static final String TABLE_NAME = "Calendar";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TODO = "todo";
    public static final String COLUMN_WEATHER = "weather";
    public static final String COLUMN_TIME = "time";
    private static final int DATABASE_VERSION = 1;

    public CalendarDbHelper(Context context) {
        // 부모의 생성자 호출
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // DB 가 없을 때 DB 생성 하는 부분
    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE Person (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        // date TEXT,
        // todo TEXT,
        // weather TEXT,
        // time TEXT);
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TODO + " TEXT, "
                + COLUMN_WEATHER + " TEXT, "
                + COLUMN_TIME + " TEXT);");
    }

    // DB 구조 변경 되었을 때, 처리
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValues(schedule);
        long id = db.insert(TABLE_NAME, null, values);
        return id;
    }

    private ContentValues getContentValues(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, schedule.getDate());
        values.put(COLUMN_TODO, schedule.getTodo());
        values.put(COLUMN_WEATHER, schedule.getWeather());
        values.put(COLUMN_TIME, schedule.getTime());
        return values;
    }

    /**
     * 모든 데이타를 Cursor 로 받음
     * 
     * @return _id, date, todo, weather, time
     */
    public Cursor selectAll() {
        // SELECT * FROM Calendar;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

    /**
     * 특정날짜의 스케쥴 정보
     * 
     * @param date yyyyMMdd 형태
     * @return
     */
    public Cursor select(String date) {
        // SELECT * FROM Calendar WHERE date='20150515';

        // ? 에 데이타 바인딩 하는 방법
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE date=?",
                new String[] {
                    date
                });
//        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE date='" + date + "'",
//                null);
    }

    /**
     * id 값에 해당하는 data를 삭제
     * 
     * @param id _id
     * @return 삭제 된 data 수
     */
    public int delete(long id) {
        // DELETE FROM Person WHERE _id = id;
        SQLiteDatabase db = getWritableDatabase();

        int deletedRowCount = db.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
        return deletedRowCount;
    }

    public int update(long id, Schedule schedule) {
        // UPDATE Person SET name='person.name', email='person.email' WHERE _id
        // = id;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = getContentValues(schedule);

        int updatedCount = db.update(TABLE_NAME, contentValues, BaseColumns._ID + " = " + id, null);
        return updatedCount;
    }

}
