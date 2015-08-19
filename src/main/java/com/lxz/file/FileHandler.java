/*
* Copyright (c) 2014 Qunar.com. All Rights Reserved.
*/
package com.lxz.file;

import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author: xiaole  Date: 15-5-25 Time: 下午10:25
 * @version: \$Id$
 */
public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    public static void main(String[] args) throws Exception{
       /* BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.txt",true),"utf-8"));

        RandomGenerator gen = new JDKRandomGenerator();
        gen.setSeed(1000000);

        long count = 1024*1024*1024*4L;
        for(long i=0; i<count; i++){
            writer.write(Math.abs(gen.nextInt())+"\n");
            if(i%1000 ==0)
                writer.flush();
        }*/

        long index = 0;

        BloomFilter<Long> bloomFilter =  BloomFilter.create(Funnels.longFunnel(), 200000000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt"),"utf-8"));
        while (true){
            String line = reader.readLine();
            if(line == null)
                return;

            bloomFilter.put(Long.valueOf(line));
            index ++;

            if(index %50000 == 0) {
                logger.info("index: {},exists: {}, key: {}", index, bloomFilter.mightContain(Long.valueOf(line)), line);
            }
        }



        /*long index = 0;
        List<Object> list = Lists.newArrayList();
        while(true){
            list.add(new Object());
            index ++;

            logger.info("index: {}",index);
        }*/


        // logger.info("numOfBits: {}", optimalNumOfBits(10000,0.03));
    }

    public static long optimalNumOfBits(long n, double p){
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
}