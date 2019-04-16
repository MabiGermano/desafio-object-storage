package com.gestor.gestor.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestor.gestor.model.Conteudo;
import com.gestor.gestor.util.MetadadoUtil;
/**
 * @author M. Beatri Germano
 * 
 * Classe responsável pela comunicação do objeto Conteudo com o banco de dados 
 **/
@Component
public class ConteudoDao {
	@Autowired
	private MetadadoUtil meta;
	
	/**
	 * Método para salvar o conteudo.
	 * Durante a execução do código ele criará um diretório dentro do projeto (caso não exista),
	 * para armazenar os arquivos e retornará o caminho absoluto do arquivo.
	 * 
	 * @param conteudo {@link Conteudo}
	 * @param nomeArquivo {@link String}
	 * @return caminhoAbsoluto {@link String}
	 **/
	public String save(Conteudo conteudo, String nomeArquivo){
		
        String caminhoAbsoluto = "";
        try {
			File dir = new File("resources/arquivos");
			if (!dir.exists()) {
			dir.mkdirs();
			}
		
			File serverFile = new File(dir.getAbsolutePath() + File. separator + meta.obterMomentoAtual() + " - " + nomeArquivo);
			
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(conteudo.getBytes());
			stream.close();
			caminhoAbsoluto = serverFile.getAbsolutePath();
		}catch (Exception e) {
			System.out.println("Você falhou em carregar o arquivo " + nomeArquivo + " => " + e.getMessage());
		}
        
		return caminhoAbsoluto;   
    }
	/**
	 * Método de busca do conteudo.
	 * A partir do caminho onde está armazenado o conteudo será possível identificar e buscar os arquivos
	 * e assim retornar o conteudo encontrado. 
	 * 
	 * @param caminho {@link String}
	 * @return conteudo {@link Conteudo}
	 **/
	public Conteudo findByPath(String caminho) throws Exception{
		File arquivo = new File(caminho);
		FileInputStream lerArquivo = new FileInputStream(arquivo);
		byte[] bytes = new byte[(int)arquivo.length()];
		lerArquivo.read(bytes);
		lerArquivo.close();
		Conteudo conteudo = new Conteudo();
		conteudo.setBytes(bytes);
		return conteudo;
	} 

}
