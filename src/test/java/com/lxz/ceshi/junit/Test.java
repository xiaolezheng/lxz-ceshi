/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.ceshi.junit;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.lxz.ceshi.Person;
import com.lxz.ceshi.util.JsonUtils;
import com.lxz.util.JsonUtil;

/**
 * @author: xiaole Date: 14-2-20 Time: 下午12:32
 * @version: \$Id$
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final Object NULL = null;
    private static final String FLAG = "OK";
    private static Cache<String, Integer> cacheCounter = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS).build();

    public static enum FeeType {
        ALL_FREE, PART_FREE, ALL_CHARGE, UNKNOWN;
    }

    public static int getCounter(String key) {
        logger.debug("ces");
        try {
            return cacheCounter.get("counterKey", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    logger.debug("load counter");
                    return Integer.valueOf(2);
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }
        return -1;
    }

    public boolean testBoolean(int data) {
        if (data > 20)
            return false;
        for (int i = 1; i <= data; i++) {
            if (i % 15 == 0) {
                logger.debug("test: {}", i);
                return true;
            }
        }
        logger.debug("test last");
        return true;
    }

    public void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param source 源字符串
     * 包装隐私安全信息，进行中间打码处理
     *
     * @param replaceChar 打码字符
     * @return
     */
    public static String warpSecrecyMsg(String source, char replaceChar) {
        if (StringUtils.isNotEmpty(source)) {
            int length = source.length();
            int partStart = length / 3;
            int partEnd = partStart * 2;
            StringBuilder buf = new StringBuilder(length);
            buf.append(StringUtils.substring(source, 0, partStart));
            buf.append(StringUtils.repeat(replaceChar, partEnd - partStart));
            buf.append(StringUtils.substring(source, partEnd));
            return buf.toString();
        }
        return null;
    }





    public static int testTime() {
        long start = System.currentTimeMillis();
        try {
            int i = 10;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {

            }
            return i;
        } finally {
            logger.debug("time: {}", System.currentTimeMillis() - start);
        }
    }

    public static void print(String str) {
        if (StringUtils.isEmpty(str)) {
            return;
        }

        logger.debug("print: {}", str);
    }

    static class HotelBase{
        private int price;
        private String name;

        public HotelBase(int price,String name){
            this.price = price;
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "HotelBase{" +
                    "price=" + price +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static String buildResult(String name,String... requestSource){
        if(ArrayUtils.isNotEmpty(requestSource)){
            return Joiner.on('_').join(name,requestSource[0]);
        }

        return Joiner.on('_').join(name,"");
    }

    @org.junit.Test
    public void testIntegerToString(){
        Map<String,Object> json = Maps.newHashMap();
        json.put("ctserial", 2222+"");

        String ctSerial = String.valueOf(json.get("ctserial"));
        logger.info("ctSerial: {}",ctSerial);

        ctSerial = String.valueOf("DDD");
        logger.info("ctSerial: {}",ctSerial);
        ctSerial = String.valueOf(1111111);
        logger.info("ctSerial: {}",ctSerial);
    }

    @org.junit.Test
    public void testStringFormat(){
        String template = "%s-%s";
        logger.info("ddd: {}", String.format(template,"豪华房", "1"));
    }

    @org.junit.Test
    public void testDays(){
        String strCurDate = DateFormatUtils.format(DateUtils.addDays(new Date(),-2), "yyyyMMdd");
        logger.info("strCurDate: {}",strCurDate);
    }

    @org.junit.Test
    public void testAny(){
        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6);
        boolean flag = Iterables.any(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input.intValue() == 10;
            }
        });

        logger.info("testAny: {}",flag);
    }

    @org.junit.Test
    public void testBigDicmal(){
        BigDecimal bigDecimal = new BigDecimal("3");
        logger.info("test: {}", bigDecimal);

        BigDecimal groupPrice = new BigDecimal("120");
        BigDecimal finalPrice = new BigDecimal("117");

        logger.info("grossfit: {}",(groupPrice.subtract(finalPrice)).divide(groupPrice, 4, BigDecimal.ROUND_DOWN));

        String content = 1 + "#" + "测试";
        logger.info("ddddddd: {}, {}", StringUtils.substringBefore(content,"#"), StringUtils.substringAfter(content, "#"));
    }

    @org.junit.Test
    public void testCCCC(){
        logger.info(buildResult("jim"));
        logger.info(buildResult("tom", new String[]{"ps"}));
    }

    @org.junit.Test
    public void testJoiners(){
        logger.info(Joiner.on('_').join("crm_task","tuan","ps"));
    }

    @org.junit.Test
    public void testEquails(){
        logger.info("flag: {}", "leon".equals("Leon"));
        logger.info("flag: {}", "leon".equalsIgnoreCase("Leon"));
    }

    @org.junit.Test
    public void testSets(){
        Set<Integer> con = Sets.newHashSet(1,2,6);

        logger.info("{}",con.contains(2));
        logger.info("{}",con.contains(5));

        List<Integer> lists = Lists.newArrayList(1,2,3);
        logger.info("flag: {}", lists.contains(2));
        logger.info("flag: {}", lists.contains(0));
    }

    @org.junit.Test
    public void sortSS(){
        List<HotelBase> data = Lists.newArrayList(new HotelBase(5, "xiecheng"), new HotelBase(2, "aaa"), new HotelBase(2, "yilong"), new HotelBase(3,"yilong"),new HotelBase(2,"quanr"));

        // 按价格排序
        Collections.sort(data, new Comparator<HotelBase>() {
            @Override
            public int compare(HotelBase o1, HotelBase o2) {
                if(o1.getPrice() == o2.getPrice()){
                    if(o1.getName().equals("quanr")){
                        return -1;
                    }
                }

                return o1.getPrice() - o2.getPrice();
            }
        });

        for(HotelBase base: data) {
            logger.info("{}", base);
        }
    }

    @org.junit.Test
    public void testJsonToMap() {

       /* String json ="{\"detail\":{\"checkIn\":\"2014-09-28\",\"checkOut\":\"2014-09-30\",\"hotelSeq\":\"beijing_city_22744\",\"priceInfo\":[{\"price\":24,\"wrapperId\":\"wictrip0000\"},{\"price\":139,\"wrapperId\":\"wiqunarqta2\"}],\"roomCategory\":\"豪华标准房\",\"roomIds\":[\"1111\",\"22222\"],\"type\":2,\"serialNumber\":\"CH01408777\",\"hotelName\":\"去呼呼北京鸟巢爱特家酒店公寓\",\"userId\":\"zhongjie.wang\"},\"kpName\":\"撒旦法\",\"hotel\":\"去呼呼北京鸟巢爱特家酒店公寓\",\"customerPhone\":\"--400-890-2060\",\"type\":13,\"serialNum\":\"CH01408777\",\"kpPhone\":\"13811111111\"}";

        Map<String,String> detailMap = buildMap(json);
        logger.info("dddddddddddddd: {}",detailMap.get("hotelSeq"));*/

        Map<String,Object> content = Maps.newHashMap();
        content.put("name","jim");
        logger.info("============ name: {}, {}", (String)content.get("name"), (String)content.get("sex"));

        if("null".equals((String)content.get("sex"))){
            logger.info("----------------------");
        }

    }

    public Map<String,String> buildMap(String json){
        if (StringUtils.isNotBlank(json)) {
           return JsonUtils.decode(json, "detail", Map.class);
        }

        return Maps.newHashMap();
    }

    @org.junit.Test
    public void testdTime() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90));
        logger.info("" + date.getTime());
    }

    @org.junit.Test
    public void testMuiltMapSet() {
        HashMultimap<String, String> map = HashMultimap.create();
        map.put("1", "a");
        map.put("2", "b");
        map.put("1", "c");
        map.put("1", "a");

        Map<String, Collection<String>> collectionMap = map.asMap();
        for (Map.Entry<String, Collection<String>> entry : collectionMap.entrySet()) {
            logger.info("{} -> {}", entry.getKey(), entry.getValue());
        }
    }

    @org.junit.Test
    public void testBigDecial() {
        // 返现后价格
        BigDecimal price = new BigDecimal("-0.13");
        // 返现价格
        BigDecimal oprice = new BigDecimal("0.23");

        logger.info("result: {}", price.doubleValue()+"");
        logger.info("result: {}", price.add(oprice));
        logger.info("result: {}", price.compareTo(oprice));
    }

    @org.junit.Test
    public void testDDate() {
        Date beginDate = DateUtils.truncate(DateUtils.addDays(new Date(), -2), Calendar.DAY_OF_MONTH);
        Date endDate = DateUtils.addDays(beginDate, 1);
        logger.info("beginDate: {}", FastDateFormat.getInstance("yyyy-MM-dd").format(beginDate));
        logger.info("endDate: {}", FastDateFormat.getInstance("yyyy-MM-dd").format(endDate));
    }

    @org.junit.Test
    public void testmm() {

        boolean flag = CharMatcher.DIGIT.matchesAllOf("1132374");
        logger.debug("flag: {}", flag);

        String productId = Math.abs(Integer.parseInt("1132374")) + "";

        logger.debug("productId: {}", productId);

        Set<Integer> PS_PRODUCTTYPE_SET = ImmutableSet.of(3, 4);
        logger.debug("flag: {}", PS_PRODUCTTYPE_SET.contains(3));

        logger.debug(MessageFormat.format("解析消息失败, message({0}), 详情({1})", "ceshi", PS_PRODUCTTYPE_SET));
    }

    @org.junit.Test
    public void testCC() {
        for (int i = 0; i < 10; i++) {
            String str = i + "##";
            if (i % 2 == 0) {
                str = "";
            }
            print(str);
        }

        Person p = new Person(20, 1);
        logger.debug("person: {}", p);

        class Person {
            private int age;
            private int sex;

            Person(int age, int sex) {
                this.age = age;
                this.sex = sex;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String toString() {
                return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
            }
        }
    }

    @org.junit.Test
    public void testcDate() {
        FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");

        Date curDate = DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY);

        logger.debug(format.format(curDate));

        Date lastDate = curDate;

        lastDate = DateUtils.addDays(curDate, -2);

        logger.debug(format.format(curDate));
        logger.debug(format.format(lastDate));
    }

    @org.junit.Test
    public void testCache() {
        Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MILLISECONDS).build();

        for (int i = 0; i < 10; i++) {
            try {
                String value = cache.get("11", new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        logger.info("--------------------");
                        return "4444444";
                    }
                });

                logger.info("value: {}", value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    @org.junit.Test
    public void testStringToList() {
        String serialNumbers = "0122,21012,312021";
        List<String> serialNumberList = Lists.newArrayList(StringUtils.split(serialNumbers, ','));
        logger.debug("list: {}", JsonUtil.encode(serialNumberList));

        StringBuilder sb = new StringBuilder(String.valueOf(116.391495)).append(",").append(39.929442);
        logger.debug(sb.toString());

    }

    @org.junit.Test
    public void testTimestamp() {
        Timestamp operateTime = Timestamp.valueOf("0000-00-00 00:00:00");
        logger.debug("operateTime: {}", operateTime.after(new Date()));
    }

    @org.junit.Test
    public void testDoubleCompare() {
        double a = 20.1235;
        double b = 30.2315;
        logger.debug("f: {}", a != b);
    }

    @org.junit.Test
    public void testSplit() {
        final String numberList = "One,Two,Three,Four,Five,Six,Seven,Eight,Nine,Ten";

        int count = 10;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            StringUtils.split(numberList, ',');
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Splitter.on(',').split(numberList);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @org.junit.Test
    public void testMessage() {
        logger.debug(MessageFormat.format("{0},{1}", "jim", ""));
        logger.debug(MessageFormat.format("参数有误, ctSerial: {0}, customerSerial: {1}", "1001", "1225"));
        logger.debug(MessageFormat.format("合同({0})迁移到商户({1})下面", "10251", "10251026"));
        logger.debug(MessageFormat.format("任务内容构建器配置有问题, dutyTypeName:{0}","333"));
    }

    @org.junit.Test
    public void testMultiKeyMap() {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap.put("a1", "a2", "00");
        multiKeyMap.put("b1", "b2", "01");
        logger.debug(multiKeyMap.get("a1") + "");
    }

    @org.junit.Test
    public void testStringType(){
        String str1 = "";
        String str2 = null;

        logger.info("result1: {}",str1 instanceof String);
        logger.info("result2: {}",str2 instanceof Object);
    }

    @org.junit.Test
    public void testTT() {
        logger.debug("" + testTime());
    }

    @org.junit.Test
    public void testDateTruncate() {
        logger.debug("" + DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY));
        logger.debug("" + DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        logger.debug("" + DateUtils.truncate(new Date(), Calendar.MINUTE));
    }

    @org.junit.Test
    public void testStrings() {
        String hotelSeq = "beijing_city_20011";
        int index = StringUtils.lastIndexOf(hotelSeq, "_");
        logger.debug(index + "");
        String cityCode = StringUtils.substring(hotelSeq, 0, index);

        String seqId = StringUtils.substring(hotelSeq, index + 1);

        String url = "http://hotel.qunar.com/city/{0}/dt-{1}/";

        url = MessageFormat.format(url, cityCode, seqId);

        logger.debug(url);
    }

    @org.junit.Test
    public void testString() {
        String ctId = "CTD00240101010101";

        logger.debug(StringUtils.substring(ctId, 0, 6));

        logger.debug("" + StringUtils.startsWith(ctId, "CTD"));

        logger.debug(MessageFormat.format("ctserial({0}) is illegal", ctId));

    }

    @org.junit.Test
    public void testRanw(){
        Random rand = new Random();
        for(int i=0; i<10; i++){

            logger.info("random: {}", rand.nextInt(20));
        }
    }

    @org.junit.Test
    public void testIntern() {
        // intern方法 内存分配在持久带常量池分配，不足触发fullgc回收
        String a = new String("abc").intern();
        String b = new String("abc").intern();
        logger.debug("flag: {}", a == b);

        // new 对象 内存在堆中分配，不足触发yanggc回收
        String c = new String("abc");
        String d = new String("abc");
        logger.debug("flag: {}", c == d);

        String e = "abc";
        String f = "abc";
        logger.debug("flag: {}", e == f);
        logger.debug("flag: {}", e == a);

        int count = 1000000;
        for (int i = 0; i < count; i++) {
            new String(RandomStringUtils.randomAlphabetic(1000)).intern();
        }
    }

    @org.junit.Test
    public void ceshiCpuCache() {
        StopWatch watch = new StopWatch();
        int[] arr = new int[64 * 1024 * 1024];
        int k = 5;

        watch.start("k=5");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 10;
        watch.start("k=10");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 16;
        watch.start("k=16");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 17;
        watch.start("k=17");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 32;
        watch.start("k=32");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 64;
        watch.start("k=64");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        k = 90;
        watch.start("k=90");
        for (int i = 0; i < arr.length; i += k)
            arr[i] *= 3;
        watch.stop();

        logger.debug(watch.prettyPrint());
    }

    @org.junit.Test
    public void format(){
        logger.info(String.format("你好: s%, s%", "jim","tom"));
    }

    @org.junit.Test
    public void ceshiListRandom() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(list);
            logger.debug(JsonUtils.encode(list));
        }
    }

    @org.junit.Test
    public void decimal() {
        String grossProfit = Double.valueOf("0.03") * 100 + "%";
        logger.debug("price: {}", new BigDecimal("0.1"));
        logger.info("grossprofit: {}", grossProfit);
    }

    @org.junit.Test
    public void testLocalCache() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 3; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        logger.debug("counter: {}", getCounter("counter"));
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
                }
            });
        }

        try {
            TimeUnit.SECONDS.sleep(1000000);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    @org.junit.Test
    public void testStr() {
        String signKey = "D1EEFEB897C4DD754CB89275C33BA044";

        logger.debug(StringUtils.overlay(signKey, "*********", 10, 11));

        logger.debug("signkey: {}", warpSecrecyMsg(signKey, '*'));

        long begin = System.currentTimeMillis();
        int length = signKey.length();
        int partStart = length / 3;
        int partEnd = partStart * 2;

        logger.debug("{}", signKey.length());
        logger.debug(" {}, {}", partStart, partEnd);

        StringBuilder buf = new StringBuilder(length);
        buf.append(StringUtils.substring(signKey, 0, partStart));
        buf.append(StringUtils.repeat('*', partStart));
        buf.append(StringUtils.substring(signKey, partEnd));
        logger.debug("{},{}", buf.length(), buf.toString());

        logger.debug("time: {}", System.currentTimeMillis() - begin);

        long start = System.currentTimeMillis();
        String[] search = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        String[] place = new String[] { "*", "*", "*", "*", "*", "*", "*", "*", "*", "*" };
        String warpSignKey = StringUtils.replaceEach(signKey, search, place);
        logger.debug("signkey: {}, time: {}", warpSignKey, System.currentTimeMillis() - start);

        String[] signs = signKey.split("");
        logger.debug(JsonUtils.encode(signs));

        long start2 = System.currentTimeMillis();
        String warpSignKey2 = StringUtils.replacePattern(signKey, "\\d", "*");
        logger.debug("signkey2: {}, time: {}", warpSignKey2, System.currentTimeMillis() - start2);

    }

    @org.junit.Test
    public void testListPart() {
        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5, 5, 6, 7, 8, 9);
        List<List<Integer>> lists = Lists.partition(integers, 3);
        logger.debug(JsonUtils.encode(lists));
    }

    @org.junit.Test
    public void testGc() {
        sleep(10);
        List caches = new ArrayList();
        for (int i = 0; i < 10; i++) {
            sleep(2);
            caches.add(new byte[1024 * 1024 * 3]);
        }
        caches.clear();
        for (int i = 0; i < 2; i++) {
            sleep(2);
            caches.add(new byte[1024 * 1024 * 3]);
        }
    }

    @org.junit.Test
    public void testInterablsesfilter() {
        logger.debug("flag1: {}", this.testBoolean(14));
        logger.debug("flag1: {}", this.testBoolean(15));
        logger.debug("flag1: {}", this.testBoolean(16));
    }

    @org.junit.Test
    public void testInterables() {
        List<Integer> lists = Lists.newArrayList(1, 2, null, 3, 4, 8);
        Iterable<Integer> iterable = Iterables.filter(lists, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input != null;
            }
        });

        logger.debug(lists.size() + "|" + JsonUtils.encode(Lists.newArrayList(iterable)));
    }

    @org.junit.Test
    public void testCurrentHashMap() {
        ConcurrentMap<String, Integer> systemItems = new ConcurrentHashMap<String, Integer>();
        systemItems.get(null);
    }

    @org.junit.Test
    public void getDateTimestamp() throws Exception {
        long lastSyncTime = DateUtils.parseDate("2014-05-01 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
        logger.debug("lasttime: {}", lastSyncTime);

    }

    @org.junit.Test
    public void testenum() {
        logger.debug(FeeType.ALL_CHARGE.name());
    }

    @org.junit.Test
    public void testOptional() {
        String id = null;
        logger.debug(Optional.fromNullable(id).or("HELLO"));
    }

    @org.junit.Test
    public void testBitSet() {
        int ADDRESS_BITS_PER_WORD = 6;
        int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
        logger.debug("BITS_PER_WORD: {}", BITS_PER_WORD);

        BitSet bm = new BitSet();
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(0);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(1);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        System.out.println(bm.get(65));
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(127);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(1209875983);
        System.out.println(bm.isEmpty() + "--" + bm.size());
    }

    @org.junit.Test
    public void testJs() throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByExtension("js");
        logger.debug("flag: {}", FLAG);

        logger.debug("flag: {}", FLAG);

        engine.put("age", 26);// 赋值脚本环境中所使用的变量
        engine.eval("if(age>=18){println('Old enough to vote!');}else{println('Back to school!');}");// 解析 JavaScript
                                                                                                     // 脚本,对脚本表达式进行求值
    }

    @org.junit.Test
    public void testMap() {
        Map<Integer, RoomType> maps = Maps.newHashMap();
        RoomType room1 = new RoomType("11", "ss");
        maps.put(1, room1);
        logger.debug(JsonUtils.encode(maps.get(1)));
        logger.debug(JsonUtils.encode(maps.get(2)));
    }

    @org.junit.Test
    public void testCurTime() {
        for (int i = 0; i < 10; i++) {
            logger.debug(System.currentTimeMillis() + "");
        }
    }

    @org.junit.Test
    public void testBitComputer() {
        logger.debug(Integer.toBinaryString(120));
        logger.debug(Integer.toBinaryString(48));

        int hours = 43, minutes = 25, seconds = 31;
        int bits = 0;
        logger.debug("bits: {}", Integer.toBinaryString(bits));
        bits |= hours;
        logger.debug("bits: {}", Integer.toBinaryString(bits));
        bits <<= 6;
        logger.debug("bits: {}", Integer.toBinaryString(bits));
        bits |= minutes;
        logger.debug("bits: {}", Integer.toBinaryString(bits));
        bits <<= 6;
        logger.debug("bits: {}", Integer.toBinaryString(bits));
        bits |= seconds;
        logger.debug("bits: {}", Integer.toBinaryString(bits));

        logger.debug("bits: {},hour: {},minute: {},second: {}", new Object[] { bits, (bits >> 12) & 0x3F,
                (bits >> 6) & 0x3F, (bits >> 0) & 0x3F });
        logger.debug(Integer.toBinaryString((bits >> 12)));
        logger.debug(Integer.toBinaryString((bits >> 12) & 0x3F));
        logger.debug(Integer.toBinaryString((bits >> 6)));
        logger.debug(Integer.toBinaryString((bits >> 6) & 0x3F));
        logger.debug(Integer.toBinaryString((bits >> 0)));
        logger.debug(Integer.toBinaryString((bits >> 0) & 0x3F));

        logger.debug("-----------------------------------------------");
        long WORD_MASK = 0xffffffffffffffffL;
        logger.debug(Long.toBinaryString(WORD_MASK).length() + "");
        logger.debug(Integer.toBinaryString(63));
    }

    @org.junit.Test
    public void testEnum() {
        if (FeeType.valueOf("ALL_FREE") == null) {
            logger.debug("null");
        }
        logger.debug(FeeType.valueOf("ALL_FREE").name());
    }

    @org.junit.Test
    public void joiner() {
        String line = Joiner.on(",").join("1", "3", 4, 5, "9");
        logger.debug("line: {}", line);
        line = Joiner.on(",").join("1", "3", 4, 5, StringUtils.defaultString("p", "x"));
        logger.debug("line: {}", line);
    }

    @org.junit.Test
    public void map() {
        Map<Integer, Integer> map = Maps.newHashMap();
        map.put(1, 1);
        map.put(2, 2);
        map.put(5, 5);
        map.put(4, 4);
        map.put(3, 3);
        map.put(305, 305);
        map.put(301, 301);

        logger.debug("map: " + map.toString());
        logger.debug("list: " + map.values());

        List<Integer> result = Ordering.from(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }).sortedCopy(map.values());

        logger.debug("result: " + result.toString());
    }

    @org.junit.Test
    public void listFilter() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(123);

        // List-->Set
        Set<Integer> listSet = new HashSet<Integer>(list);

        Iterable<Integer> iterable = Iterables.filter(list, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input >= 3;
            }
        });

        List<Integer> filterList = Lists.newArrayList(iterable);

        logger.debug(filterList.toString());
    }

    @org.junit.Test
    public void listToMap() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(123);

        // List-->Set
        Set<Integer> listSet = new HashSet<Integer>(list);
        listSet.add(2);

        for (Integer key : list) {
            logger.debug("exist: " + listSet.contains(key));
        }

        logger.debug("set: " + listSet.toString());
    }

    @org.junit.Test
    public void longtime() {
        long time = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(-2);
        Timestamp timestamp = new Timestamp(time);
        logger.debug("timestamp: " + timestamp.toString());

        Date lastDate = DateUtils.addDays(new Date(), -2);
        lastDate = DateUtils.truncate(lastDate, Calendar.DATE);

        Timestamp lastTime = new Timestamp(lastDate.getTime());
        logger.debug("lasttime: " + lastTime);
    }

    @org.junit.Test
    public void integer() {
        Integer t = new Integer(20);
        logger.debug(t.toString());
    }

    @org.junit.Test
    public void logerr() {
        try {
            throw new IllegalArgumentException("测试");
        } catch (Exception e) {
            logger.error("错误, name: {}", "jim", e);
        }
    }

    @org.junit.Test
    public void bigDemical() {
        BigDecimal decimala = new BigDecimal("-2");
        BigDecimal decimalb = new BigDecimal("99999999");

        logger.debug("decimala: {}", decimala);
        logger.debug("decimalb: {}", decimalb);

        BigDecimal decimal = new BigDecimal(2);
        BigDecimal decimal2 = new BigDecimal(10);
        logger.debug("decimal: {}", decimal.doubleValue());

        int result = decimal2.compareTo(decimal);
        logger.debug(result + "");

        BigDecimal min = decimal;
        min = new BigDecimal(5);

        logger.debug("min: {}", min.doubleValue());
        logger.debug("decimal: {}", decimal.doubleValue());
    }

    @org.junit.Test
    public void testDate() {
        logger.debug("hello world!");

        Date date = new Date();

        logger.debug("date: {}", DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));

        logger.debug("date: {}, d: {}, f: {}", FastDateFormat.getInstance().format(date), 1);

    }

    @org.junit.Test
    public void md5() {
        String str = "dd";
        String md5 = org.apache.commons.codec.digest.DigestUtils.md2Hex(str);
        logger.debug("md5: {}", md5);
    }

    @org.junit.Test
    public void minuteToSecond() {
        logger.debug("10分钟: {}", TimeUnit.MINUTES.toSeconds(10));
    }

    @org.junit.Test
    public void split() {
        String splitStr = "jim,tom,lilei,lili,sunjian,tomc";
        int counter = 10000;
        StopWatch watch = new StopWatch("split");

        watch.start("String.split");

        for (int i = 0; i < counter; i++) {
            String[] arr = splitStr.split(",");
        }

        watch.stop();
        logger.debug(watch.getLastTaskTimeMillis() + "");

        watch.start("StringUtils.split");

        for (int i = 0; i < counter; i++) {
            String[] arr = StringUtils.split(splitStr, ",");
        }

        watch.stop();
        logger.debug(watch.getLastTaskTimeMillis() + "");

        logger.debug(watch.prettyPrint());
    }

    @org.junit.Test
    public void praOver() {
        int counter = 100;
        for (int i = 0; i < counter; i++) {
            praAndSeq();
        }
    }

    @org.junit.Test
    public void praAndSeq() {
        StopWatch watch = new StopWatch("并行/顺序执行任务对比");

        // 并发执行
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadPoolExecutor.DiscardOldestPolicy());

        CompletionService<Integer> service = new ExecutorCompletionService<Integer>(executor);

        watch.start("并行执行任务");

        int sumResult = 0;
        int taskCounter = 100;
        for (int i = 0; i < taskCounter; i++) {
            SumTask task = new SumTask(Lists.newArrayList(1, 2, 5, 6));
            service.submit(task);
        }

        for (int i = 0; i < taskCounter; i++) {
            try {
                Future<Integer> result = service.take();
                int sum = result.get();
                sumResult += sum;
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        logger.debug("并行执行计算结果: {}", sumResult);

        watch.stop();

        /*
         * watch.start("顺序执行任务"); sumResult = 0; for (int i = 0; i < taskCounter; i++) { try { int sum =
         * Test.computerListSize(Lists.newArrayList(1, 2, 5, 6)); sumResult += sum; } catch (Exception e) {
         * logger.error("", e); } } watch.stop(); logger.debug("顺序执行计算结果: {}", sumResult);
         */

        logger.debug(watch.prettyPrint());

    }

    @org.junit.Test
    public void dateTrunc() {
        Date fromDate = new Date();
        Date toDate = DateUtils.addDays(fromDate, 1);

        logger.debug(DateUtils.truncate(fromDate, Calendar.DATE).toString());
        logger.debug(DateUtils.truncate(toDate, Calendar.DATE).toString());
    }

    @org.junit.Test
    public void join() {
        int hdsSeq = 1021;
        String romeType = "豪华大双房";

        String key = Joiner.on(".").join(hdsSeq, romeType);
        logger.debug("key: {}", key);

    }

    @org.junit.Test
    public void spliterToList() {
        String line = "aabenraa_municipality_1\twapbooking0\tBooking.com旗舰店\t双人或双床间 - 单人用(含早)(免费宽带)";
        List<String> list = Splitter.on("\t").splitToList(line);

        logger.debug("list.size: {}, list.content: {}", list.size(), list.toString());

        Iterable<String> params = Splitter.on("\t").split(line);
        for (String param : params) {
            logger.debug("param: {}", param);
        }
        String[] paramArr = StringUtils.split(line, "\t");
        for (int i = 0; i < paramArr.length; i++) {
            logger.debug("param: {}", paramArr[i]);
        }
    }

    @org.junit.Test
    public void random() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(10);
            logger.debug("index: {}", index);
        }
    }

    @org.junit.Test
    public void mapTest() {
        Map<String, String> source = Maps.newHashMap();
        Map<String, Object> result = Maps.transformValues(source, new Function<String, Object>() {
            @Override
            public Object apply(String input) {
                return null;
            }
        });

        logger.debug("map.size: {}", result.size());
    }

    @org.junit.Test
    public void urlEncode() throws Exception {
        String name = "李红";
        logger.debug("result: {}", URLEncoder.encode(name, "utf-8"));

    }

    @org.junit.Test
    public void testLogger() {
        logger.warn("测试, hds:{},id:{},seq:{}", new Object[]{1, 2, 3});
    }

    @org.junit.Test
    public void testtime() {
        System.out.println(System.currentTimeMillis());
    }

    private static class SumTask implements Callable<Integer> {
        private List<Integer> element;

        public SumTask(List<Integer> element) {
            this.element = element;
        }

        @Override
        public Integer call() throws Exception {
            return Test.computerListSize(this.element);
        }
    }

    @org.junit.Test
    public void listSort() {
        RoomType a = new RoomType("-1", "jim");
        RoomType b = new RoomType("101", "jim");
        RoomType c = new RoomType("123565", "jim");
        RoomType d = new RoomType("45492227560000", "tom");

        List<RoomType> list = Lists.newArrayList(b, c, a, d);

        logger.info(list.toString());

        Collections.sort(list, new Comparator<RoomType>() {
            @Override
            public int compare(RoomType o1, RoomType o2) {
                if (o1 != null && o2 != null) {
                    return new BigInteger(o2.getRoomTypeId()).compareTo(new BigInteger(o1.getRoomTypeId()));
                } else if (o1 != null && o2 == null) {
                    return -1;
                } else if (o1 == null && o2 != null) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        logger.info(list.toString());
    }

    @org.junit.Test
    public void nullTest() {
        RoomType roomType = null;
        RoomType roomType1 = null;
        if (roomType == NULL) {
            logger.debug("roomType is null");
        } else {
            logger.debug("roomType is not null");
        }
        if (roomType1 == NULL) {
            logger.debug("roomType1 is null");
        } else {
            logger.debug("roomType1 is not null");
        }
    }

    @org.junit.Test
    public void transList() {
        List<Integer> list = Lists.newArrayList(1, 2);
        List<String> resultList = Lists.transform(list, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return String.valueOf(input);
            }
        });

        logger.debug(JsonUtils.encode(resultList));
    }

    @org.junit.Test
    public void testJson() {
        RoomType toom = null;
        logger.debug("json: {}", JsonUtils.encode(toom));
    }

    public static class RoomType {
        private String roomTypeId;
        private String roomTypeName;

        public RoomType() {
        }

        public RoomType(String roomTypeId, String roomTypeName) {
            this.roomTypeId = roomTypeId;
            this.roomTypeName = roomTypeName;
        }

        public String getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(String roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public String getRoomTypeName() {
            return roomTypeName;
        }

        public void setRoomTypeName(String roomTypeName) {
            this.roomTypeName = roomTypeName;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    private static int computerListSize(List<Integer> element) throws Exception {
        TimeUnit.MILLISECONDS.sleep(20);
        return element.size();
    }

    @org.junit.Test
    public void testParallel() {
        long start = System.currentTimeMillis();

        Map<String, String> result = Maps.newHashMap();
        CountDownLatch latch = new CountDownLatch(2);
        ReentrantLock lock = new ReentrantLock();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(CralwTask.build(result, latch, lock, "name"));
        executor.execute(CralwTask.build(result, latch, lock, "sex"));
        try {
            latch.await(400, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("RESUlt: {}, time: {}", JsonUtil.encode(result), System.currentTimeMillis() - start);

        executor.shutdown();
    }

    private static class CralwTask implements Runnable {
        private Map<String, String> result;
        private CountDownLatch latch;
        private Lock lock;
        private String key;

        private CralwTask(Map<String, String> result, CountDownLatch latch, Lock lock, String key) {
            this.result = result;
            this.latch = latch;
            this.lock = lock;
            this.key = key;
        }

        public static CralwTask build(Map<String, String> result, CountDownLatch latch, Lock lock, String key) {
            return new CralwTask(result, latch, lock, key);
        }

        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(300);

                lock.lock();
                try {
                    result.put(key, String.valueOf(System.currentTimeMillis()));
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
    private static enum EditTaskStateType {
        EDIT_WAIT("项目待编辑", 2),
        DRAFT("项目编辑中", 3),
        VERIFY_WAIT("项目编辑完成，待认领", 4),
        VERIFY_IN_PROGRESS("页面待总部审核", 5),
        VERIFY_NOT_PASS("总部驳回，待编辑", 6),
        VERIFY_PASS("等待商家确认", 7),
        CUSTOMER_CONFIRM_NOT_PASS("商家驳回，待编辑", 8),
        CUSTOMER_CONFIRM_PASS("商家确认，团品待上架", 9),
        BOOK_ONLINE("预约上架", 11),// 暂无
        ONLINE("团品已上架", 12),
        OFFLINE("团品已下架", 13),
        ONLINE_UPDATE("已修改，团品待更新", 14),
        EDIT_LEADER_REJECT("编辑Leader驳回", 15);

        private String text;
        private int code;

        private EditTaskStateType(String text, int code) {
            this.text = text;
            this.code = code;
        }

   }
}
