package util;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type; 

import modelo.Mapa;

public class ImportaJson {

	public static List<Mapa> converterJsonToListDescontoProdutoCondicao(String json) {    
	     Type listType = new TypeToken<List<Mapa>>(){}.getType();  
	     List<Mapa> lista = new Gson().fromJson(json, listType);    
	     return lista;    
	} 

	public static void main(String args[]) throws IOException{
		/*
		Gson gson = new Gson();  
		System.out.println("Lendo um arquivo JSON");  
		System.out.println("----------------------------");  
	     
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/Users/rornelas/Documents/mapa.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	     
		Mapa mapObj = gson.fromJson(br, Mapa.class); 
		
		//converterJsonToListDescontoProdutoCondicao(br.toString());
		
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}
		System.out.println("Contents of file:");
		System.out.println(stringBuffer.toString());
		*/
		
		Gson gsonn = new Gson(); 
		try { BufferedReader brr = new BufferedReader(new FileReader("/Users/rornelas/Documents/mapa.txt")); 
		//Converte String JSON para objeto Java 
		
		Mapa obj = gsonn.fromJson(brr, Mapa.class); 
		System.out.println(obj); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
			} 
		}

		
		
		
	}



