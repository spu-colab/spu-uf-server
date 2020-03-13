package br.gov.economia.seddm.spu.automacao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Navegador {

	private Response response;

	private Document documento;

	private Map<String, String> cookies;

	public Navegador() {
		this.cookies = new HashMap<String, String>();
	}

	public void get(String url) throws IOException {
		Requisicao requisicao = new Requisicao(Requisicao.GET, url);
		executarRequisicao(requisicao);
	}
	
	public void post(String url, Map<String, String> parametros) throws IOException {
		Requisicao requisicao = new Requisicao(Requisicao.POST, url);
		requisicao.setParametros(parametros);
		this.executarRequisicao(requisicao);
	}
		
	public void executarRequisicao(Requisicao requisicao) {
		System.out.println("==========================================================");
		System.out.println((requisicao.isGet() ? "GET" : "POST") + " para " + requisicao.getUrl());
		Connection conexao = Jsoup.connect(requisicao.getUrl());
		
		if(requisicao.getHeaders() != null) {
			conexao.headers(requisicao.getHeaders());
		}
		
		conexao.cookies(this.cookies);
		
		if(requisicao.getParametros() != null) {
			conexao.data(requisicao.getParametros());			
		}
		
		conexao.requestBody(requisicao.getRequestBody());
		
		
		
		try {
			if(requisicao.isGet()) {
				this.documento = conexao.get();
			} else {
				conexao.method(Method.POST);
				this.documento = conexao.post();
			}
		} catch (HttpStatusException se) {
			se.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}			
		
		this.response = conexao.response();
		
		System.out.println("Status: " + this.response.statusCode() + ": " + this.response.statusMessage());
		
		if(!this.response.cookies().isEmpty()) {
			for(String cookie : this.response.cookies().keySet()) {
				this.cookies.put(cookie, this.response.cookie(cookie));
			}
		}
		// System.out.println(this.getConteudo());		
	}
	
	public Response getResponse() {
		return this.response;
	}

	public Document getDocumento() {
		return documento;
	}
	
	public String getConteudo() {
		return this.getDocumento().text();
	}


}
