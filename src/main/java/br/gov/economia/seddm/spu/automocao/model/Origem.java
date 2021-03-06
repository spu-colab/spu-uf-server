package br.gov.economia.seddm.spu.automocao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "origem", schema = "autodoc")
public class Origem {
	
	static int SAPIENS_ID = 1;
	static int SPUNET_ID = 2;

	static Origem SAPIENS = new Origem(SAPIENS_ID, "Sapiens");
	static Origem SPUNET = new Origem(SPUNET_ID, "SPUNet");

	@SuppressWarnings("serial")
	static List<Origem> ORIGENS = new ArrayList<Origem>() {{ 
		add(SAPIENS); 
		add(SPUNET); 
		}};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer origemId;
	
	private String descricao;

	public Origem() {
	}
	
	public Origem(Integer origemId, String descricao) {
		this.origemId = origemId;
		this.descricao = descricao;
	}

	public Origem(Integer origemId) {
		this.origemId = origemId;
	}

	public Integer getOrigemId() {
		return origemId;
	}

	public void setOrigemId(Integer origemId) {
		this.origemId = origemId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
