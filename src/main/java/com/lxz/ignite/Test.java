package com.lxz.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;

/**
 * Created by xiaolezheng on 16/5/4.
 */
@Slf4j
public class Test {
    private static final String config = "/usr/local/app/apache-ignite-fabric-1.5.0/examples/config/example-cache.xml";
    public static void main(String[] args)throws Exception{

        try(Ignite ignite = Ignition.start(config)) {

// Initialize atomic sequence.
            final IgniteAtomicSequence seq = ignite.atomicSequence("seqName", 0, true);

// Increment atomic sequence.
            for (int i = 0; i < 20; i++) {
                long currentValue = seq.get();
                long newValue = seq.incrementAndGet();

                log.info("current: {}, next: {}", currentValue, newValue);
            }
        }

        try(Ignite ignite = Ignition.start(config)){
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache1");
            for(int i=0;i<1000;i++){
                cache.put(i,Integer.toString(i));
            }

            for(int i=0; i<1000; i++){
                log.info("Got [key={}, val={}]", i, cache.get(i));
            }
        }

        try(Ignite ignite = Ignition.start(config)) {

            IgniteCluster cluster = ignite.cluster();
            // Local Ignite node.
            ClusterNode localNode = cluster.localNode();

// Node metrics.
            ClusterMetrics metrics = localNode.metrics();

// Get some metric values.
            double cpuLoad = metrics.getCurrentCpuLoad();
            long usedHeap = metrics.getHeapMemoryUsed();
            int numberOfCores = metrics.getTotalCpus();
            int activeJobs = metrics.getCurrentActiveJobs();

            log.info("{}", metrics);
        }

    }
}
