package br.gov.economia.seddm.spu.automacao;

import java.util.Map;

public class Requisicao {
	
	public static final String GET = "GET";
	public static final String POST = "POST";

	private String metodo = GET;
	
	private String url;
	
	private Map<String, String> parametros;
	private Map<String, String> headers;
	
	private String requestBody;

	public Requisicao(String metodoHTTP, String url) {
		this.metodo = metodoHTTP;
		this.url = url;
	}

	public boolean isGet() {
		return this.metodo == GET;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	
	
	

}
