package br.gov.economia.seddm.spu.core.controller;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.seddm.spu.core.exception.UsuarioLDAPInvalidoException;
import br.gov.economia.seddm.spu.core.exception.UsuarioLDAPNaoEncontradoException;
import br.gov.economia.seddm.spu.core.jwt.JwtRequest;
import br.gov.economia.seddm.spu.core.jwt.JwtResponse;
import br.gov.economia.seddm.spu.core.jwt.JwtTokenUtil;
import br.gov.economia.seddm.spu.core.jwt.JwtUserDetailsService;
import br.gov.economia.seddm.spu.core.pojo.UsuarioLDAP;

@RestController
@CrossOrigin
@RequestMapping(path = "/auth")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		try {
			UsuarioLDAP usuarioLDAP = obterUsuarioLDAP(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			if(usuarioLDAP == null) {
				throw new UsuarioLDAPNaoEncontradoException();
			}
			// TODO encontrou usuário valido, cria/atualiza cadastro
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			
		} catch(UsuarioLDAPNaoEncontradoException unee) {
			try {
				authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			} catch (Exception e) {
				throw e;
			}			
		} catch (Exception e) {
			throw e;
		}		

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	public static UsuarioLDAP obterUsuarioLDAP(String username, String password) throws NamingException, UsuarioLDAPInvalidoException {

		Properties props = new Properties();
	    props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    props.put(Context.PROVIDER_URL, "ldap://10.209.8.245:389");
	    props.put(Context.SECURITY_AUTHENTICATION, "simple");
	    props.put(Context.SECURITY_PRINCIPAL, username + "@mp.intra");
        props.put(Context.SECURITY_CREDENTIALS, password);
        
        try {        	
        	DirContext context = new InitialDirContext(props);
        	SearchControls ctls = new SearchControls();
        	ctls.setReturningAttributes(UsuarioLDAP.ATRIBUTOS);
        	ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        	
        	NamingEnumeration<?> answer = context.search(
        			UsuarioLDAP.CONTAINER,
        			"(&(objectClass=user)(description="+username+"))", 
        			ctls);
        	
        	if(answer.hasMore()) {
        		SearchResult rslt = (SearchResult) answer.next();
        		Attributes attrs = rslt.getAttributes();
        		
        		/* leitura dos atributos
        		Iterator<Attribute> iterator = (Iterator<Attribute>) attrs.getAll().asIterator();
        		while(iterator.hasNext()) {
        			Attribute atributo = iterator.next();
        			System.out.println(atributo);
        		}
        		// System.out.println(resultado);
        		// */
        		
        		UsuarioLDAP usuarioLDAP = new UsuarioLDAP();
        		usuarioLDAP.setLogin(username);
        		usuarioLDAP.setSenha(password);
        		usuarioLDAP.setNome((String) attrs.get("displayName").get());
        		usuarioLDAP.setEmail((String) attrs.get("mail").get());
        		usuarioLDAP.setTelefone((String) attrs.get("telephoneNumber").get());
        		usuarioLDAP.setUf((String) attrs.get("l").get());
        		
        		usuarioLDAP.validar();
        		
        		context.close();
        		
        		return usuarioLDAP;
        		
        	}
        	return null;
        } finally {
		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}