
package com.diandian.dubbo.common.redis;

import cn.hutool.core.util.StrUtil;
import com.diandian.dubbo.common.redis.lock.LockExecutor;
import com.diandian.dubbo.common.redis.lock.LockTemplate;
import com.diandian.dubbo.common.redis.lock.RedisTemplateLockExecutor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * Redis配置
 *
 * @author x
 * @date 2018/9/12 16:36
 */
@Configuration
@Conditional(RedisCondition.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //KEY 为String方式
        template.setKeySerializer(template.getStringSerializer());
        //VALUE 使用 jackson进行序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public LockExecutor lockExecutor(StringRedisTemplate redisTemplate) {
        RedisTemplateLockExecutor redisTemplateLockExecutor = new RedisTemplateLockExecutor();
        redisTemplateLockExecutor.setRedisTemplate(redisTemplate);
        return redisTemplateLockExecutor;
    }

    @Bean
    public LockTemplate lockTemplate(LockExecutor lockExecutor){
        LockTemplate lockTemplate = new LockTemplate();
        lockTemplate.setLockExecutor(lockExecutor);
        return lockTemplate;
    }

    @Bean
    public JedisPool jedisPool(JedisConnectionFactory factory) {
        return new JedisPool(factory.getPoolConfig(), factory.getHostName(), factory.getPort(), 3000,
                StrUtil.isBlank(factory.getPassword()) ? null : factory.getPassword(), factory.getDatabase());
    }

}
