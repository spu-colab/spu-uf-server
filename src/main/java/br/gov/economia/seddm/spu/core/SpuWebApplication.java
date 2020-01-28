package br.gov.economia.seddm.spu.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value = {
		"br.gov.economia.seddm.spu.core.model",
		"br.gov.economia.seddm.spu.autodoc.model"
		})
public class SpuWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpuWebApplication.class, args);
	}

}
