package com.lxz.ceshi.jwt;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.google.common.collect.Maps;
import com.lxz.ceshi.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * Created by xiaolezheng on 16/5/27.
 */
@Slf4j
public class JWTVerifierTest {
    private static final String secret = "s234343e";
    private JWTVerifier jwtVerifier = new JWTVerifier(secret);
    private JWTSigner jwtSigner = new JWTSigner(secret);

    @Test
    public void testVerifier() throws Exception{
        Map<String,Object> params = Maps.newHashMap();
        params.put("name","zxl");
        params.put("uid",10033l);
        params.put("lastTime", System.currentTimeMillis());

        String sign =jwtSigner.sign(params);

        log.info("sign: {}",sign);

        Map<String,Object> source = jwtVerifier.verify(sign);

        log.info("source: {}", JsonUtils.encode(source));
    }
}
