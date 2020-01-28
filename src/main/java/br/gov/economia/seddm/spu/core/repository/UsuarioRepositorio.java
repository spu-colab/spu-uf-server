package br.gov.economia.seddm.spu.core.repository;

import org.springframework.data.repository.CrudRepository;

import br.gov.economia.seddm.spu.core.model.Usuario;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer> {

	Usuario findByLogin(String login);

}
