/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.esper;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author: xiaole  Date: 14-3-12 Time: 下午9:39
 * @version: \$Id$
 */
public class RequestEvent implements Serializable{

    private static final long serialVersionUID = 3759062256058655723L;
    private long id;
    private long counter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}