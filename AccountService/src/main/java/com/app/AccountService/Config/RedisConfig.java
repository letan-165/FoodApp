    package com.app.AccountService.Config;

    import com.app.AccountService.Entity.Token;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.data.redis.connection.RedisConnectionFactory;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
    import org.springframework.data.redis.serializer.StringRedisSerializer;

    @Configuration
    public class RedisConfig {
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);

            // Sử dụng StringRedisSerializer cho key
            template.setKeySerializer(new StringRedisSerializer());

            // Sử dụng GenericJackson2JsonRedisSerializer cho value
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

            return template;
        }

    }
