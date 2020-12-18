package com.zlk.limit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author zc217
 * @Date 2020/12/18
 */
@SpringBootTest
class LimitInterceptorTest {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void testLimit(){
        RedisScript<Number> redisScript = RedisScript.of("local c\n" +
                "c = redis.call('get',KEYS[1])\n" +
                "if c and tonumber(c) > tonumber(ARGV[1]) then\n" +
                "return c;\n" +
                "end\n" +
                "c = redis.call('incr',KEYS[1])\n" +
                "if tonumber(c) == 1 then\n" +
                "redis.call('expire',KEYS[1],ARGV[2])\n" +
                "end\n" +
                "return c;",Number.class);
        List<String> list = new ArrayList<>();
        list.add("door3");
        Number result = redisTemplate.execute(redisScript,list,"60");
        System.out.println(result);
    }

}