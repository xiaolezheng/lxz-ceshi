/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author: xiaole  Date: 14-8-13 Time: 上午11:48
 * @version: \$Id$
 */
public class Person {
    private int age;
    private String name;
    private List<String> supports;
    private GradeAType gradeType;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GradeAType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeAType gradeType) {
        this.gradeType = gradeType;
    }

    public List<String> getSupports() {
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}