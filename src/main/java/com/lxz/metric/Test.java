package com.lxz.metric;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.lxz.metric.listener.MyMetricsServletContextListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaolezheng on 15/9/5.
 */

@Service
public class Test {
    @Resource
    private MetricRegistry metrics;

    private ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    public Test() {
        reporter.start(3, TimeUnit.SECONDS);

        Meter meter = metrics.meter("test");
        int i = 0;
        while (i++ < Long.MAX_VALUE){
            meter.mark();
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
