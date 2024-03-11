package org.redisClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PreDestroy;

@Component
@Profile("redis")
public class jedisClient {
    private Jedis jedis;
    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(jedisClient.class);
    public jedisClient(final JedisPool pool)
    {
        this.pool = pool;
        jedis= pool.getResource();

    }
    public void doPublish()
    {
        jedis.set("first_key","first_value");
    }
    @PreDestroy
    public void cleanup()
    {
        logger.info("terminating jedis connection");
        this.pool.close();
    }

}
