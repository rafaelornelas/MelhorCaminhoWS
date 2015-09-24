package com.ornelas.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ornelas.model.Mapa;
import com.ornelas.persistence.PlanoDeRota;

/**
 * @author rornelas
 * Classe responsavel por importar a malha de dados via JSON
 */
public class ImportaJson {

	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException{
		
		Mapa mapa = null;
		File jsonFileMapa = new File("mapa.json");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapa = mapper.readValue(jsonFileMapa, Mapa.class);
		System.out.println(mapa.getNome());
		
		PlanoDeRota plano = new PlanoDeRota();
		plano.gravar(mapa);
		
	}
}



