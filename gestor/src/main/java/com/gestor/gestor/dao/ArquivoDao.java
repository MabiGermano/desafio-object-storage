package com.gestor.gestor.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.gestor.model.Arquivo;
/**
 * @author M. Beatriz Germano
 * Interface responsável pela comunicação com o banco de dados sobre o objeto Arquivo
 **/
public interface ArquivoDao extends JpaRepository<Arquivo, Long> {

	@Transactional(readOnly = true)
	Optional<Arquivo> findByNome(String nome);
}
