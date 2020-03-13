package br.gov.economia.seddm.spu.repository;

import org.springframework.data.repository.CrudRepository;

import br.gov.economia.seddm.spu.model.DivisaoOrganograma;

public interface DivisaoOrganogramaRepositorio extends CrudRepository<DivisaoOrganograma, Integer> {

	DivisaoOrganograma findByNome(String nome);

}
