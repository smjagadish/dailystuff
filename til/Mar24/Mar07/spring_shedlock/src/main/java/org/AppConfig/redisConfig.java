package org.AppConfig;

import lombok.Getter;
import lombok.Setter;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.jedis4.JedisLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class redisConfig {
    @Bean
    public JedisPool getPool()
    {
        return new JedisPool("localhost",10001);
    }
    //Redis based shed lock provider
    @Bean
    public LockProvider getLock(JedisPool pool)
    {
        return new JedisLockProvider(pool);
    }

}
