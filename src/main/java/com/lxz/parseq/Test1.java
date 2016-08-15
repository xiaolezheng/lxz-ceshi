/*
* Copyright (c) 2014 . All Rights Reserved.
*/
package com.lxz.parseq;

import com.google.common.base.Optional;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.function.Success;
import com.linkedin.parseq.httpclient.HttpClient;
import com.linkedin.parseq.promise.Promise;
import com.linkedin.parseq.promise.PromiseListener;
import com.linkedin.parseq.trace.TraceUtil;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiaole  Date: 15-5-17 Time: 下午7:15
 * @version: \$Id$
 */
public class Test1 {
    private static final Logger logger = LoggerFactory.getLogger(Test1.class);

    public static void main(String[] args) throws Exception {
        final int numCores = Runtime.getRuntime().availableProcessors();
        final ExecutorService taskScheduler = Executors.newFixedThreadPool(numCores + 1);
        final ScheduledExecutorService timerScheduler = Executors.newSingleThreadScheduledExecutor();

        final Engine engine = new EngineBuilder()
                .setTaskExecutor(taskScheduler)
                .setTimerScheduler(timerScheduler)
                .build();


        Task<Response> head = HttpClient.head("http://www.baidu.com").task().withTimeout(1, TimeUnit.SECONDS);
        Task<String> contentType = head.map("toContentType", response -> response.getContentType());
        Task<String> printContentType = contentType.andThen("print", System.out::println);
        Task<String> logFailure = contentType.onFailure("print stack trace", e -> e.printStackTrace());

        engine.run(printContentType);
        engine.run(logFailure);

        String trace = TraceUtil.getJsonTrace(printContentType);

        logger.info("trace: {}", trace);

        logger.info("-------------------------------------------------------------------------------------------");
        logger.info("-------------------------------------------------------------------------------------------");

        Task<Response> get = HttpClient.get("http://www.baidu.com").task();
        Task<Optional<String>> contents = get.transform("getContents", tryGet -> {
            if (tryGet.isFailed()) {
                return Success.of(Optional.fromNullable("fail"));
            } else {
                return Success.of(Optional.of(tryGet.get().getResponseBody()));
            }
        });
        contents.addListener(new PromiseListener<Optional<String>>() {
            @Override
            public void onResolved(Promise<Optional<String>> promise) {
                logger.info(promise.get().get());
            }
        });

        engine.run(contents);

        logger.info("-------------------------------------------------------------------------------------------");
        logger.info("-------------------------------------------------------------------------------------------");


        final Task<String> googleContentType = getContentType("http://www.baidu.com");
        final Task<String> bingContentType = getContentType("http://www.bing.com");
        final Task<String> contentTypes =
                Task.par(googleContentType, bingContentType)
                        .map("concatenate", (google, bing) -> "Google: " + google + "\n" +
                                "Bing: " + bing + "\n");

        engine.run(contentTypes);


        engine.shutdown();
        engine.awaitTermination(1, TimeUnit.SECONDS);
        taskScheduler.shutdown();
        timerScheduler.shutdown();
    }

    private static Task<String> getContentType(String url) {
        return HttpClient.get(url).task()
                .map("getContentType", response -> response.getContentType());
    }
}