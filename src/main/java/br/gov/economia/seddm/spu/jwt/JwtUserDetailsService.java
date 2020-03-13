package br.gov.economia.seddm.spu.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.economia.seddm.spu.model.Usuario;
import br.gov.economia.seddm.spu.repository.UsuarioRepositorio;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private UsuarioRepositorio repositorio;
	
	public JwtUserDetailsService(UsuarioRepositorio userService) {
        this.repositorio = userService;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = repositorio.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Nenhum usuário encontrado com o login: '%s'.", username));
        } else {
            return new JwtUser(user.getLogin(), user.getSenha(), user);
        }
	}

}
