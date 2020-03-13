package br.gov.economia.seddm.spu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.economia.seddm.spu.model.DivisaoOrganograma;
import br.gov.economia.seddm.spu.repository.DivisaoOrganogramaRepositorio;

@Service
public class DivisaoOrganogramaServico {

	@Autowired
	private DivisaoOrganogramaRepositorio repositorio;
	
	public DivisaoOrganograma obterOuGerarSuperintendencia(String uf) {
		DivisaoOrganograma superintendencia = repositorio.findByNome(uf);
		if(superintendencia == null) {
			superintendencia = new DivisaoOrganograma();
			superintendencia.setNome(DivisaoOrganograma.PREFIXO_NOME_SUPERINTENDENCIA + uf);
			superintendencia = repositorio.save(superintendencia);
		}
		return superintendencia;
	}

}
