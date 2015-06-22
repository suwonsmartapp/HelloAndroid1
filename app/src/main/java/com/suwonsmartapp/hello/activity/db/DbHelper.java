package com.suwonsmartapp.hello.activity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by junsuk on 15. 6. 19..
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, "Address", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB 생성 SQL
        Log.d(TAG, "onCreate");
        String sql = "CREATE TABLE Address (_id integer auto increment, name text, email text, phone text);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 안 써요
    }

    // 등록 기능
    public long 추가(String 이름, String 이메일, String 전화번호) {
        Log.d(TAG, 이름 + ", " + 이메일 + "," + 전화번호 + " 등록 완료");

        // SQL 모를 때 추가 방법
        ContentValues 삽입할데이터 = new ContentValues();
        삽입할데이터.put("name", 이름);
        삽입할데이터.put("email", 이메일);
        삽입할데이터.put("phone", 전화번호);

        return getWritableDatabase().insert("Address", null, 삽입할데이터);
    }

    // 전체 데이터 반환
    public ArrayList<String> 검색() {
        return 검색(null);
    }

    /**
     * 전체검색 또는 조건을 주어서 검색
     * @param 검색할이름 null 이면 전체검색, null 이 아니면 조건 검색
     * @return 검색 결과
     */
    public ArrayList<String> 검색(String 검색할이름) {

        String 조건 = null;
        if (검색할이름 != null) {
            조건 = "name='" + 검색할이름 + "'";
        }

        Cursor cursor = getReadableDatabase().query("Address",
                new String[]{"name", "email", "phone"},
                조건,
                null,
                null,
                null,
                null);

        ArrayList<String> list = new ArrayList<>();

        if (cursor != null) {
            // 데이터가 있을 때
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String email = cursor.getString(1);
                String phone = cursor.getString(2);
                list.add(name + ", " + email + ", " + phone);
            }
        }

        return list;
    }

//    public void 검색(String 검색할이름) {
//        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM Address WHERE name = '" + 검색할이름 + "'", null);
//
////        getReadableDatabase().query("Address", new String[] { "name", "email", "phone"},
////                "name = ?", new String[] { 검색할이름 }, null, null, null);
//    }
}
