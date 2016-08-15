/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.ceshi.junit;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * -verbose:gc -XX:+PrintGCDetails -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author: xiaole  Date: 14-5-28 Time: 下午1:01
 * @version: \$Id$
 */
public class JavaMethodAreaOOM {

    static class OOMObject{}
    
    public static void main(String[] args){
        while(true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invoke(obj,args);
                }
            });
            enhancer.create();
        }
    }
}
