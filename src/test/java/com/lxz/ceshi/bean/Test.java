package com.lxz.ceshi.bean;

import com.lxz.bean.Student;
import com.lxz.ceshi.SpringBaseTest;

import javax.annotation.Resource;

/**
 * Created by xiaolezheng on 15/9/9.
 */
public class Test extends SpringBaseTest {
    @Resource
    private Student student;

    @org.junit.Test
    public void testSet(){
        System.out.println(student);
    }
}
