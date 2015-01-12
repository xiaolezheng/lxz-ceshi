/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole  Date: 14-12-28 Time: 上午11:48
 * @version: \$Id$
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String zookeeperConnectionString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

        try {
            client.start();

            client.create().forPath("/zk_test", "helloWorld".getBytes());
        } catch (Exception e) {
            logger.error("",e);
        } finally {
            client.close();
        }

    }
}
