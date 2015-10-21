package com.lxz.mongo;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.lxz.pg.FormData;
import com.lxz.util.JsonUtil;
import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.FindObservable;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.util.StopWatch;
import rx.Observable;
import rx.Subscriber;

import java.sql.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaolezheng on 15/10/20.
 */
@Slf4j
public class MongoRx {
    static String selectSql = "SELECT id, class, \"dataId\", \"tenantId\", \"formId\", \"userId\", \"updateTime\", \n" +
            "       status, fields\n" +
            "  FROM \"xFormData\" limit 100000";

    static String insertSql = "INSERT INTO \"xFormData\"(\n" +
            "            class, \"dataId\", \"tenantId\", \"formId\", \"userId\", \"updateTime\", \n" +
            "            status, fields)\n" +
            "    VALUES ( ?, ?, ?, ?, ?, ?, \n" +
            "            ?, ?::jsonb);\n";

    static Random RANDOM = new Random();
    static int SEED = 20;

    public static void main(String[] args) throws Exception {
        // makeDataInsertMongo();
        queryDataFromMongo();
    }

    public static void queryDataFromMongo() throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.1.117:27017");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("xForm");

        MongoCollection<Document> collection = mongoDatabase.getCollection("xFormData");

        final AtomicInteger index = new AtomicInteger(0);
        FindObservable<Document> findObservable = collection.find(Filters.eq("formId", "fm_0a5ea0c3cfaa33d5093d74987b3ac35_14"));
        findObservable.subscribe(new Subscriber<Document>() {
            @Override
            public void onCompleted() {
                log.info("comp-->1");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error-->1");

            }

            @Override
            public void onNext(Document document) {
                log.info("next-->{}, {}",index.getAndIncrement(), document);
            }
        });


        TimeUnit.SECONDS.sleep(10);
        mongoClient.close();
    }

    private static void makeDataInsertMongo() {
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.1.117:27017");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("xForm");

        MongoCollection<Document> collection = mongoDatabase.getCollection("xFormData");

        StopWatch watch = new StopWatch();
        watch.start();

        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            FormData formData = createFormData();
            Document document = Document.parse(JsonUtil.encode(formData));


            Observable<Success> observable = collection.insertOne(document);

            observable.subscribe(new Subscriber<Success>() {
                @Override
                public void onCompleted() {
                    count.getAndIncrement();
                    log.info("completed");
                }

                @Override
                public void onError(Throwable throwable) {
                    log.error("", throwable);
                }

                @Override
                public void onNext(Success success) {
                    log.info("Inserted");
                }
            });

        }

        if (count.get() == 100) {
            mongoClient.close();
        }

        watch.stop();

        log.info(watch.prettyPrint());
    }

    public static FormData createFormData() {
        FormData formData = FormData.builder();
        int rand = rand();

        formData.setTenantId(createTenantId(rand));
        formData.setFormId(createFormId(rand));
        formData.setStatus(0);
        formData.setUpdateTime(new Date(System.currentTimeMillis()));
        formData.setUserId(1L);
        formData.setFields(createFields(rand));

        return formData;
    }

    public static Map<String, Object> createFields(int rand) {
        String field1 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field2 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field3 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;
        String field4 = "fm_f_dbe58ab52d57b2fc7e7a4361de9479f" + rand++;

        String value1 = "apple" + RANDOM.nextInt(100);
        int value2 = RANDOM.nextInt(1000);
        int value3 = RANDOM.nextInt(1000);
        String value4 = "A123123132AASDASD" + RANDOM.nextInt(100);

        return ImmutableMap.of(field1, value1, field2, value2, field3, value3, field4, value4);
    }


    public static String createTenantId(int rand) {
        return Joiner.on('_').join("bdp", rand);
    }

    public static String createFormId(int rand) {
        return Joiner.on('_').join("fm_0a5ea0c3cfaa33d5093d74987b3ac35", rand);
    }

    private static int rand() {
        return RANDOM.nextInt(SEED);
    }

}
