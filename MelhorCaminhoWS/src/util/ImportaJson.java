package util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Mapa;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author rornelas
 *Esta classe seria para fazer uma importacao com uma maior usabilidade, porem nao vou ter tempo de finalizar.
 */
public class ImportaJson {

	public static List<Mapa> converterJsonToListDescontoProdutoCondicao(String json) {    
	     Type listType = new TypeToken<List<Mapa>>(){}.getType();  
	     List<Mapa> lista = new Gson().fromJson(json, listType);    
	     return lista;    
	} 

	public static void main(String args[]) throws IOException{

		
		Gson gsonn = new Gson(); 
		try { BufferedReader brr = new BufferedReader(new FileReader("/Users/rornelas/Documents/mapa.txt")); 
		
		Mapa obj = gsonn.fromJson(brr, Mapa.class); 
		System.out.println(obj); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
			} 
		
		Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
			
		String json = ""; 

		try {
			json = "{\"distancia\": \"10\",\"origem\": \"A\",\"destino\": \"B\"}";		
		
			//Faz a conversao de um astring JSON para MAP
			map = mapper.readValue(json,new TypeReference<HashMap<String,String>>(){});
				
			System.out.println(map);

			//servico.gravar(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}



