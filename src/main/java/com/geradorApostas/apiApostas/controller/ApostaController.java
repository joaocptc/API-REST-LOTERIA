package com.geradorApostas.apiApostas.controller;

import java.net.URI;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.geradorApostas.apiApostas.dto.ApostaDTO;
import com.geradorApostas.apiApostas.model.Aposta;
import com.geradorApostas.apiApostas.repository.ApostaRepository;


@RestController
@RequestMapping(value = "/api")
public class ApostaController {

	@Autowired
	ApostaRepository apostaRepository;
	
	@PostMapping("/aposta/{email}")
	public ResponseEntity<ApostaDTO> geraAposta(@PathVariable(name = "email") String email, UriComponentsBuilder uriBuilder) {
		String aposta = geraNumeros();
		long qtd = apostaRepository.contaIguais(email, aposta);
		if ( qtd > 0 ) {
			while ( qtd > 0 ) {
				aposta = geraNumeros();
				qtd = apostaRepository.contaIguais(email, aposta);
			}
		} 
		Aposta ap = new Aposta();
		ap.setAposta(aposta);
		ap.setEmail(email);
		ap = apostaRepository.save(ap);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(ap.getId()).toUri();
		return ResponseEntity.created(uri).body(new ApostaDTO(ap));
	}
	
	private String geraNumeros() {
		String aposta = "";
		Random gerador = new Random();
		for (int i=0; i<6; i++) {
			aposta += gerador.nextInt(60);
			if ( i < 5 ) aposta += ",";
		}
		return aposta;
	}
	
	@GetMapping("/apostas/{email}")
	public Page<ApostaDTO> buscaApostas(@PathVariable(name = "email") String email, 
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Aposta> apostas = apostaRepository.findByEmail(email, paginacao);		
		return ApostaDTO.converter(apostas);
	}
}
