package br.com.lduran.redispersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.lduran.redispersistence.service.JedisService;

@SpringBootApplication
public class RedispersistenceApplication implements CommandLineRunner
{
	@Autowired
	private JedisService jedisService;

	public static void main(String[] args)
	{
		SpringApplication.run(RedispersistenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		// =========================
		// Id da Planilha de Boletas
		// =========================
		String id_operacao = "f3dec1d6-d193-4c2c-805d-11e819c52a257";

		// =====================================
		// Forma a chave usando o Id da Planilha
		// =====================================
		StringBuilder chave = new StringBuilder("planilhasEnquadramento:");
		chave.append(id_operacao);
		chave.append(":boletas");

		// ======================
		// Forma lista de boletas
		// ======================
		String[] boletasTeste = { "fc8ef35f-256f-4364-8a65-011a05e8efde", "7a94fce5-09d7-40dc-a4e3-616cf93d69e6",
				"854fffbc-af7e-4868-b6d9-0ccb832c7b06", "0a1af5ff-163b-4226-ab7b-38233604f424",
				"3ee826c9-826a-41ba-8840-7881c0a97645", "73f1d0e4-ff3a-4321-91ee-40b3d3fa49eb" };

		// =======================
		// Insere boletas no Redis
		// =======================
		jedisService.insereMembrosNoGrupo(chave.toString(), boletasTeste);

		// ======================
		// Obtêm lista de boletas
		// ======================
		System.out.println();
		Set<String> boletas = jedisService.obtemListaMembros(chave.toString());
		boletas.forEach(System.out::println);
		System.out.println();

		// ===========================
		// Atualizar status das boletas
		// ===========================
		StringBuilder chaveConsultaAtrib = new StringBuilder("planilhasEnquadramento:");
		chave.append(id_operacao);

		jedisService.atualizaAtributoGrupo(chaveConsultaAtrib.toString(), "estado", "EM_PROCESSAMENTO");

		// ========================
		// Verifca o campo "estado"
		// ========================
		String valorAtributo = jedisService.exibeValorAtributo(chaveConsultaAtrib.toString(), "estado");
		System.out.println("estado: " + valorAtributo + "\n");

		// =================================
		// Obtêm todos os atributos do Grupo
		// =================================
		Map<String, String> atribsConsulta = jedisService.exibeTodosAtributos(chaveConsultaAtrib.toString());
		atribsConsulta.forEach((k, v) -> System.out.println(k + ": " + v));
		System.out.println();

		// =======================
		// Forma Mapa de Atributos
		// =======================
		Map<String, String> atributos = new HashMap<>();
		atributos.put("estado", "PROCESSADO");
		atributos.put("bucket", "fu7-xxy");
		atributos.put("object", "enquadramento_excel/qwyeuqwieyqwuie.xls");

		// ======================================
		// Atualizar vários atributos das boletas
		// ======================================
		jedisService.atualizaAtributoGrupo(chaveConsultaAtrib.toString(), atributos);

		// =================================
		// Obtêm todos os atributos do Grupo
		// =================================
		atribsConsulta = jedisService.exibeTodosAtributos(chaveConsultaAtrib.toString());
		atribsConsulta.forEach((k, v) -> System.out.println(k + ": " + v));
	}
}
