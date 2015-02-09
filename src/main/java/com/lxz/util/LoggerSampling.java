/*
 * Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
package com.lxz.util;

import java.util.Calendar;

import org.slf4j.Logger;

import com.google.common.primitives.Ints;

/**
 * 采样打印日志 (支持分钟,小时)
 * 
 * @author: xiaole Date: 15-1-28 Time: 下午6:11
 * @version: \$Id$
 */
public final class LoggerSampling {
    /**
     * 当前时间分钟值采样池
     */
    private static final int[] minutePool = new int[] { 5, 15, 25, 35, 45, 55 };

    /**
     * 当前时间小时采样池
     */
    private static final int[] hourPool = new int[] { 10, 13, 15, 17, 21, 23 };

    /**
     * 日志打印级别
     */
    private enum Level {
        INFO, WARN
    }

    private LoggerSampling() {
    }

    /**
     * 按分钟打印info级别采样日志
     * 
     * @param logger
     * @param content
     */
    public static void infoOfMinuteSampling(Logger logger, String content) {
        logOfMinuteSampling(logger, content, Level.INFO);
    }

    /**
     * 按分钟打印warn级别采样日志
     * 
     * @param logger
     * @param content
     */
    public static void warnOfMinuteSampling(Logger logger, String content) {
        logOfMinuteSampling(logger, content, Level.WARN);
    }

    /**
     * 按小时打印info级别采样日志
     * 
     * @param logger
     * @param content
     */
    public static void infoOfHourSampling(Logger logger, String content) {
        logOfHourSampling(logger, content, Level.INFO);
    }

    /**
     * 按小时打印info级别采样日志
     * 
     * @param logger
     * @param content
     */
    public static void warnOfHourSampling(Logger logger, String content) {
        logOfHourSampling(logger, content, Level.WARN);
    }

    private static void logOfHourSampling(Logger logger, String content, Level level) {
        int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (Ints.contains(hourPool, curHour)) {
            doLog(logger, content, level);
        }
    }

    private static void logOfMinuteSampling(Logger logger, String content, Level level) {
        int curMinute = Calendar.getInstance().get(Calendar.MINUTE);
        if (Ints.contains(minutePool, curMinute)) {
            doLog(logger, content, level);
        }
    }

    private static void doLog(Logger logger, String content, Level level) {
        switch (level) {
        case INFO:
            logger.info(content);
            break;

        case WARN:
            logger.warn(content);
            break;

        default:
            break;
        }
    }
}
