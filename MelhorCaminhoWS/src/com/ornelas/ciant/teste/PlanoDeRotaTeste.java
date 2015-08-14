package com.ornelas.ciant.teste;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.ornelas.ciandt.model.Mapa;
import com.ornelas.ciandt.model.Rota;

/**
 * @author rornelas
 *Classe para testar os metodos e o Webservice 
 */
public class PlanoDeRotaTeste {
	/**
	 * Teste de inclusao de rotas e mapas 
	 */
	@Test
	public void testeIncluindoMapaRotas() {
		List<Rota> routes = new ArrayList<Rota>();
		routes.add(new Rota("A", "B", 10));
		routes.add(new Rota("B", "D", 15));
		routes.add(new Rota("A", "C", 20));
		routes.add(new Rota("C", "D", 30));
		routes.add(new Rota("B", "E", 50));
		routes.add(new Rota("D", "E", 30));
		
		Mapa mapa = new Mapa("MG", routes);
		
		Gson gson = new Gson(); 
	    String json = gson.toJson(mapa);
	    
		given().contentType("application/json").body(json).
		expect().statusCode(201).when().post("/MelhorCaminhoWS/rota/mapa");
	}

	/**
	 * Teste de melhor caminho
	 */
	@Test
	public void testeMelhorCaminho() {
		given().
			param("origem", "A").
	        param("destino", "B").
	        param("autonomia", "8.5").
	        param("preco", "2.5").
		expect().
			statusCode(200).
		when().
			get("/MelhorCaminhoWS/rota/qualcaminho");
	}
}