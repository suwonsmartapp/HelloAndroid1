package com.suwonsmartapp.hello.save.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by junsuk on 15. 5. 4..
 */
public class PersonTest {

    @Test
    public void testGetEmail() {
        Person person = new Person();
        person.setName("testName");

        Person person1 = new Person();
        person1.setName("testName");

        Assert.assertEquals(person.getName(), "testName");
        Assert.assertNotSame(person, person1);
    }
}