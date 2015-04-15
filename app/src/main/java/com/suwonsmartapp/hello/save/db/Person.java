
package com.suwonsmartapp.hello.save.db;

/**
 * Created by junsuk on 15. 4. 15..
 */
public class Person {
    private String name;
    private String email;

    public Person() {
        this.name = "";
        this.email = "";
    }

    public Person(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
