/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.ceshi.junit;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-4-20 Time: 下午8:31
 * @version: \$Id$
 */
public class Javassist {
    private static final Logger logger = LoggerFactory.getLogger(Javassist.class);

    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("com.lxz.ceshi.junit.Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
        m.insertAfter("{ System.out.println(\"Hello.say():\"); }");
        Class c = cc.toClass();
        Hello h = (Hello) c.newInstance();
        h.say();
    }
}

class Hello {
    public void say() {
        System.out.println("Hello");
    }
}
