
package com.suwonsmartapp.hello.listview.data;

/**
 * Created by junsuk on 15. 3. 19..
 */
public class Person {
    private String name;
    private String skill;
    private String birthday;

    public Person(String birthday, String name, String skill) {
        this.birthday = birthday;
        this.name = name;
        this.skill = skill;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
