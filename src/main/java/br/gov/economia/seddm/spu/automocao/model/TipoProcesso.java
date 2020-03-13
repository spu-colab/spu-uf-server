package br.gov.economia.seddm.spu.automocao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipoprocesso", schema = "autodoc")
public class TipoProcesso {
	
	static int ELETRONICO_ID = 1;
	static int FISICO_ID = 2;

	static TipoProcesso ELETRONICO = new TipoProcesso(ELETRONICO_ID, "Eletr�nico");
	static TipoProcesso FISICO = new TipoProcesso(FISICO_ID, "F�sico");

	@SuppressWarnings("serial")
	static List<TipoProcesso> TIPOS_PROCESSO = new ArrayList<TipoProcesso>() {{ 
			add(ELETRONICO); 
			add(FISICO); 
		}};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tipoProcessoId;
	
	private String descricao;

	public TipoProcesso() {
	}
	
	public TipoProcesso(Integer tipoProcessoId) {
		this.tipoProcessoId = tipoProcessoId;
	}

	public TipoProcesso(Integer tipoProcessoId, String descricao) {
		this.tipoProcessoId = tipoProcessoId;
		this.descricao = descricao;
	}

	public Integer getTipoProcessoId() {
		return tipoProcessoId;
	}

	public void setTipoProcessoId(Integer tipoProcessoId) {
		this.tipoProcessoId = tipoProcessoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
