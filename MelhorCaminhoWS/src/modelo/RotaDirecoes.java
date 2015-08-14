package modelo;

import java.util.List;

public class RotaDirecoes {
	private List<String> direcoes;
	private double custo;
	private double distancia;

	public RotaDirecoes() {
	}

	public RotaDirecoes(List<String> direcoes, double custo, double distancia) {
		this.direcoes = direcoes;
		this.custo = custo;
		this.distancia = distancia;
	}

	public List<String> getDirecoes() {
		return direcoes;
	}

	public void setDirecoes(List<String> direcoes) {
		this.direcoes = direcoes;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getDsitancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
}
