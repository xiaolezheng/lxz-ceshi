/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.dto;

/**
 * @author: xiaole  Date: 14-8-13 Time: 下午1:09
 * @version: \$Id$
 */
public enum GradeAType {
    A(1,"DI"),B(2,"MID");
    private int code;
    private String text;
    private GradeAType(int code,String text){
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
