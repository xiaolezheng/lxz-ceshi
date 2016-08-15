/*
 * Copyright (c) 2014 . All Rights Reserved.
 */
package com.lxz.ceshi.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-12-28 Time: 下午12:00
 * @version: \$Id$
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    private static final String zookeeperConnectionString = "127.0.0.1:2181";
    private static final CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString,
            retryPolicy);
    private static final String path = "/zk_ceshi";

    @BeforeClass
    public static void setUp() {
        client.start();
    }

    @AfterClass
    public static void destory() {
        client.close();
    }

    @org.junit.Test
    public void createNode() throws Exception {
        client.create().forPath(path, "你好".getBytes());
        byte[] data = client.getData().forPath(path);
        logger.info("create data: {}", new String(data));

        client.getData().watched().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                logger.info("back watch date", new String(event.getPath()));
            }
        }).forPath(path);

        client.setData().forPath(path, "helloWorld".getBytes());

        client.delete().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                logger.info("back delete date", new String(event.getPath()));
            }
        }).forPath(path);

        data = client.getData().forPath(path);
        logger.info("delete data: {}", new String(data));
    }

    @org.junit.Test
    public void distributedLock() {

    }

    @org.junit.Test
    public void distributedQueue() {

    }
}
