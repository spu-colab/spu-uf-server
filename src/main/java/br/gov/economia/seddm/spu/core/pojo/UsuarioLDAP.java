package br.gov.economia.seddm.spu.core.pojo;

public class UsuarioLDAP {
	
	// TODO passar cada atributo para um arquivo de propriedades
	public final static String[] ATRIBUTOS = { "description", "displayName", "mail", "l", "telephoneNumber", "mailNickname" };
	
	// TODO passar para arquivo de propriedades
    public final static String CONTAINER = "OU=Usuarios,OU=SPUs,OU=MP,DC=mp,DC=intra";
	
	private String login;
	
	private String senha;
	
	private String nome;
	
	private String email;
	
	private String telefone;
	
	private String uf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
