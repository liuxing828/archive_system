package com.shenmao.archive_management_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;


@SpringBootTest
public class redisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username", "shenmao");
        operations.set("id", "2", 10, TimeUnit.SECONDS);
    }
}
