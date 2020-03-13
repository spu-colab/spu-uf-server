package br.gov.economia.seddm.spu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "divisaoorganograma")
public class DivisaoOrganograma {
	
	public static final String PREFIXO_NOME_SUPERINTENDENCIA = "Superintendência do Patrimônio da União em ";
	
	@Id
	@SequenceGenerator(name="divisaoorganogramaid_seq", sequenceName="divisaoorganogramaid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="divisaoorganogramaid_seq")
	private Integer divisaoOrgranogramaId;
	
	private String nome;
	
	

	public Integer getDivisaoOrgranogramaId() {
		return divisaoOrgranogramaId;
	}

	public void setDivisaoOrgranogramaId(Integer divisaoOrgranogramaId) {
		this.divisaoOrgranogramaId = divisaoOrgranogramaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	

}
