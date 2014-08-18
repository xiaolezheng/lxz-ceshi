/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.util;

import com.lxz.dto.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lxz.dto.GradeType;
import com.lxz.dto.Student;

/**
 * @author: xiaole Date: 14-8-13 Time: 上午11:41
 * @version: \$Id$
 */
public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 复制对象
     * 
     * @param orig
     * @param dest
     */
    public static void copyProperties(Object orig, Object dest) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(orig, dest);

        } catch (Exception e) {
            logger.error("orig: {}, dest: {}", orig, dest, e);
        }
    }

    /**
     * 复制对象
     * 
     * @param orig
     */
    public static <T> T copyProperties(Object orig, Class<?> destClassType) {
        try {
            Object dest = destClassType.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
            return (T) dest;
        } catch (Exception e) {
            logger.error("orig: {}", orig, e);
        }

        return null;
    }

    public static void main(String[] args) {
        Student p = new Student();
        p.setName("jim");
        p.setAge(12);
        p.setSupports(Lists.newArrayList("足球", "游泳"));
        p.setGradeType(GradeType.A);

        /* Person s = BeanUtils.copyProperties(p,Person.class); */

        Person s = new Person();

        BeanUtils.copyProperties(p, s);

        logger.info("p: {}, s:{}, {}", p, s, s.getAge());
    }
}
