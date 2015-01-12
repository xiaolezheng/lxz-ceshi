package com.lxz.spark;

import spark.ResponseTransformer;

import com.lxz.util.JsonUtil;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) {
        return JsonUtil.encode(model);
    }

}
