package com.gestor.gestor.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestor.gestor.model.Arquivo;
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
	
	public List<Conteudo> find(List<Arquivo> arquivos) throws Exception{
		List<Conteudo> conteudos = new ArrayList<>();
		for (Arquivo arquivo : arquivos) {
			String caminho = arquivo.getConteudo();
			File file = new File(caminho);
			FileInputStream lerConteudo = new FileInputStream(file);
			Conteudo c = new Conteudo();
			c.setBytes(lerConteudo.readAllBytes());
			lerConteudo.close();
			conteudos.add(c);
		}
		return conteudos;
	} 

}
