package com.labourseSearching.Service.JobQuery;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisLuaJobAcceptanceService {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> script;

    public RedisLuaJobAcceptanceService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.script = new DefaultRedisScript<>();
        this.script.setResultType(Long.class);
        this.script.setScriptText(
                """
                local remaining = redis.call("HGET", KEYS[1], ARGV[1])
                if not remaining then return -1 end
                remaining = tonumber(remaining)
                if remaining <= 0 then return 0 end
                redis.call("HSET", KEYS[1], ARGV[1], remaining - 1)
                return remaining - 1
                """
        );
    }

    public long decrement(String key, String labourType) {
        return redisTemplate.execute(
                script,
                List.of(key),
                labourType
        );
    }
}