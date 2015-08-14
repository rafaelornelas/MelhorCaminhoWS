package controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import modelo.Mapa;
import modelo.RotaDirecoes;
import servico.PlanoDeRota;

@Path("/rota")
public class Controlador {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPing(){
		
		String output = "<root>WebService OK!</root>";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/qualcaminho")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaMelhorCaminho(@QueryParam("origem") String origem, 
			@QueryParam("destino") String destino, 
			@QueryParam("autonomia") Double autonomia, 
			@QueryParam("precoCombustivel") Double precoCombustivel) {
		validadorEntradas(origem, destino, autonomia, precoCombustivel);
		
		PlanoDeRota plan = new PlanoDeRota();
		RotaDirecoes direcoes = plan.getDirecoes(origem, destino, autonomia, precoCombustivel);

		return Response.status(200).entity(direcoes).build();
	}
	
    
    @GET
	@Path("/mapa")
	@Produces(MediaType.APPLICATION_JSON)
    public Response salvarMapa(@QueryParam("json") String json) {
		PlanoDeRota servico = new PlanoDeRota();
		//servico.gravar(mapa);

		Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
			
		try {
			json = "{\"name\":\"mkyong\", \"age\":\"29\"}";
			
			json = "{\"distance\": \"10\",\"origin\": \"A\",\"destination\": \"B\"}";		
		
			//convert JSON string to Map
			map = mapper.readValue(json,new TypeReference<HashMap<String,String>>(){});
				
			System.out.println(map);
			//servico.gravar(map);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
    	try {
			JSONObject jsonObj = new JSONObject(json);
			System.out.println(jsonObj.length());
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	return Response.status(Response.Status.CREATED).build();
	}
	
	private void validadorEntradas(String origem, String destino, Double autonomia, Double precoCombustivel) {
		if ((origem == null)||(destino == null)||(autonomia == null)||(precoCombustivel == null)) {
			throwException(400, "MOdelo deve ser preenchido: http://localhost:8080/MelhorCaminhoWS/rota/qualcaminho?origem=A&destino=D&autonomia=10&precoCombustivel=2.5");
		}
	}
	
	private void throwException(int codigoStatus, String mensagem) {
		java.util.Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("Codigo", codigoStatus);
		hash.put("Mensagem", mensagem);
				
		Gson gson = new Gson();
	    String json = gson.toJson(hash);
	    
		Response response = Response.status(codigoStatus)
				.entity(json)
				.build();
		
		throw new WebApplicationException(response);
	}
	


}
