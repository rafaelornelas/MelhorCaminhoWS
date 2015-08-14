package teste;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

import modelo.Mapa;
import modelo.Rota;

public class PlanoDeRotaTeste {
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

	@Test
	public void testeMelhorCaminho() {
		given().
			param("origin", "A").
	        param("destination", "B").
	        param("fuelEfficiency", "8.5").
	        param("fuelPrice", "2.5").
		expect().
			statusCode(200).
		when().
			get("/MelhorCaminhoWS/rota/qualcaminho");
	}
}