package Proj;

import java.util.LinkedList;
import java.util.ArrayList;

import Proj.HeapB.No_pred;
import Proj.Arco;


public class Rede {

	private int numNos;  // n� de n�s na rede
	LinkedList<Arco>[] adjList;  //Lista de adjac�ncias
	
	// solu��o do dijkstra
	ArrayList<No_pred> arvore = new ArrayList<>(numNos); // guarda na arraylist

	
	//para guardar o caminho de menor custo desde o n� fonte at� ao n� destino
	ArrayList<Arco> caminho; // variavel, estrutura, onde vai guardar o caminho desde um certo n� ao outro
	
	private int custo_total; // custo total do caminho
	
	
	
	private int INF = Integer.MAX_VALUE;
	No_pred d [] = new No_pred[numNos];
	
	
	public ArrayList<Arco> getCaminho() {
		return caminho;
	}
	
	public void setCaminho(ArrayList<Arco> caminho) {
		this.caminho = caminho;
	}
	
	public void setCusto_total(int custo_t) {
		this.custo_total = custo_t;
	}
	
	public int getCusto_total() {
		return custo_total;
	}
	
	
	//construtor da classe Rede
	public Rede(int v) {
		numNos = v +1; 
		adjList = new LinkedList[numNos];

		
		
		//Inicializa�ao da lista de adjacencias para todos os nos
		for (int i = 0; i < numNos ; i++) {
			adjList[i] = new LinkedList<>();
		}

	}


	public Rede() {
		// TODO Auto-generated constructor stub
	}

	
	//========================================== DIJKSTRA ====================================
	// devolve o conjunto dos Predecessores de cada n� � origem - que permite a constru��o da arvore dos caminhos de menor custo
	public No_pred[] Dijkstra(int no_fonte) {
		
		// 1 - cria heap H
		HeapB h = new HeapB(); // create_heap(H)

		No_pred d [] = new No_pred[numNos];
		
		// 2 - muda o valor da chave dos j's E N    		
		for (int j = 1; j < numNos ; j++) {
			d[j] = new No_pred();
			d[j].no = j;
			d[j].chave = INF;
		}
		
		
		//3 - Muda o valor da chave do n� fonte | d(s) = 0 ; pred(s) = 0
		d[no_fonte].chave = 0;
		d[no_fonte].pred = 0;

					
		// 4 - insere primeiro elemento na heap ; chave =0 e pred = 0
		h.insert(d[no_fonte]); // insert(s,H)

		// 5 - Enquanto a heap nao est� vazia
		while(!h.heap.isEmpty()) {

			// 6 - guarda na vari�vel no_min o n� de min chave ;   
			No_pred no_min_chave = h.find_min();  // guarda n� de minima chave
			h.delete_min();
			
			arvore.add(no_min_chave); 
			
			
			// tem de ser criada esta vari�vel para guardar o n�
			int ii = no_min_chave.no; // procura n� i pelo n� 

			// 7 - percorrer lista de adjacencias do n� i, A(i) arcos incidentes no n� i       	  

			for(int i = 0; i < adjList[no_min_chave.no].size(); i++) {
				Arco a = adjList[no_min_chave.no].get(i);
				int j = a.destino; 

				int valor = d[no_min_chave.no].chave + a.custo;

				if(d[j].chave > valor) {
					if(d[j].chave == INF) {
						d[j].chave = valor;
						d[j].pred = no_min_chave.no;
						h.insert(d[j]);
					}
					else {

						d[j].chave = valor;
						d[j].pred = ii;
						h.decrementa_chave(valor, d[j]);
					}
				}
			}	
		} 	
		/*
		for (int i = 1; i < numNos ; i++) {
			No_pred tree = new No_pred();
			
			tree.pred = d[i].pred;
			tree.no= d[i].no;
			tree.chave = d[i].chave;
			
			arvore.add(tree); // coloca na �rvore 
		} 
		*/
		
		System.out.println();
		System.out.println("");
		return d; // devolve o caminho de cada n� � origem e o custo total do caminho de cada n� � origem	
	}

	//================================================= FIM Dijkstra ==============


	/* 
	 * O m�todo tem que usar a vari�vel/estrutura 'arvore' que est� definida na classe Rede (onde � guardada a solu��o
	 * do dijkstra). � nesta "arvore" que se encontra o caminho. 
	 * 
	 */
	
	
	// ================================ M�todo caminho de menor custo 1 ==========
	public void caminhoMenorCust(No_pred [] v, int origem, int destino) {
		
		caminho = new ArrayList<Arco>(); 
		int custo_total = v[destino].chave;
		
		/*for (int i = 1; i < numNos ; i++) {
			No_pred nos = new No_pred();
			
			nos.pred = d[i].pred;
			nos.no = d[i].no;
			nos.chave = d[i].chave;
			
			caminho.add(nos); // coloca no caminho
		} */
		
		while(destino != origem) {
			No_pred noFinal = v[destino]; // cria n� que est� na ultima pos do vetor
			Arco arco = new Arco(noFinal.pred, noFinal.no, noFinal.chave); // cria novo arco enquanto destino != origem
			destino = noFinal.pred;
			caminho.add(0, arco);
			
		}
		
		Rede path = new Rede(); // caminho
		path.setCaminho(caminho); // mete o conjunto 'arcos' para fazer o caminho
		System.out.println("");
		
	}
	
	
	
	
	
	// ================================ M�todo caminho menor custo 2 ==========

	/*public static void caminhoMenorCusto2(No_pred[] dijk, int origem, int destino) {

		ArrayList<Arco> caminho = new ArrayList<Arco>();  
		//int custo_total = dijk[destino].chave;  

		while (destino != origem) {
			No_pred noFinal = dijk[destino]; // cria noFinal que tem na posicao no_destino 
			Arco arco = new Arco(noFinal.pred, noFinal.no, noFinal.chave); // cria novo enquanto destino for diferente origem
			destino = noFinal.pred; // o que era destino passa a ser o pred 
			caminho.add(0, arco); // adiciona na pos 0 o arco

		}
		
		
		Rede path = new Rede(); // caminho
		path.setCaminho(caminho); // mete o conjunto 'arcos' para fazer o caminho  
		
		//System.out.print(origem + " -> "+ caminho.));

	}*/

	
	
	// print do caminho menor custo
	/*public static void printCaminho(Rede path) {

		String resultado = "" ;
		Arco ultimo = null;

		ArrayList<Arco> nos = path.getCaminho();

		for (Arco no : nos) { // para cada n� , que est� dentro de 'nos' , ou seja, os arcos todos
			resultado = resultado + no.getOrigem() + " -> ";
			ultimo = no;
		}

		resultado = resultado + ultimo.getDestino();

		System.out.println("Caminho menor custo desde o n� " + nos.get(0).getOrigem() + " at� ao n� " + ultimo.getDestino() +":" );
		System.out.println(resultado);


		System.out.println("");
	}*/
	// ================================== FIM do M�todo para mostrar o caminho =================
	
	

	// adiciona arco
	public void adicionaArco(int n1, int n2, int c) {
		Arco arco = new Arco(n1, n2, c);
		adjList[n1].add(arco);

		arco = new Arco(n2, n1, c); // adiciona arco - grafo n�o dirijido
		adjList[n2].add(arco);
	}


	// m�todo que imprime a Lista de Adjacencias do Grafo
	public static void imprimeListaAdj(Rede graph) {
		for (int i = 1; i < graph.numNos ; i++) {
			System.out.println("");
			System.out.println(i + ":");


			for (Arco e : graph.adjList[i]) {
				System.out.print("(" + e.origem + "," + e.destino + "," + e.custo + ")" );

			} System.out.println("");
		} 
	}

	
	// M�todo para saber se um dado arco no grafo existe
	public static void Existe_arco(Rede graph, int noOrigem, int noDestino){

		int contador = 0;
		for(int v = 1; v < graph.numNos; v++){

			for (Arco e : graph.adjList[v]){ 
				if(e.origem == noOrigem & e.destino == noDestino)
				{
					contador = 1;                	
					break;
				}
			}
		}
		if (contador == 1) {

			System.out.print("O arco (" + noOrigem + "," + noDestino + ") existe ...");	            	
		}
		else {       	
			System.out.print("O arco (" + noOrigem + "," + noDestino + ") n�o existe ...");
		}

	}	


} // fim da classe Rede
	    
	    
	        