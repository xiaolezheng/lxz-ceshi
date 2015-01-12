import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.reporting.ConsoleReporter;

/**
 * TODO
 * 
 * @author scutshuxue.chenxf
 */
public class TestGauges {
    public static Queue<String> queue = new LinkedList<String>();

    public static void main(String[] args) throws InterruptedException {
        ConsoleReporter.enable(5, TimeUnit.SECONDS);

        Gauge<Integer> g = Metrics.newGauge(TestGauges.class, "pending-jobs", new Gauge<Integer>() {
            @Override
            public Integer value() {
                return queue.size();
            }
        });

        System.out.println(g.value());
        while (true) {
            Thread.sleep(1000);
            queue.add("ssss");
        }
    }
}
