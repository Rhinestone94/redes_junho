package Proj;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Proj.HeapB.No_pred;
import Proj.Arco;


public class Rede {

	int numNos;  // nº de nós na rede
	LinkedList<Arco>[] adjList;  //Lista de adjacências
	
	// solução do dijkstra
	ArrayList<No_pred> arvore = new ArrayList<>(numNos); // guarda na arraylist

	
	//para guardar o caminho de menor custo desde o nó fonte até ao nó destino
	ArrayList<Arco> caminho; // variavel, estrutura, onde vai guardar o caminho desde um certo nó ao outro
	
	
	// para o Prim
	ArrayList <No_pred> Tree;
	
	// para o 2Edge
	ArrayList <No_pred> M_Tree; // links cujas capacidades de backup estão definidas
	

	
	private int INF = Integer.MAX_VALUE;
		
	
	public void setCaminho(ArrayList<Arco> caminho) {
		this.caminho = caminho;
	}
	
	
	//construtor da classe Rede
	public Rede(int v) {
		numNos = v +1; 
		adjList = new LinkedList[numNos];
		
		

		//Inicializaçao da lista de adjacencias para todos os nos
		for (int i = 0; i < numNos ; i++) {
			adjList[i] = new LinkedList<>();
		}

	}


	
	//========================================== DIJKSTRA ====================================
	// devolve o conjunto dos Predecessores de cada nó à origem - que permite a construção da arvore dos caminhos de menor custo
	public void Dijkstra(int no_fonte) {
		
		// 1 - cria heap H
		HeapB h = new HeapB(); // create_heap(H)

		No_pred d [] = new No_pred[numNos];
		
		// 2 - muda o valor da chave dos j's E N    		
		for (int j = 1; j < numNos ; j++) {
			d[j] = new No_pred();
			d[j].no = j;
			d[j].chave = INF;
		}
		
		
		//3 - Muda o valor da chave do nó fonte | d(s) = 0 ; pred(s) = 0
		d[no_fonte].chave = 0;
		d[no_fonte].pred = 0;

					
		// 4 - insere primeiro elemento na heap ; chave =0 e pred = 0
		h.insert(d[no_fonte]); // insert(s,H)

		// 5 - Enquanto a heap nao está vazia
		while(!h.heap.isEmpty()) {

			// 6 - guarda na variável no_min o nó de min chave ;   
			No_pred no_min_chave = h.find_min();  // guarda nó de minima chave
			h.delete_min();
			
			arvore.add(no_min_chave); 
			

			// 7 - percorrer lista de adjacencias do nó i, A(i) arcos incidentes no nó i       	  

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
						d[j].pred = no_min_chave.no;
						h.decrementa_chave(valor, d[j]);
					}
				}
			}	
		} 	
		
		
		System.out.println("");
		// devolve o caminho de cada nó À origem e o custo total do caminho de cada nó à origem	
	}

	//================================================= FIM Dijkstra ==============

	
	
	// ================================ Método caminho de menor custo ==========
	public void caminhoMenorCusto(int origem, int destino) {
		
		caminho = new ArrayList<Arco>(); // inicializa caminho
		
		for(int i = 0; destino != origem && destino > 0; i++) { 
			
			int aux = destino; //variavel aux para poder ser utilizada no filtro
			No_pred arcoFinal = this.arvore.stream().filter(a -> a.no == aux).findFirst().get();
			
			
			Arco arco = new Arco(arcoFinal.pred, arcoFinal.no, arcoFinal.chave);
			destino = arcoFinal.pred;
			caminho.add(0, arco); // coloca arco no caminho
		
		}
		
		//Collections.reverse(caminho); //inverte a ordem dos elementos (caso meta add(1,arco))
		this.setCaminho(caminho); // atualiza e mete na variável caminho da Rede
		
	}
	
	
	// ================================== FIM do Método caminho menor custo =================
	

	// ====================================== Algoritmo de Prim =============================
	
	// manipulação dos valores dos custos/capacidade
	
	public void Prim(int no_fonte) {

		HeapB h = new HeapB(); // create_heap(H)
		
		No_pred no [] = new No_pred[numNos];

		for (int j = 1; j < numNos; j++) {  
			no[j] = new No_pred();
			no[j].no = j;
			no[j].chave = INF; // d[j] = C + 1 ou seja,  INF
					
			h.insert(no[j]); // for each j E N  -> insere na heap

		}
			
		// d(1) = 0 ; pred(1) = 0 , ou seja, o nó fonte
		
		no[no_fonte].chave = 0;

		h.insert(no[no_fonte]); // insere na heap o nó de minima chave

		Tree = new ArrayList<>(); // inicializa T

		while(Tree.size() < numNos) { // enquanto T é inferior ao nº de nós
			No_pred no_min_chave = h.find_min();
			h.delete_min();

			Tree.add(no_min_chave); 
			
			for(int i = 0; i < adjList[no_min_chave.no].size(); i++) {
				Arco arco = adjList[no_min_chave.no].get(i);
				int j = arco.destino; 

				if(h.heap.contains(no[j])) { // j E H
					
					if(no[j].chave > arco.custo) { 
						no[j].chave = arco.custo; // d(j) = cij
						no[j].pred = no_min_chave.no; // pred(j) = i
						h.decrementa_chave(arco.custo, no[j]);
					}
				}

			}

		}

	}
	// ====================================== FIM Prim =============================
	
	
	
	
	
	// ==================================== 2-EDGE =============================
	
	
	/*
	public void prim2edge(ArrayList<No_pred> Tree, Rede graph) { //necessário passar como parâmetro a árvore e a rede/grafo para comparar

		M_Tree = new ArrayList<>(); // M inicialmente vazia

		
		Arco arco = new Arco;

		for (int i = 1; i < graph.numNos ; i++) {
			for (int j = 1; i < Tree.size(); j++) {

				if ((graph.adjList[i].get(i).origem == graph.Tree.get(j).no && graph.adjList[i].get(i) .get(j).pred == Tree.get(j).pred ) || (rede .get(j).no == Tree.get(j).pred && rede .get(j).pred == Tree.get(j).no)) {

					if(se v1 e v2 são nós raiz) {
						Tree.add(arco); // F = F + {ei}

						// é formado um ciclo C , que inclui os arcos da árvore + o arco i

						for(para cada arco que pertence ao ciclo C  MENOS os que estão em M, inicialmente a 0) {
							
							workingC = chave / 2; // w = u/2
							protectionC = chave - workingC; // p = u - w
							M_Tree.add(arco); // M = M + e

						}
					}
					else
					{
						workingC = chave;
						protectionC = 0;
					}
				}


			}
		}

	}
	*/
	
	

		
	
	// ===================================== FIM v2 ================================
	
	
	
	// adiciona arco
	public void adicionaArco(int n1, int n2, int c) {
		Arco arco = new Arco(n1, n2, c);
		adjList[n1].add(arco);

		arco = new Arco(n2, n1, c); // adiciona arco - grafo não dirijido
		adjList[n2].add(arco);
	}


	// método que imprime a Lista de Adjacencias do Grafo
	public static void imprimeListaAdj(Rede graph) {
		for (int i = 1; i < graph.numNos ; i++) {
			System.out.println("");
			System.out.println(i + ":");


			for (Arco e : graph.adjList[i]) {
				System.out.print("(" + e.origem + "," + e.destino + "," + e.custo + ")" );

			} System.out.println("");
		} 
	}

	
	// Método para saber se um dado arco no grafo existe
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
			System.out.print("O arco (" + noOrigem + "," + noDestino + ") não existe ...");
		}

	}	


} // fim da classe Rede
	    
	        