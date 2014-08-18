/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz;

/**
 * @author: xiaole Date: 14-8-18 Time: 下午5:02
 * @version: \$Id$
 */
public enum MessageType {

    NO_ROOMS(1, "满房预警信息"), LOSE_PRICE(2, "返现前价格预警信息"), CLOSE_ROOM(3, "照妖镜关房预警信息"), PS_ORDER(4, "PS订单信息"), OFF_ROOM(5,
            "连续关房信息"), ON_ROOM(6, "开房信息"), CONTRACT_REJECT(7, "合同驳回信息"), CTPACKAGE_REJECT(8, "方案驳回信息"), OUT_CUSTOMER(9,
            "掉出私海警告信息");

    private final int code;
    private final String name;

    private MessageType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int code() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return this.name;
    }

    public static MessageType codeOf(int code) {
        for (MessageType e : MessageType.values()) {
            if (e.code == code)
                return e;
        }
        return null;
    }

    public static void main(String[] args){
        String messageType = "LOSE_PRICE";
        MessageType messageType1 = Enum.valueOf(MessageType.class, messageType);
        System.out.println(messageType1.name().toLowerCase());
    }
}
