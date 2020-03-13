package br.gov.economia.seddm.spu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.economia.seddm.spu.pojo.UsuarioLDAP;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	public Usuario() {}
	
	/*
	Usuario(String login, String senha, String nome, String telefone) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.telefone = telefone;
	}
	*/
	
	public void preencherDados(UsuarioLDAP usuarioLDAP) {
		this.login = usuarioLDAP.getLogin();
		this.nome = usuarioLDAP.getNome();
		this.telefone = usuarioLDAP.getTelefone();
		this.email = usuarioLDAP.getEmail();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer usuarioId;
	
	@ManyToOne
	private DivisaoOrganograma superindentencia;
	
	@Column(unique = true, nullable = false)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String nome;
	
	private String email;
	
	private String telefone;

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void validar() {
		// TODO Auto-generated method stub		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DivisaoOrganograma getSuperindentencia() {
		return superindentencia;
	}

	public void setSuperindentencia(DivisaoOrganograma superindentencia) {
		this.superindentencia = superindentencia;
	}
	
}
