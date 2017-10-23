package es.config;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:datasource.properties")
public class RedisConfig {

    @Value("${redis.pool.maxActive}")
    private String maxActive;
    @Value("${redis.pool.maxIdle}")
    private String maxIdle;
    @Value("${redis.pool.maxWait}")
    private String maxWait;
    @Value("${redis.pool.minIdle}")
    private String minIdle;
    @Value("${redis.pool.testOnBorrow}")
    private String testOnBorrow;
    @Value("${redis.ip}")
    private String ip;
    @Value("${redis.port}")
    private String port;
    @Value("${redis.password}")
    private String password;

    @Value("${item.redis.pool.maxActive}")
    private String itemMaxActive;
    @Value("${item.redis.pool.maxIdle}")
    private String itemMaxIdle;
    @Value("${item.redis.pool.maxWait}")
    private String itemMaxWait;
    @Value("${item.redis.pool.minIdle}")
    private String itemMinIdle;
    @Value("${item.redis.pool.testOnBorrow}")
    private String itemTestOnBorrow;
    @Value("${item.redis.ip}")
    private String itemIp;
    @Value("${item.redis.port}")
    private String itemPort;
    @Value("${item.redis.password}")
    private String itemPassword;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(NumberUtils.toInt(maxActive));
        config.setMaxIdle(NumberUtils.toInt(maxIdle));
        config.setMinIdle(NumberUtils.toInt(minIdle));
        config.setMaxWaitMillis(NumberUtils.toLong(maxWait));
        config.setTestOnBorrow(BooleanUtils.toBoolean(testOnBorrow));
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(ip);
        jedisConnectionFactory.setPort(NumberUtils.toInt(port));
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setDatabase(0);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }

    // item
    @Bean
    public JedisPoolConfig itemJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(NumberUtils.toInt(itemMaxActive));
        config.setMaxIdle(NumberUtils.toInt(itemMaxIdle));
        config.setMinIdle(NumberUtils.toInt(itemMinIdle));
        config.setMaxWaitMillis(NumberUtils.toLong(itemMaxWait));
        config.setTestOnBorrow(BooleanUtils.toBoolean(itemTestOnBorrow));
        return config;
    }

    @Bean
    public JedisConnectionFactory itemJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(itemIp);
        jedisConnectionFactory.setPort(NumberUtils.toInt(itemPort));
        jedisConnectionFactory.setPoolConfig(itemJedisPoolConfig());
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPassword(itemPassword);
        jedisConnectionFactory.setDatabase(1);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate itemRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
        stringRedisTemplate.setConnectionFactory(itemJedisConnectionFactory());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return stringRedisTemplate;
    }

    // itemAttr
    @Bean
    public JedisConnectionFactory itemAttrVerifyJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(itemIp);
        jedisConnectionFactory.setPort(NumberUtils.toInt(itemPort));
        jedisConnectionFactory.setPoolConfig(itemJedisPoolConfig());
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPassword(itemPassword);
        jedisConnectionFactory.setDatabase(2);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate itemAttrVerifyRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
        stringRedisTemplate.setConnectionFactory(itemAttrVerifyJedisConnectionFactory());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return stringRedisTemplate;
    }

    // itemCode
    @Bean
    public JedisConnectionFactory itemCodeVerifyJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(itemIp);
        jedisConnectionFactory.setPort(NumberUtils.toInt(itemPort));
        jedisConnectionFactory.setPoolConfig(itemJedisPoolConfig());
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPassword(itemPassword);
        jedisConnectionFactory.setDatabase(3);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate itemCodeAttrVerifyRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
        stringRedisTemplate.setConnectionFactory(itemAttrVerifyJedisConnectionFactory());
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return stringRedisTemplate;
    }

    // session
    @Bean
    public JedisPoolConfig sessionJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(NumberUtils.toInt(maxActive));
        config.setMaxIdle(NumberUtils.toInt(maxIdle));
        config.setMinIdle(NumberUtils.toInt(minIdle));
        config.setMaxWaitMillis(NumberUtils.toLong(maxWait));
        config.setTestOnBorrow(BooleanUtils.toBoolean(testOnBorrow));
        return config;
    }

    @Bean
    @Primary
    public JedisConnectionFactory sessionJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(ip);
        jedisConnectionFactory.setPort(NumberUtils.toInt(port));
        jedisConnectionFactory.setPoolConfig(sessionJedisPoolConfig());
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setDatabase(15);
        return jedisConnectionFactory;
    }
}
