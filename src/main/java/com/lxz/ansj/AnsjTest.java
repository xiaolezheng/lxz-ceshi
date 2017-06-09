package com.lxz.ansj;

import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.testng.annotations.Test;

/**
 * Created by xiaolezheng on 17/6/9.
 */
@Slf4j
public class AnsjTest {
    @Test
    public void test1() throws Exception {
        Result result = BaseAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。");
        log.info("{}", result.getTerms());
    }

    @Test
    public void test2() throws Exception{
        Result result = ToAnalysis.parse("让战士们过一个欢乐祥和的新春佳节。");
        log.info("{}", result.getTerms());
    }

    @Test
    public void test3() throws Exception{
        Result result = IndexAnalysis.parse("主副食品");
        log.info("{}", result.getTerms());
    }
}
