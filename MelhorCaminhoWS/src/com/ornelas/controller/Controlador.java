package com.ornelas.controller;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.ornelas.model.Direcoes;
import com.ornelas.model.Mapa;
import com.ornelas.persistence.PlanoDeRota;

/**
 * @author rornelas
 * Classe de controle responsavel pelos metodos acessados pelo Webservice
 *
 */
@Path("/rota")
public class Controlador {
	
	/**
	 * @return Metodo verifica se servico esta ativo.
	 */
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPing(){
		
		String output = "<root>WebService OK!</root>";
		return Response.status(200).entity(output).build();
	}

	/**
	 * @param origem
	 * @param destino
	 * @param autonomia
	 * @param precoCombustivel
	 * @return
	 * 
	 * Metodo Busca o menor caminho entre dois pontos retrno JSON
	 */
	@GET
	@Path("/qualcaminho")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaMelhorCaminho(
			@QueryParam("origem") String origem, 
			@QueryParam("destino") String destino, 
			@QueryParam("autonomia") Double autonomia, 
			@QueryParam("precoCombustivel") Double precoCombustivel) {
		
		validadorEntradas(origem, destino, autonomia, precoCombustivel);
		
		PlanoDeRota plan = new PlanoDeRota();
		Direcoes direcoes = plan.getDirecoes(origem, destino, autonomia, precoCombustivel);

		return Response.status(200).entity(direcoes).build();
	}
	
    
    /**
     * @param mapa
     * @return
     * Metodo para enviar via POSt uma String JSON para popular a base no noe4J
     */
    @POST
	@Path("/mapa")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response salvarMapa(Mapa mapa) {
		PlanoDeRota servico = new PlanoDeRota();
		servico.gravar(mapa);
    	return Response.status(Response.Status.CREATED).build();
	}
	
	/**
	 * @param origem
	 * @param destino
	 * @param autonomia
	 * @param precoCombustivel
	 * Metodo para fazer a validacao das entradas no Webservice
	 */
	private void validadorEntradas(String origem, String destino, Double autonomia, Double precoCombustivel) {
		if ((origem == null)||(destino == null)||(autonomia == null)||(precoCombustivel == null)) {
			throwException(400, "MOdelo deve ser preenchido: http://localhost:8080/MelhorCaminhoWS/rota/qualcaminho?origem=A&destino=D&autonomia=10&precoCombustivel=2.5");
		}
	}
	
	/**
	 * @param codigoStatus
	 * @param mensagem
	 * metodo apra tratamento das excptions
	 */
	private void throwException(int codigoStatus, String mensagem) {
		java.util.Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("Codigo", codigoStatus);
		hash.put("Mensagem", mensagem);
		Gson gson = new Gson();
	    String json = gson.toJson(hash);
		Response response = Response.status(codigoStatus).entity(json).build();
		throw new WebApplicationException(response);
	}
	


}
