package com.lxz.ceshi.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PluginTest {

  @Test
  public void mapPluginShouldInterceptGet() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
    System.out.println(map.get("Anything"));
  }

  @Test
  public void shouldNotInterceptToString() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
    System.out.println(map);
  }

  @Intercepts({
      @Signature(type = Map.class, method = "get", args = {Object.class})})
  public static class AlwaysMapPlugin implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
      return "Always";
    }

    public Object plugin(Object target) {
      return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
  }

}