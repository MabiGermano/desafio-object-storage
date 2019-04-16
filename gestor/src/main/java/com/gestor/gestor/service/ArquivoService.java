package com.gestor.gestor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestor.gestor.dao.ArquivoDao;
import com.gestor.gestor.model.Arquivo;

/**
 * @author M. Beatriz Germano
 * 
 * Classe que possui as regras de negócio referentes a Arquivo.
 * Metodos imlpementados tratam os dados vindos da classe DAO, para uso no RestController
 **/
@Service
public class ArquivoService {
	@Autowired
	private ArquivoDao arquivoDao;

	public Arquivo EncontrarArqivo(Long id) {
		Optional<Arquivo> optional = arquivoDao.findById(id);
		return optional.get();
	}
	
	public Arquivo EncontrarArqivoPeloNome(String nome) {
		Optional<Arquivo> optional = arquivoDao.findByNome(nome);
		return optional.get();
	}
	
	public void salvar(Arquivo arquivo) {
		arquivoDao.save(arquivo);
	}
	
	public List<Arquivo> EncontrarListaArquivos(){
		List<Arquivo> lista = arquivoDao.findAll();
		return lista;
	}
}
