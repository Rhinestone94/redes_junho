package Proj;


import java.util.ArrayList;
import java.util.List;

public class CaminhoSP { // no fundo � um conjunto de n�s

	// inicializa��o
	private ArrayList<Arco> arcos; // guarda o caminho menor custo
	private int custo_total; // custo total do caminho
	
	
	// m�todos get e set
	public ArrayList<Arco> getArcos() {
		return arcos;
	}
	
	public void setArcos(ArrayList<Arco> arcos) {
		this.arcos = arcos;
	}
	
	
	public void setCusto(int custo_total) {
		this.custo_total = custo_total;
	}
	
	public int getCusto() {
		return custo_total;
	}
	
}
