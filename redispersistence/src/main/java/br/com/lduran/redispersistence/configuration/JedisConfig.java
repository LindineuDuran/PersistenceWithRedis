package br.com.lduran.redispersistence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;

@Configuration
public class JedisConfig
{
	@Bean
	public Jedis jedis()
	{
		Jedis jedis = new Jedis();

		return jedis;
	}
}
