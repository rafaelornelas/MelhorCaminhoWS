package servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.neo4j.graphdb.Relationship;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import com.google.gson.Gson;

import modelo.Mapa;
import modelo.Rota;
import modelo.RotaDirecoes;
import modelo.Caminho;
import util.*;

public class PlanoDeRota {
	private static String URI = "http://localhost:7474/db/data/";
	private static String NOME = "name";
	private static String DISTANCIA = "distance";
	
	private RestAPI api;
	
	public PlanoDeRota() {
		api = new RestAPIFacade(URI);
	}

	public void gravar(Mapa mapa) {
		for (String nome : criarNo(mapa)) {
			persisteNo(nome);
		}

		for (Rota rota : mapa.getRotas()) {
			gerarRelacionamentoOrigemDestino(rota.getOrigem(), rota.getDestino(), rota.getDistancia());
		}
	}
	
	public RotaDirecoes getDirecoes(String origem, String destino, double autonomia, double preco) {
		RestNode origemNo = buscarNo(origem);
		RestNode destinoNo = buscarNo(destino);
		
		if ((null == origemNo)||(null == destinoNo)){
			throwException(404, "Rota Nao Encontrada");
		}
		
		Dijkstra dijkstra = new Dijkstra();
		Caminho caminho = dijkstra.findSinglePath(origemNo, destinoNo);
		RotaDirecoes direcoes = criaDirecoes(caminho, autonomia, preco);
		
		return direcoes;
	}
	
	private Set<String> criarNo(Mapa mapa) {
		Set<String> nodo = new HashSet<String>();
		
		for (Rota rota : mapa.getRotas()) {
			nodo.add(rota.getOrigem());
			nodo.add(rota.getDestino());
		}
		
		return nodo;
	}
	
	private RestNode criaNo(String name) {
		java.util.Map<String, Object> props = new HashMap<String, Object>();
		props.put(NOME, name);
		
		return api.createNode(props);
	}
	
	private void gerarRelacionamentoOrigemDestino(String origin, String destination, double distance) {
		RestNode originNode = persisteNo(origin);
		RestNode destinationNode = persisteNo(destination);
		
		java.util.Map<String, Object> props = new HashMap<String, Object>();
		props.put(DISTANCIA, distance);
		
		Relationship r = originNode.createRelationshipTo(destinationNode, Tipo.LEADS_TO);
		r.setProperty(DISTANCIA, distance);
	}
	
	private RestNode persisteNo(String name) {
		RestNode node = buscarNo(name);
        
        if (node == null) {
            node = criaNo(name);
        }
        
        return node;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private RestNode buscarNo(String name){
		
		try {
			String query = String.format("MATCH (node {name: '%s'}) RETURN node", name);
			
			QueryEngine engine = new RestCypherQueryEngine(api);
			QueryResult<java.util.Map<String,Object>> result = engine.query(query, Collections.EMPTY_MAP);
			
			Iterator<java.util.Map<String, Object>> iterator = result.iterator();
			if (iterator.hasNext()) {  
				java.util.Map<String,Object> row = iterator.next();
				return (RestNode) row.get("node");
			}
			
		} catch (Exception e) {
			throwException(500, "No nao encontrado");
		}
		
		return null;
	}
	
	private RotaDirecoes criaDirecoes(Caminho caminho, double autonomia, double preco) {
		double distancia = caminho.getPeso();
		double precoCalc = (preco * distancia) / autonomia;
		
		List<String> direcao = new ArrayList<String>();
		
		for (Long nodeId : caminho.getCaminho()) {
			RestNode node = api.getNodeById(nodeId);
			direcao.add(node.getProperty(NOME).toString());
		}
		
		RotaDirecoes direcoes = new RotaDirecoes(direcao, precoCalc, distancia);
		
		return direcoes;
	}
	
	private void throwException(int statusCode, String message) {
		java.util.Map<String, Object> result = new HashMap<String, Object>();
		result.put("Codigo", statusCode);
		result.put("Mensagem", message);
				
		Gson gson = new Gson();
	    String json = gson.toJson(result);
	    
		Response response = Response.status(statusCode)
				.entity(json)
				.build();
		
		throw new WebApplicationException(response);
	}
}