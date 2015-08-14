package modelo;

public class Rota {
	private String origem;
	private String destino;
	private double distancia;

	public Rota() {
	}

	public Rota(String origem, String destino, double distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distance) {
		this.distancia = distance;
	}

	@Override
	public String toString() {
		return "Rota [destino=" + origem + ", destino=" + destino
				+ ", distancia=" + distancia + "]";
	}
}
