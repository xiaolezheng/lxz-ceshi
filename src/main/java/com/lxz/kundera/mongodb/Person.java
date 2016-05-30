package com.lxz.kundera.mongodb;

import com.lxz.util.JsonUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by xiaolezheng on 16/5/30.
 */
@Entity
@Table(name="person", schema = "testDB@mongo_pu")
public class Person {
    /** The person id. */
    @Id
    @Column(name = "person_id")
    private long personId;

    /** The person name. */
    @Column(name = "person_name")
    private String personName;

    /** The age. */
    @Column(name = "age")
    private int age;
    // setters and getters.

    @Column(name = "createTime")
    private long createTime;

    @Column(name = "updateTime")
    private long updateTime;


    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String toString(){
        return JsonUtil.encode(this);
    }
}
