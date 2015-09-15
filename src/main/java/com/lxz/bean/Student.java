package com.lxz.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Created by xiaolezheng on 15/9/9.
 */
public class Student {
    private Set<String> rules;

    private String rule1;

    public Student(Set<String> rules, String rule1) {

        this.rules = rules;
        this.rule1 = rule1;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
