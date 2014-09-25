package com.wataru420.helloworld;

import io.dropwizard.Configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ApplicationModule extends AbstractModule
{
	@Override
    protected void configure()
    {

    }

    @Provides
    public ApplicationConfiguration configuration(Configuration configuration)
    {
        return (ApplicationConfiguration) configuration;
    }

	@Provides
	public JedisPool provideJedisPool(ApplicationConfiguration applicationConfiguration)
	{
		RedisConfiguration redisConfig = applicationConfiguration.getRedis();
		return new JedisPool(
				new JedisPoolConfig(),
				redisConfig.getHostname(),
				redisConfig.getPort() );
	}
}
