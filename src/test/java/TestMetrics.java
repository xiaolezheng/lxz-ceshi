import java.util.concurrent.TimeUnit;


import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import com.yammer.metrics.reporting.ConsoleReporter;
import org.junit.Test;

/**
 * TODO
 * 
 * @author scutshuxue.chenxf
 */
public class TestMetrics {
    private static Histogram histo = Metrics.newHistogram(TestMetrics.class, "histo-sizes");
    private static final Timer responses = Metrics.newTimer(TestMetrics.class, "responses");


    @Test
    public void testHis() throws InterruptedException {
        // TODOAuto-generated method stub

        ConsoleReporter.enable(1, TimeUnit.SECONDS);
        int i = 0;
        while (true) {
            histo.update(i++);
            Thread.sleep(100);
        }
    }

    @Test
    public void testTimer() throws Exception{
        ConsoleReporter.enable(1, TimeUnit.SECONDS);

        TimerContext context = responses.time();
        try {
            // etc;
            TimeUnit.MICROSECONDS.sleep(10);
        } finally {
            context.stop();
        }
    }

}
