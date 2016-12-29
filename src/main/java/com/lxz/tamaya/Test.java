package com.lxz.tamaya;

import lombok.extern.slf4j.Slf4j;
import org.apache.tamaya.format.ConfigurationData;
import org.apache.tamaya.format.ConfigurationFormats;
import org.apache.tamaya.json.JSONFormat;

import java.util.Map;

/**
 * Created by xiaolezheng on 16/12/18.
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception{
        ConfigurationData dataRead = ConfigurationFormats.readConfigurationData(
                Thread.currentThread().getContextClassLoader().getResource("config.json"),
                new JSONFormat());


        Map config = dataRead.getCombinedProperties();

        log.info("{}", config);
        log.info("{}", config.get("a"));
    }
}
