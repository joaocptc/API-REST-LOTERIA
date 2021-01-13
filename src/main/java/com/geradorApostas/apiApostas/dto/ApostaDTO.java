package com.geradorApostas.apiApostas.dto;

import org.springframework.data.domain.Page;

import com.geradorApostas.apiApostas.model.Aposta;

public class ApostaDTO {
	
	private String aposta;
	
	public ApostaDTO() {
		
	}
	
	public ApostaDTO(Aposta ap) {
		this.aposta = ap.getAposta();
	}

	public static Page<ApostaDTO> converter(Page<Aposta> apostas) {
		return apostas.map(ApostaDTO::new);
	}
	
	public String getAposta() {
		return aposta;
	}

	public void setAposta(String aposta) {
		this.aposta = aposta;
	}
}
