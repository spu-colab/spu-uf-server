package br.gov.economia.seddm.spu.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.seddm.spu.core.model.Usuario;
import br.gov.economia.seddm.spu.core.repository.UsuarioRepositorio;

@RestController
@RequestMapping(path = "/auth/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	Iterable<Usuario> listar() {
		return usuarioRepositorio.findAll();
	}
	
	@GetMapping("/{id}")
	Optional<Usuario> obterPorId(@PathVariable Integer id) {
		return usuarioRepositorio.findById(id);
	}
	
	@PostMapping("/")
	Usuario novoUsuario(@RequestBody Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepositorio.save(usuario);
	}
	
	@PutMapping("/{id}")
	Usuario alterarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
		return usuarioRepositorio.findById(id)
				.map(usuarioAAlterar -> {
					usuarioAAlterar.setNome(usuario.getNome());
					usuarioAAlterar.setTelefone(usuario.getTelefone());
					return usuarioRepositorio.save(usuarioAAlterar);
				})
				.orElseGet(() -> {
					usuario.setUsuarioId(id);
					return usuarioRepositorio.save(usuario);
				});
	}
	

}
