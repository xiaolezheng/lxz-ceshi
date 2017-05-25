package com.lxz.main;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaolezheng on 17/1/20.
 */
@Slf4j
public class Main {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        //threadUnSafeTest();
        threadSafeTest();
    }

    private static void threadUnSafeTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000000; i++) {
                        try {
                            Date date =DATE_FORMAT.parse("2014-01-01 00:00:00");
                            //log.info("{}",date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000000);
    }

    private static void threadSafeTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000000; i++) {
                        try {
                            Date date =FAST_DATE_FORMAT.parse("2014-01-01 00:00:00");
                            //log.info("{}",date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000000);
    }
}
