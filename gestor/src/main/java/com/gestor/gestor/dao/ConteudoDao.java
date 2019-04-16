package com.gestor.gestor.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestor.gestor.model.Conteudo;
import com.gestor.gestor.util.MetadadoUtil;

@Component
public class ConteudoDao {
	@Autowired
	private MetadadoUtil meta;
	
	public String save(Conteudo conteudo, String nomeArquivo){
       
		try {
			
		
		File dir = new File("resources/arquivos");
		if (!dir.exists()) {
		dir.mkdirs();
		}
		
		File serverFile = new File(dir.getAbsolutePath() + File. separator + meta.obterMomentoAtual() + " - " + nomeArquivo);
		
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(conteudo.getBytes());
		stream.close();
		System.out.println("Arquivo armazenado em:" + serverFile.getAbsolutePath());
		System.out.println("Você fez o upload do arquivo " + nomeArquivo + " com sucesso");
		return serverFile.getAbsolutePath();
		} catch (Exception e) {
		System.out.println("Você falhou em carregar o arquivo " + nomeArquivo + " => " + e.getMessage());
		}
		return "";
        
    }
	
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
