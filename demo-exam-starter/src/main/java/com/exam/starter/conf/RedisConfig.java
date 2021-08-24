package com.exam.starter.conf;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 配置redis
 * @author liyang
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(
        prefix = "spring",
        name = {"redis"},
        matchIfMissing = true
)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;
    @Value("${spring.redis.lettuce.pool.test-on-borrow:true}")
    private boolean testOnBorrow;
    @Value("${spring.redis.lettuce.pool.test-on-return:false}")
    private boolean testOnReturn;
    @Value("${spring.redis.lettuce.pool.test-while-idle:true}")
    private boolean testWhileIdle;
    @Value("${spring.redis.lettuce.pool.min-evictable-idle-time-mills:60000}")
    private long minEvictableIdleTimeMills;
    @Value("${spring.redis.lettuce.pool.time-between-eviction-runs-millis:30000}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${spring.redis.lettuce.pool.num-tests-per-eviction-run:-1}")
    private int numTestsPerEvictionRun;
    @Value("${spring.redis.shutdown-time-out:100}")
    private int shutdownTimeOut;
    private Duration timeOut = Duration.ofMillis(2500L);
    private LettuceConnectionFactory factory;

    public RedisConfig() {
    }

    private LettuceConnectionFactory getFactory() {
        if (this.factory == null) {
            this.factory = this.createLettuceConnectionFactory();
        }

        return this.factory;
    }

    private RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(this.redisProperties.getHost());
        config.setPort(this.redisProperties.getPort());
        if (this.redisProperties.getPassword() != null) {
            config.setPassword(this.redisProperties.getPassword());
        }

        config.setDatabase(this.redisProperties.getDatabase());
        return config;
    }

    private RedisSentinelConfiguration getSentinelConfig() {
        RedisProperties.Sentinel sentinel = this.redisProperties.getSentinel();
        if (sentinel == null) {
            return new RedisSentinelConfiguration();
        } else {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.setMaster(sentinel.getMaster());
            Set<RedisNode> sentinels = new HashSet();
            Iterator var4 = sentinel.getNodes().iterator();

            while(var4.hasNext()) {
                String redisHost = (String)var4.next();
                String[] item = redisHost.split(":");
                String ip = item[0].trim();
                String port = item[1].trim();
                sentinels.add(new RedisNode(ip, Integer.parseInt(port)));
            }

            config.setSentinels(sentinels);
            return config;
        }
    }

    private RedisClusterConfiguration getClusterConfig() {
        RedisProperties.Cluster cluster = this.redisProperties.getCluster();
        if (cluster != null) {
            RedisClusterConfiguration config = new RedisClusterConfiguration(cluster.getNodes());
            if (cluster.getMaxRedirects() != null) {
                config.setMaxRedirects(cluster.getMaxRedirects());
            }

            return config;
        } else {
            return new RedisClusterConfiguration();
        }
    }

    private RedisProperties.Pool getPoolProperties() {
        RedisProperties.Pool pool = null;
        if (this.redisProperties.getLettuce() != null && this.redisProperties.getLettuce().getPool() != null) {
            pool = this.redisProperties.getLettuce().getPool();
        }

        return pool;
    }

    private GenericObjectPoolConfig getPoolConfig() {
        RedisProperties.Pool properties = this.getPoolProperties();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        if (properties != null) {
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }
        }

        config.setTestOnBorrow(this.testOnBorrow);
        config.setTestWhileIdle(this.testWhileIdle);
        config.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMills);
        config.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        config.setNumTestsPerEvictionRun(this.numTestsPerEvictionRun);
        return config;
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder() {
        RedisProperties.Pool pool = this.getPoolProperties();
        if (pool == null) {
            return LettuceClientConfiguration.builder();
        } else {
            Duration timeout = this.redisProperties.getTimeout();
            if (timeout == null) {
                timeout = this.timeOut;
            }

            return LettucePoolingClientConfiguration.builder().poolConfig(this.getPoolConfig()).commandTimeout(timeout).shutdownTimeout(Duration.ofMillis((long)this.shutdownTimeOut));
        }
    }

    private LettuceConnectionFactory createLettuceConnectionFactory() {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder;
        LettuceClientConfiguration lettuceClientConfiguration;
        LettuceConnectionFactory lettuceConnectionFactory;
        if (this.redisProperties.getSentinel() != null) {
            RedisSentinelConfiguration redisConfiguration = this.getSentinelConfig();
            builder = this.createBuilder();
            lettuceClientConfiguration = builder.build();
            lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            return lettuceConnectionFactory;
        } else if (this.redisProperties.getCluster() != null) {
            RedisClusterConfiguration redisConfiguration = this.getClusterConfig();
            builder = this.createBuilder();
            lettuceClientConfiguration = builder.build();
            lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            return lettuceConnectionFactory;
        } else {
            RedisStandaloneConfiguration redisConfiguration = this.getStandaloneConfig();
            builder = this.createBuilder();
            lettuceClientConfiguration = builder.build();
            lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            return lettuceConnectionFactory;
        }
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                Object[] var5 = params;
                int var6 = params.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Object obj = var5[var7];
                    sb.append(obj.toString());
                }

                return sb.toString();
            }
        };
    }

    @Bean
    public CacheManager cacheManager() {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = this.getOm();
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24L));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheManager cacheManager = RedisCacheManager.builder(this.getFactory()).cacheDefaults(redisCacheConfiguration).build();
        return cacheManager;
    }

    @Bean(
            name = {"redisTemplate"}
    )
    public RedisTemplate<String, Object> redisTemplate() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = this.getOm();
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(this.getFactory());
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private ObjectMapper getOm() {
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(df));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(df));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }

}
