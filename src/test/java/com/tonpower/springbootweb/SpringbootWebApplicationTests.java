package com.tonpower.springbootweb;

import com.tonpower.springbootweb.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.util.Set;

@SpringBootTest
class SpringbootWebApplicationTests {
    @Test
    void contextLoads() {
    }
    @Test
    void timeTest(){
        Long time = 1624031708047L;
        System.out.println(new DateTime(time).toString("yyyy-MM-dd HH:mm"));
    }

    @Test
    void tokenTest(){
        String token = JwtUtils.createToken(100L);
        System.out.println(token);
        System.out.println(JwtUtils.checkToken(token));
    }

    @Test
    void md5Test(){
        String s = DigestUtils.md5Hex("admin" + "Li!@#$%");
        System.out.println(s);
    }
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void redisTest(){

    }

    @Autowired
    DataSource dataSource;
    @Test
    void daraSourceTest(){
        System.out.println(dataSource.getClass());
    }
}
