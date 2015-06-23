package com.suwonsmartapp.hello.save.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by junsuk on 15. 5. 19..
 */
@SmallTest
public class PersonHelperTest {
    PersonHelper mHelper;
    Context mContext;

    // 테스트 수행 시 처음에 실행되는 메소드
    @Before
    public void setUp() throws Exception {
        // 가상 Context
//        mContext = Robolectric.application.getApplicationContext();

        // 가상 Context 사용으로 매 테스트시마다 새로운 DB를 생성한다
        mHelper = new PersonHelper(mContext);
    }

    // 테스트 종료 시 수행되는 메소드
    @After
    public void tearDown() throws Exception {
        mHelper.close();
    }

    /**
     * Insert 테스트
     * @see PersonHelper#insert(Person)
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        long id = mHelper.insert(new Person("test,", "abc@abc.com"));
        Assert.assertNotSame(-1, id);
    }

    /**
     * Insert 1000번 수행 테스트
     * @throws Exception
     */
    @Test
    public void testInsert1000() throws Exception {
        for (int i = 0; i < 1000; i++) {
            mHelper.insert(new Person("test", "abc@abc.com"));
        }
    }

    /**
     * Insert 1000번을 Transaction 사용하여 테스트
     * @throws Exception
     */
    @Test
    public void testInsert1000WithTransaction() throws Exception {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();

        try {

            for (int i = 0; i < 1000; i++) {
                db.rawQuery("INSERT INTO Person (name, email) VALUES ('test', 'abc@abc.com')", null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Insert 1000번을 Transaction 과 SQLiteStatement 사용하여 테스트
     *
     * @throws Exception
     */
    @Test
    public void testInsert1000WithTransactionAndStatement() throws Exception {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.beginTransaction();

        try {
            SQLiteStatement statement = db.compileStatement("INSERT INTO Person (name, email) VALUES (?, ?)");

            for (int i = 0; i < 1000; i++) {
                statement.bindString(1, "test");
                statement.bindString(2, "abc@abc.com");
                statement.executeInsert();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Test
    public void testSelectAll() throws Exception {
    }

    /**
     * Delete 테스트
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        mHelper = new PersonHelper(mContext);
        mHelper.insert(new Person("test", "abc@abc.com"));

        Cursor cursor = mHelper.selectAll();
        Assert.assertEquals(1, cursor.getCount());

        mHelper.delete(1);
        cursor = mHelper.selectAll();
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void testUpdate() throws Exception {

    }
}