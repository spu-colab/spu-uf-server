package br.gov.economia.seddm.spu.core;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.gov.economia.seddm.spu.core.controller.RequestFilter;
import br.gov.economia.seddm.spu.core.jwt.JwtAuthenticationEntryPoint;
import br.gov.economia.seddm.spu.core.jwt.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private RequestFilter requestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// autenticacaoPorSessao(httpSecurity);
		definirRestricoesAcessoRotas(httpSecurity);
	}

	private void definirRestricoesAcessoRotas(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				
			// TODO CSRF desabilitado por enquanto...
			.csrf().disable()

				// dont authenticate this particular request
				.authorizeRequests()
					
					//.antMatchers(HttpMethod.GET, "/auth/login")
					//.permitAll()
					
					.antMatchers(HttpMethod.POST,
							"/login").permitAll()

					// all other requests need to be authenticated
					.anyRequest().authenticated()
					.and()

			.cors().and()
			
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/*
	private void autenticacaoPorSessao(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST,
						"/auth/login",
						"/auth/usuario/")
				.permitAll()
				.anyRequest().authenticated();
	}
	*/
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token", "X-CSRF-TOKEN"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "X-CSRF-TOKEN"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
