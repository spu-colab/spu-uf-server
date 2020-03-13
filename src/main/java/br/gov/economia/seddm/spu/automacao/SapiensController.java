package br.gov.economia.seddm.spu.automacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.economia.seddm.spu.automacao.service.SapiensServico;
import br.gov.economia.seddm.spu.automocao.model.ComunicacaoSapiens;

@RestController
@RequestMapping(path = "/sapiens")
public class SapiensController {
	
	@Autowired
	private SapiensServico sapiensServico;

	@GetMapping(path = "/demandas")
	Collection<ComunicacaoSapiens> listarDemandas() {
		return sapiensServico.extrairProcessos();		
	}
	

}
