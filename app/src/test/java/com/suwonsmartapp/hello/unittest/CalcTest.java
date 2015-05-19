package com.suwonsmartapp.hello.unittest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by junsuk on 15. 5. 19..
 */
public class CalcTest {

    @Before
    public void setUp() throws Exception {
        // 테스트 시작 시 호출 되는 곳
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 종료 시 호출 되는 곳
    }

    @Test
    public void testSum() throws Exception {
        Calc calc = new Calc();
        int result = calc.sum(1, 2);

        Assert.assertEquals(result, 2);
//        Assert.assertNotSame(result, 2);
    }
}