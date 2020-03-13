package br.gov.economia.seddm.spu.automacao.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.economia.seddm.spu.automacao.Navegador;
import br.gov.economia.seddm.spu.automacao.Requisicao;
import br.gov.economia.seddm.spu.automacao.repository.DocumentoRemessaRepository;
import br.gov.economia.seddm.spu.automocao.model.ComunicacaoSapiens;
import br.gov.economia.seddm.spu.automocao.model.EspecieComunicacaoSapiens;

@Service
public class SapiensServico {
	
	@Autowired
	private DocumentoRemessaRepository repositorio;
	
	private String csrf_token = null;
	
	
	public Collection<ComunicacaoSapiens> extrairProcessos() {
		Navegador navegador = new Navegador();
		try {
			carregarPaginaLogin(navegador);
			efetuarLogin(navegador);
			JSONObject dadosUsuarioJson = obterDadosUsuario(navegador);
			
			Integer id_superindentencia = dadosUsuarioJson
					.getJSONObject("result")
					.getJSONArray("records").getJSONObject(0)
					.getJSONArray("vinculacoesUsuariosPessoas").getJSONObject(0)
					.getInt("pessoa_id");
			System.out.println("id_superindentencia: " + id_superindentencia);
			
			Collection<ComunicacaoSapiens> comunicacoesSapiens = listarComunicacaoes(navegador, id_superindentencia);
			
			for(ComunicacaoSapiens comunicacaoSapiens : comunicacoesSapiens) {
				Optional<ComunicacaoSapiens> consultaComunicacao = repositorio.findById(comunicacaoSapiens.getId());
				if(consultaComunicacao.isEmpty()) {
					repositorio.save(comunicacaoSapiens);
				}
			}
			return comunicacoesSapiens;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	private List<ComunicacaoSapiens> listarComunicacaoes(Navegador navegador, Integer id_superintendencia) throws IOException {
		Requisicao postJson = new Requisicao(Requisicao.POST, "https://sapiens.agu.gov.br/route");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		postJson.setHeaders(headers);
		
		List<ComunicacaoSapiens> comunicacoes = new ArrayList<ComunicacaoSapiens>();
		
		int pagina = 0;
		int tamanhoPagina = 25;
		boolean terminouLeitura = false;
		while(!terminouLeitura) {
			pagina++;			
			postJson.setRequestBody("{\r\n" + 
					"    \"action\": \"SapiensAdministrativo_Comunicacao\",\r\n" + 
					"    \"method\": \"getComunicacao\",\r\n" + 
					"    \"data\": [\r\n" + 
					"        {\r\n" + 
					"            \"fetch\": [\r\n" + 
					"                \"pasta\",\r\n" + 
					"                \"pasta.processoJudicial\",\r\n" + 
					"                \"setorResponsavel\",\r\n" + 
					"                \"setorResponsavel.unidade\",\r\n" + 
					"                \"especieComunicacao\",\r\n" + 
					"                \"pessoaDestino\",\r\n" + 
					"                \"usuarioRemessa\",\r\n" + 
					"                \"documentoRemessa\",\r\n" + 
					"                \"documentoRemessa.componentesDigitais\",\r\n" + 
					"                \"documentoRemessa.componentesDigitais.assinaturas\"\r\n" + 
					"            ],\r\n" + 
					"            \"filter\": [\r\n" + 
					"                {\r\n" + 
					"                    \"property\": \"pessoaDestino.id\",\r\n" + 
					"                    \"value\": \"in:"+ id_superintendencia +"\"\r\n" + 
					"                },\r\n" + 
					"                {\r\n" + 
					"                    \"property\": \"dataHoraRemessa\",\r\n" + 
					"                    \"value\": \"isNotNull\"\r\n" + 
					"                },\r\n" + 
					"                {\r\n" + 
					"                    \"property\": \"dataHoraResposta\",\r\n" + 
					"                    \"value\": \"isNull\"\r\n" + 
					"                }\r\n" +  
					"            ],\r\n" + 
					"            \"page\": " + pagina + ",\r\n" + 
					"            \"start\": 0,\r\n" + 
					"            \"limit\": " + tamanhoPagina + "\r\n" + 
					"        }\r\n" + 
					"    ],\r\n" + 
					"    \"type\": \"rpc\",\r\n" + 
					"    \"tid\": 2\r\n" + 
					"}");
			System.out.println("Lendo página " + pagina);
			navegador.executarRequisicao(postJson);
			
			String respostaTxt = navegador.getDocumento().getElementsByTag("body").get(0).text();		
			JSONArray arrayResposta = new JSONArray(respostaTxt);
			JSONObject result = arrayResposta.getJSONObject(0).getJSONObject("result");
			JSONArray records = result.getJSONArray("records");
			terminouLeitura = records.isEmpty() || records.length() < tamanhoPagina;
			
			records.forEach(record -> {
				ComunicacaoSapiens documentoRemessa = new ComunicacaoSapiens((JSONObject) record);
				if(documentoRemessa.getEspecieComunicacao().isEspecieUsucapiao()) {
					comunicacoes.add(documentoRemessa);					
				}
			});
		};		
		
		return comunicacoes;
		
	}

	private JSONObject obterDadosUsuario(Navegador navegador) throws IOException {
		Requisicao postJson = new Requisicao(Requisicao.POST, "https://sapiens.agu.gov.br/route");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		postJson.setHeaders(headers);
		
		postJson.setRequestBody("{\r\n" + 
				"    \"action\": \"SapiensMain_Usuario\",\r\n" + 
				"    \"method\": \"getUsuario\",\r\n" + 
				"    \"data\": [\r\n" + 
				"        {\r\n" + 
				"            \"sessao\": true,\r\n" + 
				"            \"fetch\": [\r\n" + 
				"                \"vinculacoesUsuariosPessoas\",\r\n" + 
				"                \"vinculacoesUsuariosPessoas.pessoa\"\r\n" + 
				"            ],\r\n" + 
				"            \"page\": 1,\r\n" + 
				"            \"start\": 0,\r\n" + 
				"            \"limit\": 25\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"type\": \"rpc\",\r\n" + 
				"    \"tid\": 1\r\n" + 
				"}");
		
		navegador.executarRequisicao(postJson);
		System.out.println(navegador.getConteudo());
		
		String respostaTxt = navegador.getDocumento().getElementsByTag("body").get(0).text();		
		JSONArray arrayResposta = new JSONArray(respostaTxt);
		if(arrayResposta.isEmpty()) return null;
		return arrayResposta.getJSONObject(0);
		
	}

	private void efetuarLogin(Navegador navegador) throws IOException {
		// submete formulário de login
		Requisicao loginRequest = new Requisicao(
				Requisicao.POST, "https://sapiens.agu.gov.br/login_check");
		
		/*
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "text/plain");
		loginRequest.setHeaders(headers);
		*/
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("_csrf_token", this.csrf_token);
		parametros.put("_username", "#####");
		parametros.put("_password", "#####");
		parametros.put("_submit", "Login");
		loginRequest.setParametros(parametros);		
		
		navegador.executarRequisicao(loginRequest);
	}

	private void carregarPaginaLogin(Navegador navegador) throws IOException {
		// carrega página de login
		navegador.get("https://sapiens.agu.gov.br/login");
		Elements csrfElements = navegador.getDocumento().getElementsByClass("csrf_token");
		csrfElements.forEach(el -> {
			this.csrf_token  = el.val();
		});
		System.out.println(this.csrf_token);
	}
	
	
	

}
