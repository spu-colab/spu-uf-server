package br.gov.economia.seddm.spu.automacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.gov.economia.seddm.spu.automocao.model.ComunicacaoSapiens;

public interface DocumentoRemessaRepository extends CrudRepository<ComunicacaoSapiens, Integer> {
	

}
