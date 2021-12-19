package br.com.lduran.redispersistence.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class JedisService
{
	@Autowired
	private Jedis jedis;

	public void insereMembrosNoGrupo(String chave, String... membros)
	{
		for (String membro : membros)
		{
			jedis.sadd(chave, membro);
		}
	}

	public Set<String> obtemListaMembros(String chave)
	{
		return jedis.smembers(chave);
	}

	public void atualizaAtributoGrupo(String chave, String atributo, String valor)
	{
		jedis.hset(chave, atributo, valor);
	}

	public void atualizaAtributoGrupo(String chave, Map<String, String> atributos)
	{
		atributos.entrySet().stream().forEach(e -> atualizaAtributoGrupo(chave, e.getKey(), e.getValue()));
	}

	public String exibeValorAtributo(String chave, String atributo)
	{
		return jedis.hget(chave, atributo);
	}

	public Map<String, String> exibeTodosAtributos(String chave)
	{
		return jedis.hgetAll(chave);
	}
}
