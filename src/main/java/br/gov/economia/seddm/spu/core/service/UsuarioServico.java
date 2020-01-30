package br.gov.economia.seddm.spu.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.economia.seddm.spu.core.model.Usuario;
import br.gov.economia.seddm.spu.core.repository.UsuarioRepositorio;

@Service
public class UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UsuarioRepositorio getRepositorio() {
		return this.repositorio;
	}
	
	public Iterable<Usuario> listar() {
		return repositorio.findAll();
	}
	
	public Optional<Usuario> obterPorId(Integer id) {
		return repositorio.findById(id);
	}
	
	public Usuario criar(Usuario usuario) {
		usuario.validar();		
		return repositorio.save(usuario);
	}
	
	public Usuario alterar(Usuario usuario) throws Exception {
		Optional<Usuario> usuarioPesquisado = this.obterPorId(usuario.getUsuarioId());		
		if(!usuarioPesquisado.isPresent()) {
			throw new Exception("Usuário não encontrado");
		}
		
		Usuario usuarioAAlterar = usuarioPesquisado.get();
		usuarioAAlterar.setNome(usuario.getNome());
		usuarioAAlterar.setTelefone(usuario.getTelefone());
		return repositorio.save(usuarioAAlterar);			
	}
	
	public String cifrarSenha(String senha) {
		return passwordEncoder.encode(senha);
	}


	

}
