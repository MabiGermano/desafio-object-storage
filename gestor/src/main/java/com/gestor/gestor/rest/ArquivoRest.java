package com.gestor.gestor.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.gestor.dao.ArquivoDao;
import com.gestor.gestor.dao.ConteudoDao;
import com.gestor.gestor.model.Arquivo;
import com.gestor.gestor.model.Conteudo;
import com.gestor.gestor.util.MetadadoUtil;

@RestController
@RequestMapping("/arquivo")
public class ArquivoRest {

	@Autowired 
	private ConteudoDao conteudoDao;
	@Autowired
	private MetadadoUtil util;
	@Autowired
	private ArquivoDao arquivoDao;

	@RequestMapping(value = "/inserir",method = RequestMethod.POST)
	public ResponseEntity<String> incluir(@RequestParam String nome,
			@RequestParam MultipartFile file) throws IOException{
		
		Conteudo conteudo = new Conteudo();
		
		conteudo.setBytes(file.getBytes());
		String caminho = conteudoDao.save(conteudo, nome);
		Arquivo arquivo = new Arquivo();
		arquivo.setNome(nome);
		arquivo.setTamanho(file.getSize());
		arquivo.setUltimaModificacao(util.obterMomentoAtual());
		arquivo.setConteudo(caminho);
		arquivoDao.save(arquivo);
		
		return ResponseEntity.ok().build();
	}
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public List<Conteudo> buscar() throws Exception{
		List<Arquivo> arquivos = arquivoDao.findAll();
		List<Conteudo> conteudo = conteudoDao.find(arquivos);		
	
		return conteudo;
	}
		
		
	
}
