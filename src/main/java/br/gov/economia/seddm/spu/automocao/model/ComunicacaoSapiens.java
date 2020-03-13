package br.gov.economia.seddm.spu.automocao.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "sapiens_comunicacao", schema = "automacao")
public class ComunicacaoSapiens {
	
	@Id
	Integer id;
	
	Date dataHoraInicioPrazo, dataHoraFinalPrazo, dataHoraRemessa;
	
	String nup, chave, titulo;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	EspecieComunicacaoSapiens especieComunicacao;
	
	public ComunicacaoSapiens() {
	}

	public ComunicacaoSapiens(JSONObject remessaJson) {
		this.id = remessaJson.getInt("id");
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		
		if(remessaJson.get("dataHoraInicioPrazo") != null && remessaJson.getJSONObject("dataHoraInicioPrazo").getString("date") != null) {
			try {
				this.dataHoraInicioPrazo = dateFormater.parse((remessaJson.getJSONObject("dataHoraInicioPrazo").getString("date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		
		if(remessaJson.get("dataHoraFinalPrazo") != null && remessaJson.getJSONObject("dataHoraFinalPrazo").getString("date") != null) {
			try {
				this.dataHoraFinalPrazo = dateFormater.parse((remessaJson.getJSONObject("dataHoraFinalPrazo").getString("date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		if(remessaJson.get("dataHoraRemessa") != null && remessaJson.getJSONObject("dataHoraRemessa").getString("date") != null) {
			try {
				this.dataHoraRemessa = dateFormater.parse((remessaJson.getJSONObject("dataHoraRemessa").getString("date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		if(remessaJson.getJSONObject("especieComunicacao") != null) {
			this.especieComunicacao = new EspecieComunicacaoSapiens(remessaJson.getJSONObject("especieComunicacao"));
		}
		
		this.nup = remessaJson.getJSONObject("pasta").getString("NUP");
		this.chave = remessaJson.getJSONObject("pasta").getString("chaveAcesso");
		this.titulo = remessaJson.getJSONObject("pasta").getString("titulo");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataHoraInicioPrazo() {
		return dataHoraInicioPrazo;
	}

	public void setDataHoraInicioPrazo(Date dataHoraInicioPrazo) {
		this.dataHoraInicioPrazo = dataHoraInicioPrazo;
	}

	public Date getDataHoraFinalPrazo() {
		return dataHoraFinalPrazo;
	}

	public void setDataHoraFinalPrazo(Date dataHoraFinalPrazo) {
		this.dataHoraFinalPrazo = dataHoraFinalPrazo;
	}

	public Date getDataHoraRemessa() {
		return dataHoraRemessa;
	}

	public void setDataHoraRemessa(Date dataHoraRemessa) {
		this.dataHoraRemessa = dataHoraRemessa;
	}

	public EspecieComunicacaoSapiens getEspecieComunicacao() {
		return especieComunicacao;
	}

	public void setEspecieComunicacao(EspecieComunicacaoSapiens especieComunicacao) {
		this.especieComunicacao = especieComunicacao;
	}

	public String getNup() {
		return nup;
	}

	public void setNup(String nup) {
		this.nup = nup;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	

}
