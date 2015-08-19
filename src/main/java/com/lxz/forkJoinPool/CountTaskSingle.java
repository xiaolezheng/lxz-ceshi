package com.lxz.forkJoinPool;

import java.io.File;


public class CountTaskSingle {
    static int sum = 0;

    public void countDir(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                countDir(f);
        } else
            sum++;

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CountTaskSingle ins = new CountTaskSingle();

        ins.countDir(new File("/home/xiaole/qunar/git"));
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start);
    }
}
