package com.moyu.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void addKey(String key, Object object) {
        redisTemplate.opsForValue().set(key, object, 360, TimeUnit.MINUTES);
    }

    public boolean add(final String keyName, final String keyValue) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyName);
                byte[] name = serializer.serialize(keyValue);
                connection.setEx(key, 1800, name);
                return true;
            }
        });
        return result;
    }

    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public void addCompanyKey(String key, Object object, Long time) {
        redisTemplate.opsForValue().set(key, object, time, TimeUnit.MILLISECONDS);
    }

    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

}
