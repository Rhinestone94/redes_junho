package Proj;

public class Arco {
  
	public int origem;
	public int destino;
	public int custo;
	
	public Arco(int origem, int destino, int custo) {
		this.origem = origem;
		this.destino = destino;
		this.custo = custo;

	}

	
	// getters e setters
	public int getDestino() {
		return destino;
	}

	public int getOrigem() {
		return origem;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
}
