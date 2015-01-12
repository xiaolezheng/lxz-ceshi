package com.lxz.ceshi.vertx;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.http.HttpServerRequest;

import java.util.concurrent.TimeUnit;

public class ServerExample {

    public static void main(String[] args) throws Exception{
        Vertx vertx = VertxFactory.newVertx(); 
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {

                String file = req.path().equals("/") ? ".gitconfig" : req.path();
                req.response().sendFile("/home/xiaole/git" + file);
            }
        }).listen(8081);


    }
}
