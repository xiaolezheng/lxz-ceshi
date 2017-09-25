package com.lxz;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaolezheng on 17/6/13.
 */
@Slf4j
public class Test {
    public static void main(String[] args){
        try {
            Long.valueOf("ab");
        }catch (Exception e){
            log.error("test ", e);
        }
    }
}
