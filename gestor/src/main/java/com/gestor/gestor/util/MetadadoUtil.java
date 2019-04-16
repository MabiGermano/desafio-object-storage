package com.gestor.gestor.util;

import java.util.Calendar;
import org.springframework.context.annotation.Configuration;

/**
 * @author M. Beatriz Germano
 * 
 * Classe responsável para tratamentos necessários nos metadados relacionados ao Arquivo e Conteudo. 
 **/
@Configuration
public class MetadadoUtil {

	/**
	 * Método obtem o momento atual (data e hora) e trasforma a formatação para string.
	 * @return momentoUpload {@link String}  
	 **/
	public String obterMomentoAtual() {
		Calendar c = Calendar.getInstance();
		int ano = c.get(Calendar.YEAR);
		int mes = c.get(Calendar.MONTH);
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int hora = c.get(Calendar.HOUR_OF_DAY);
		int minuto = c.get(Calendar.MINUTE);
		int segundo = c.get(Calendar.SECOND);
		String momentoUpload = ano +"-"+ (mes+1) +"-"+ dia + "-" + hora +"-"+ minuto +"-"+ segundo;
		return momentoUpload;
		}
}
