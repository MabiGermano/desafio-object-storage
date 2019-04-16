package com.gestor.gestor.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestor.gestor.model.Arquivo;

public interface ArquivoDao extends JpaRepository<Arquivo, Long> {

}
