package sg.edu.nus.iss.app.Workshop14.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;


import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class RedisConfig {
    private Logger logger = Logger.getLogger(RedisConfig.class.getName());

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    @Scope("singleton")
    public RedisTemplate<String,Object> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        logger.log(Level.INFO,"redisHost > " + redisHost + "\n" + "redisPort >" + redisPort);


        config.setPort(redisPort.get());
        config.setHostName(redisHost);
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisClient =
                JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac =
                new JedisConnectionFactory(config,jedisClient);
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        //key for the map
        template.setHashKeySerializer(new StringRedisSerializer());

        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(serializer);

        return template;
    }
}
