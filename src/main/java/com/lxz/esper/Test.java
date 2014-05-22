/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.esper;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiaole Date: 14-3-12 Time: 下午9:47
 * @version: \$Id$
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws InterruptedException {

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

        EPAdministrator admin = epService.getEPAdministrator();

        String product = RequestEvent.class.getName();
        String epl = "select count(*),min(id),max(id) from " + product + ".win:time_length_batch(1 sec, 10)";

        EPStatement state = admin.createEPL(epl);
        state.addListener(new RequestListener());

        /*
         * String epl2 = "select counter from "+product+".win:time(1 sec)"; EPStatement state2 = admin.createEPL(epl2);
         * state2.addListener(new RequestListener());
         */

        EPRuntime runtime = epService.getEPRuntime();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 5, 3L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
        pool.execute(new RequestTask(runtime));
        //pool.execute(new RequestTask(runtime));
        //pool.execute(new RequestTask(runtime));
        //pool.execute(new RequestTask(runtime));

        TimeUnit.MINUTES.sleep(1);

        pool.shutdown();
    }

    public static class RequestTask implements Runnable {
        private EPRuntime runtime;

        public RequestTask(EPRuntime runtime) {
            this.runtime = runtime;
        }

        @Override
        public void run() {
            Long counter = 1L;
            while (true) {
                try{
                    TimeUnit.MILLISECONDS.sleep(120);
                }catch (Exception e){
                    logger.error("",e);
                }
                //logger.debug("time: {}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss SSS"));
                RequestEvent request = new RequestEvent();
                request.setId(counter);
                request.setCounter(counter++);
                runtime.sendEvent(request);
            }
        }
    }
}
