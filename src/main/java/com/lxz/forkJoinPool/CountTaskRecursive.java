package com.lxz.forkJoinPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CountTaskRecursive extends RecursiveTask {

    int sum = 0;
    File file;

    public CountTaskRecursive(File file) {
        this.file = file;
    }

    @Override
    protected Integer compute() {
        Integer csum = 0;
        List<CountTaskRecursive> tasklist = new ArrayList<CountTaskRecursive>();
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                CountTaskRecursive t = new CountTaskRecursive(f);
                tasklist.add(t);
            }
        } else
            csum++;
        if (!tasklist.isEmpty()) {
            for (CountTaskRecursive t : invokeAll(tasklist))
                csum += (Integer) t.join();
        }
        return csum;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CountTaskRecursive task = new CountTaskRecursive(new File("/home/xiaole/qunar/git"));
        Integer sum = (Integer) new ForkJoinPool().invoke(task);
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start);
    }
}