package br.gov.economia.seddm.spu;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import br.gov.economia.seddm.spu.model.Usuario;

@Component
public class AutenticacaoBean {
	
	private Usuario usuario;

	@Bean
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
