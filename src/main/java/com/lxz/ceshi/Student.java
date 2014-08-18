/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.ceshi;

/**
 * @author: xiaole  Date: 14-7-31 Time: 下午5:01
 * @version: \$Id$
 */
public class Student extends Person{
    private int grade;

    public Student(int sex, int age, int grade) {
        super(sex, age);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

}