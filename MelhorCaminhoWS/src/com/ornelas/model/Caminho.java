package com.ornelas.model;

import java.util.List;

public class Caminho {
	private double peso;
	private List<Long> caminho;
	
	public Caminho() {
	}

	public Caminho(double weight, List<Long> path) {
		this.peso = weight;
		this.caminho = path;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double weight) {
		this.peso = weight;
	}

	public List<Long> getCaminho() {
		return caminho;
	}

	public void setCaminho(List<Long> caminho) {
		this.caminho = caminho;
	}
}
