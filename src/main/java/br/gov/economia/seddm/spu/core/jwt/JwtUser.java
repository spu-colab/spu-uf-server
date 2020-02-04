package br.gov.economia.seddm.spu.core.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.economia.seddm.spu.core.model.Usuario;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -6621738698379364852L;
	
	private String username;
	
	private String password;

	private Usuario usuario;

	public JwtUser(String username, String password, Usuario usuario) {
		this.username = username;
		this.password = password;
		this.setUsuario(usuario);
		this.getUsuario().setSenha(null);
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
