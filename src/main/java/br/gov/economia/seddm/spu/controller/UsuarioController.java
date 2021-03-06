package br.gov.economia.seddm.spu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.seddm.spu.AutenticacaoBean;
import br.gov.economia.seddm.spu.model.Usuario;
import br.gov.economia.seddm.spu.service.UsuarioServico;

@RestController
@RequestMapping(path = "/auth/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private AutenticacaoBean session;
	
	@GetMapping("/hello")
	String hello() {
		// Usuario usuario = (Usuario) session.getAttribute("usuario");
		Usuario usuario = session.getUsuario();
		return "Hello, " + usuario.getNome() + "!";
	}
	
	@GetMapping("/")
	Iterable<Usuario> listar() {
		return usuarioServico.listar();
	}
	
	@GetMapping("/{id}")
	Optional<Usuario> obterPorId(@PathVariable Integer id) {
		return usuarioServico.obterPorId(id);
	}
	
	@PostMapping("/")
	Usuario criar(@RequestBody Usuario usuario) {
		return usuarioServico.criar(usuario);
	}
		
	@PutMapping("/{id}")
	Usuario alterar(@RequestBody Usuario usuario, @PathVariable Integer id) throws Exception {
		usuario.setUsuarioId(id);
		return usuarioServico.alterar(usuario);
	}
	

}
