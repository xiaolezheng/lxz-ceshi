package com.lxz.ceshi.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServerExample {
    private static final Logger logger = LoggerFactory.getLogger(ServerExample.class);
    private Vertx vertx = Vertx.vertx();

    @Test
    public void testFileSystem() throws Exception {
        FileSystem fs = vertx.fileSystem();
        /*fs.copy("/home/xiaole/tmp/EC122.zip", "/home/xiaole/tmp/EC122.BAK.zip", result -> {
            if (result.succeeded()) {
                logger.info("success, result: {}");
            } else {
                logger.info("fail, result: {}", result.cause());
            }
        });*/

        //fs.copyBlocking("/home/xiaole/tmp/EC122.zip", "/home/xiaole/tmp/EC1223.BAK.zip");

        List<String> list = fs.readDirBlocking("/home/xiaole/tmp/");
        list.forEach(item -> logger.info(item));
    }

    @Test
    public void testNet() {
        NetServer server = vertx.createNetServer(
                new NetServerOptions().setPort(1234).setHost("localhost")
        );
        server.connectHandler(sock -> {
            sock.handler(buffer -> {
                // Write the data straight back
                sock.write(buffer);
            });
        }).listen();
    }
}
