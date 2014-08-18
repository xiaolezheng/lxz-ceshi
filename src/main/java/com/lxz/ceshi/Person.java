/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.ceshi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author: xiaole  Date: 14-7-31 Time: 下午5:01
 * @version: \$Id$
 */
public class Person {
    private int sex;
    private int age;

    public Person(int sex, int age) {
        this.sex = sex;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}