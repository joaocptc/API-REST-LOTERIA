package com.geradorApostas.apiApostas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geradorApostas.apiApostas.model.Aposta;

public interface ApostaRepository extends JpaRepository<Aposta, Long> {

	@Query(value = "SELECT count(*) FROM TB_APOSTAS WHERE email = ?1 AND aposta = ?2", nativeQuery = true)
	long contaIguais(String email, String aposta);

	Page<Aposta> findByEmail(String email, Pageable paginacao);

}


