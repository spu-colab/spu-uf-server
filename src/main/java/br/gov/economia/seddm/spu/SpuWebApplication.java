package br.gov.economia.seddm.spu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
@EntityScan(value = {
		"br.gov.economia.seddm.spu.model",
		"br.gov.economia.seddm.spu.automacao.model"
		})
@ComponentScan(basePackages = { 
		"br.gov.economia.seddm.spu.*" })
 */

@SpringBootApplication
@EnableJpaRepositories({
		"br.gov.economia.seddm.spu.repository",
		"br.gov.economia.seddm.spu.automacao.repository"
	})
public class SpuWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpuWebApplication.class, args);
	}

}
