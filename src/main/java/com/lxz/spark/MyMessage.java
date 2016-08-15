/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.spark;

/**
 * @author: xiaole  Date: 15-1-11 Time: 下午10:06
 * @version: \$Id$
 */
public class MyMessage {
    private long id;
    private String content;
    public MyMessage(){
    }

    public MyMessage(String content){
        this.content = content;
        this.id = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}