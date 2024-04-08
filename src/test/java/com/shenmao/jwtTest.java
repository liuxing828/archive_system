package com.shenmao;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtTest {
    @Test
    public void testGen(){
        Map<String, Object> cliams = new HashMap<>();
        cliams.put("id", 1);
        cliams.put("username", "张三");
        String token = JWT.create()
                .withClaim("user", cliams)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*3600*12))//添加过期时间
                .sign(Algorithm.HMAC256("shenmao"));//指定算法，配置密钥
        System.out.println(token);
    }

    @Test
    public void testPause(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MTEzOTM5NzB9." +
                "B-Dd7nHcD_Xm4TgK5H0oyPFLuEFJMOs9DVgqAaisCfE";
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("shenmao")).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
