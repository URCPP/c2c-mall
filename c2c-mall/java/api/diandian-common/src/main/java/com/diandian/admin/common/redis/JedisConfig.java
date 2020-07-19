package com.diandian.admin.common.redis;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zengzh
 * @date 2018/09/21
 */
@Slf4j
@Configuration
@Conditional(RedisCondition.class)
public class JedisConfig {

    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        JedisPool pool = new JedisPool(config, properties.getHost(), properties.getPort(), 3000,
                StrUtil.isBlank(properties.getPassword()) ? null : properties.getPassword(), properties.getDatabase());
        return pool;
    }
}
