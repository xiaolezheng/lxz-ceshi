/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.esper;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * @author: xiaole Date: 14-3-12 Time: 下午9:41
 * @version: \$Id$
 */
public class RequestListener implements UpdateListener {
    private static final Logger logger = LoggerFactory.getLogger(RequestListener.class);

    private Object lock = new Object();

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        //synchronized (lock) {
            if (newEvents != null) {
                Long count = (Long) newEvents[0].get("count(*)");
                Long minId = (Long) newEvents[0].get("min(id)");
                Long maxId = (Long) newEvents[0].get("max(id)");
                logger.debug("Request count: {},result: {}", count, new Object[] { minId, maxId });
                /*if (count >= 5) {
                    logger.debug("sleep in----------------------------------------------------------");
                    logger.debug("Request count: {},result: {}", count, new Object[] { minId, maxId });
                    try {
                        TimeUnit.MILLISECONDS.sleep(700);
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }
                    logger.debug("sleep out----------------------------------------------------------");
                } else {
                    //logger.debug("Request count: {},result: {}", count, new Object[] { minId, maxId });
                }*/
            }
        //}
    }
}
