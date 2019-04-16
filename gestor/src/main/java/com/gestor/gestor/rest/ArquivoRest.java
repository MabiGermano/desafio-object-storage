package com.gestor.gestor.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestor.gestor.dao.ConteudoDao;
import com.gestor.gestor.model.Arquivo;
import com.gestor.gestor.model.Conteudo;
import com.gestor.gestor.service.ArquivoService;
import com.gestor.gestor.util.MetadadoUtil;

/**
 * @author M. Beatriz Germano
 * 
 * Classe Rest que gerencia as requisições recebidas pela API
 * */
@RestController
@RequestMapping("/arquivo")
public class ArquivoRest {

	@Autowired 
	private ConteudoDao conteudoDao;
	@Autowired
	private MetadadoUtil util;
	@Autowired
	private ArquivoService arquivoService;
	
	/**
	 * Método de inclusão.
	 * Durante a execução o código, o conteúdo do arquivo recebido é salvo no projeto e os metadados relacionados
	 * são salvos no banco de dados.
	 * 
	 *   @param nome {@link String}
	 *   @param file {@link MultipartFile}
	 *   
	 *   @return the response entity
	 **/
	@RequestMapping(value = "/inserir",method = RequestMethod.POST)
	public ResponseEntity<String> inserir(@RequestParam String nome,
			@RequestParam MultipartFile file) throws IOException{
		
		Conteudo conteudo = new Conteudo();
		conteudo.setBytes(file.getBytes());
		String caminho = conteudoDao.save(conteudo, nome);
		
		Arquivo arquivo = new Arquivo();
		arquivo.setNome(nome);
		arquivo.setTamanho(file.getSize());
		arquivo.setUltimaModificacao(util.obterMomentoAtual());
		arquivo.setConteudo(caminho);
		
		arquivoService.salvar(arquivo);
		
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Método de busca.
	 * 
	 * Código executado de forma que buscará e retornará todos os arquivos registrados no projeto.
	 *   
	 * @return conteudos {@link List<Conteudo>}
	 **/
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public List<Conteudo> buscar() throws Exception{	
		List<Conteudo> conteudos = new ArrayList<>();
		List<Arquivo>arquivos = arquivoService.EncontrarListaArquivos();
		for(Arquivo arquivo : arquivos) {
			String caminho = arquivo.getConteudo();
			if(!caminho.isEmpty()) {
				conteudos.add(conteudoDao.findByPath(caminho));
			}
		}
		return conteudos;
	}
	

	/**
	 * Método de busca por id.
	 * 
	 * Ao realizar a execução do código o método buscará o arquivo identificado pelo ID passado por parâmetro 
	 * e retornará o conteúdo do arquivo.
	 * 
	 * @param id {@link Long}
	 * @return conteudo {@link Conteudo}
	 **/
	@RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
	public Conteudo buscarPorId(@PathVariable Long id) throws Exception{	
		
		Arquivo arquivo = arquivoService.EncontrarArqivo(id);
		Conteudo conteudo = conteudoDao.findByPath(arquivo.getConteudo());
		
		return conteudo;
	}
	
	/**
	 * Método de busca por nome.
	 * 
	 * Durante a execução do código, o arquivo será buscado pelo nome e será retornado o seu conteúdo.
	 * 
	 * @param nome {@link String}
	 * @return conteudo {@link Conteudo}
	 **/
	@RequestMapping(value = "/buscar/name={nome}", method = RequestMethod.GET)
	public Conteudo buscarPorNome(@PathVariable String nome) throws Exception{	
		
		Arquivo arquivo = arquivoService.EncontrarArqivoPeloNome(nome);
		Conteudo conteudo = conteudoDao.findByPath(arquivo.getConteudo());
		
		return conteudo;
	}
	
	/**
	 * Método de atualização de arquivo.
	 * 
	 * A execução desse bloco de código resultará na alteração do objeto identificado pelo ID passado por parâmetro.
	 * O método verificará se o nome e o arquivo foram alterados e serão feitas as devidas atribuições aos metadados relacionados.
	 * 
	 * @param id {@link Long}
	 * @param nome {@link String}
	 * @param file {@link MultipartFile}
	 * 
	 * @return the response entity
	 **/
	@RequestMapping(value = "/alterar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> alterar(@PathVariable Long id, @RequestParam String nome, @RequestParam MultipartFile file) throws IOException{
	
		Arquivo arquivo = arquivoService.EncontrarArqivo(id);
		Conteudo conteudo = new Conteudo();	
		String caminho = "";
		if(!file.isEmpty()) {
			conteudo.setBytes(file.getBytes());
			caminho = conteudoDao.save(conteudo, arquivo.getNome());	
			arquivo.setTamanho(file.getSize());
			arquivo.setUltimaModificacao(util.obterMomentoAtual());
			arquivo.setConteudo(caminho);
		}
		if(!nome.isEmpty()) {
			arquivo.setNome(nome);	
		}
		
		arquivoService.salvar(arquivo);
		return ResponseEntity.ok().build();
		
	}
	
	
	
}
