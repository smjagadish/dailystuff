package org.AppConfig;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.provider.redis.jedis4.JedisLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;

@Configuration
public class lockConfig {
    @Bean
    @Profile("redis")
    public JedisPool getPool()
    {
        return new JedisPool("localhost",10001);
    }
    //Redis based shed lock provider
    @Bean
    @Profile("redis")
    public LockProvider getLock(JedisPool pool)
    {
        return new JedisLockProvider(pool);
    }
    @Bean
    @Profile("pg")
    public LockProvider getPgLock(DataSource ds)
    {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(ds))
                        .usingDbTime() // Works on Postgres, MySQL, MariaDb, MS SQL, Oracle, DB2, HSQL and H2
                        .build());
    }

}
