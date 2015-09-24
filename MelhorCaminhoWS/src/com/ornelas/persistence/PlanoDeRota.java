package com.ornelas.persistence;

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
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import com.google.gson.Gson;
import com.ornelas.model.Caminho;
import com.ornelas.model.Direcoes;
import com.ornelas.model.Mapa;
import com.ornelas.model.Rota;
import com.ornelas.util.Dijkstra;

/**
 * 
 * @author rornelas
 * Nesta classe os metodos persisten no noe4j
 * 
 * 
 */

public class PlanoDeRota {
	private static String URI = "http://localhost:7474/db";
	private static String NOME = "name";
	private static String DISTANCIA = "distancia";
	
	private RestAPI api;
	
	public PlanoDeRota() {
		api = new RestAPIFacade(URI);
	}

	/**
	 * 
	 * @param mapa
	 * Metodo responsavel por persistir na base o mapa informado na chamado do webservice
	 *
	 */
	public void gravar(Mapa mapa) {
		for (String nome : criarNo(mapa)) {
			persisteNo(nome);
		}

		for (Rota rota : mapa.getRotas()) {
			gerarRelacionamentoOrigemDestino(rota.getOrigem(), rota.getDestino(), rota.getDistancia());
		}
	}
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @param autonomia
	 * @param preco
	 * @return
	 * 
	 * Metodo faz a busca da rota nos nos
	 */
	public Direcoes getDirecoes(String origem, String destino, double autonomia, double preco) {
		RestNode origemNo = buscarNo(origem);
		RestNode destinoNo = buscarNo(destino);
		
		if ((null == origemNo)||(null == destinoNo)){
			throwException(404, "Rota Nao Encontrada");
		}
		
		Dijkstra dijkstra = new Dijkstra();
		Caminho caminho = dijkstra.findSinglePath(origemNo, destinoNo);
		Direcoes direcoes = criaDirecoes(caminho, autonomia, preco);
		
		return direcoes;
	}
	
	/**
	 * 
	 * @param mapa
	 * @return
	 * metodo que persiste o no 
	 *
	 */
	private Set<String> criarNo(Mapa mapa) {
		Set<String> nodo = new HashSet<String>();
		
		for (Rota rota : mapa.getRotas()) {
			nodo.add(rota.getOrigem());
			nodo.add(rota.getDestino());
		}
		
		return nodo;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * Metodo apra criar o Nodo
	 *
	 */
	private RestNode criaNo(String nome) {
		java.util.Map<String, Object> props = new HashMap<String, Object>();
		props.put(NOME, nome);
		
		return api.createNode(props);
	}
	
	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param distance
	 * Metodo faz o relacionamento entre o o no de oriegem e de destino
	 */
	private void gerarRelacionamentoOrigemDestino(String origem, String destino, double distancia) {
		RestNode originNode = persisteNo(origem);
		RestNode destinationNode = persisteNo(destino);
		
		java.util.Map<String, Object> props = new HashMap<String, Object>();
		props.put(DISTANCIA, distancia);
		
		Relationship r = originNode.createRelationshipTo(destinationNode, Tipo.LEADS_TO);
		r.setProperty(DISTANCIA, distancia);
	}
	
	/**
	 * @param name
	 * @return
	 * Metodo para persistir o no
	 */
	private RestNode persisteNo(String nome) {
		RestNode no = buscarNo(nome);
        
        if (no == null) {
            no = criaNo(nome);
        }
        
        return no;
	}
	
	/**
	 * @param name
	 * @return
	 * Metodo faz a consulta no noe4j pelo nome do nodo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private RestNode buscarNo(String noome){
		
		try {
			String query = String.format("MATCH (node {nome: '%s'}) RETURN node", noome);
			
			QueryEngine engine = new RestCypherQueryEngine(api);
			QueryResult<java.util.Map<String,Object>> result = engine.query(query, Collections.EMPTY_MAP);
			
			Iterator<java.util.Map<String, Object>> iterator = result.iterator();
			if (iterator.hasNext()) {  
				java.util.Map<String,Object> row = iterator.next();
				return (RestNode) row.get("node");
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
	/**
	 * @param caminho
	 * @param autonomia
	 * @param preco
	 * @return
	 * 
	 * metodo reponsavel por calcular o caminho e custos 
	 */
	private Direcoes criaDirecoes(Caminho caminho, double autonomia, double preco) {
		double distancia = caminho.getPeso();
		double precoCalc = (preco * distancia) / autonomia;
		
		List<String> direcao = new ArrayList<String>();
		
		for (Long nodeId : caminho.getCaminho()) {
			RestNode node = api.getNodeById(nodeId);
			direcao.add(node.getProperty(NOME).toString());
		}
		
		Direcoes direcoes = new Direcoes(direcao, precoCalc, distancia);
		
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

enum Tipo implements RelationshipType {
	LEADS_TO
}
