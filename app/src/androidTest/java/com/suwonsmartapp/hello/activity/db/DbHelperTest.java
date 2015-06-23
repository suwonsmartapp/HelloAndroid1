package com.suwonsmartapp.hello.activity.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * JUnit4 unit tests for the calculator logic.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DbHelperTest {

    private Context mMockContext;
    private DbHelper mDbHelper;

    @Before
    public void setUp() throws Exception {
        // 일반적인 Context 취득
//        mMockContext = InstrumentationRegistry.getContext();

        // DB 테스트를 위한 Context 취득
        mMockContext = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");

        mDbHelper = new DbHelper(mMockContext);
    }

    @Test
    public void test추가() throws Exception {
        long result = mDbHelper.추가("abc", "abc.com", "123-123");
        Assert.assertThat(result, is(not(-1L)));

    }

}