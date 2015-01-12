/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.spark;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;
import static spark.Spark.port;

/**
 * @author: xiaole  Date: 15-1-11 Time: 下午9:39
 * @version: \$Id$
 */
public class HelloWorld {
    public static void main(String[] args) {
        externalStaticFileLocation("/home/xiaole/temp"); // Static files

        port(8080);

        get("/hello", (req, res) -> "Hello World");

        get("/hi/h", "application/json", (request, response) -> {
                    return new MyMessage("Hello World");
                }, new JsonTransformer());

        before((request, response) -> {
            System.out.println("helloworld.................");
        });

        after((request, response) -> {
            response.header("foo", "set by after filter");
        });

        exception(Exception.class, (e, request, response) -> {
            response.status(404);
            response.body("Resource not found");
        });
    }
}
