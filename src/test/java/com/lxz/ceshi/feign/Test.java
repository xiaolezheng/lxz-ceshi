package com.lxz.ceshi.feign;

import lombok.extern.slf4j.Slf4j;
import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.codec.StringDecoder;

/**
 * Created by xiaolezheng on 15/9/15.
 */
@Slf4j
public class Test {

    interface Maven {
        @RequestLine("GET /artifact/com.netflix.feign/feign-slf4j/{version}")
        String show(@Param("version") String version);
    }

    public static void main(String[] args) {
        Maven maven = Feign.builder().decoder(new StringDecoder()).target(Maven.class, "http://mvnrepository.com/");

        String result = maven.show("8.10.0");

        log.info(result);
    }
}
