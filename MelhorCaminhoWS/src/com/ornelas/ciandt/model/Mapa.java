package com.ornelas.ciandt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Mapa {
	private String nome;
	private List<Rota> rotas;

	public Mapa() {
	}

	public Mapa(String nome, List<Rota> rota) {
		this.nome = nome;
		this.rotas = rota;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}

	@Override
	public String toString() {
		return "Map [nome=" + nome + ", rota=" + rotas + "]";
	}
}
