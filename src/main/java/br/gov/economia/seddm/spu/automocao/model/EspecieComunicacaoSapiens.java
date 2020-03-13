package br.gov.economia.seddm.spu.automocao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "sapiens_especiecomunicacao",schema = "automacao")
public class EspecieComunicacaoSapiens {
	
	@Id
	Integer id;
	
	String nome, descricao;
	
	public EspecieComunicacaoSapiens() {
	}

	public EspecieComunicacaoSapiens(JSONObject especieComunicaoJson) {
		this.id = especieComunicaoJson.getInt("id");
		this.nome = especieComunicaoJson.getString("nome");
		this.descricao = especieComunicaoJson.getString("descricao");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isEspecieUsucapiao() {
		return this.nome.endsWith("SOLICITAÇÃO DE SUBSÍDIOS");
	}

}
