package com.lxz.etcd;

import lombok.extern.slf4j.Slf4j;
import mousio.etcd4j.EtcdClient;

/**
 * Created by xiaolezheng on 16/12/29.
 */
@Slf4j
public class EtcdTest {
    public static void main(String[] args) throws Exception{
        try(EtcdClient etcd = new EtcdClient()){
            // Logs etcd version
            log.info("{}", etcd.version());
        }
    }
}
