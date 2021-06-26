package Proj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Proj.HeapB.No_pred;

public class Main {

	public static void main(String[] args) throws IOException {

		File ficheiro = new File("test.txt"); 

		if(!ficheiro.exists()){ 
			System.out.print("Ficheiro não encontrado FAIL...");
			System.out.printf("\n");     
		}



		BufferedReader buffer = new BufferedReader (new FileReader(ficheiro)); 
		BufferedReader buffer_2 = new BufferedReader(new FileReader(ficheiro)); 

		String[] elemento;
		String linha;


		ArrayList tabela_total = new ArrayList<>();  //vai adicionando ; a tabela vai sendo preenchida
		//ArrayList <Arco> tabela_total = new ArrayList<>(); 


		while ((linha = buffer.readLine()) != null) { // para saber o tamanho / nº de nós a utilizar no grafo / para construir o grafo
			elemento = linha.split(" ");  
			if (!tabela_total.contains(Integer.parseInt(elemento[0]))) // se a tabela total nao contem um certo numero da coluna dos nos origem, entao adiciona-o, se nao o tiver adiciona-o
				tabela_total.add(Integer.parseInt(elemento[0]));  // 0
			if(!tabela_total.contains(Integer.parseInt(elemento[1]))) 
				tabela_total.add(Integer.parseInt(elemento[1])); 
		}

		
		
		// Cria o grafo

		//Rede grafo = new Rede(7); //EXEMPLO para grafo Desconexo
		Rede grafo = new Rede(tabela_total.size());


		//lê linha a linha - elementos separados por um espaço
		while( (linha = buffer_2.readLine()) != null ){
			elemento = linha.split(" ");

			int no1 = Integer.parseInt(elemento[0]);
			int no2 = Integer.parseInt(elemento[1]);
			int custo = Integer.parseInt(elemento[2]);

			grafo.adicionaArco(no1, no2, custo);

		}

		buffer.close();
		buffer_2.close();


		System.out.println("Lista de Adjacencias: ");
		Rede.imprimeListaAdj(grafo);

		System.out.println("");
		System.out.println("");

		Rede.Existe_arco(grafo, 1, 4); // alterar consoante o pretendido
		
	    System.out.println("");
		System.out.println("=========================================================");
		
		
		// ========================== Árvore e caminho de menor custo ==========
		
		int noFonte = 2;
		int noDestino = 4;

		No_pred[] caminho;
		caminho = grafo.Dijkstra(noFonte);
		
		
		//debug Dijkstra
		System.out.println("");
		System.out.println("Árvore dos caminhos mais curtos desde o nó fonte: " + noFonte);
		System.out.println("Nó   Pred   CustoTotal" );
		for(int i = 0; i < grafo.arvore.size() ; i++) {
			//if (grafo.arvore.get(i).pred != 0) {
			
			System.out.println(grafo.arvore.get(i).no + "  -  " + grafo.arvore.get(i).pred + "  -  " + grafo.arvore.get(i).chave);		
		
		}	
		
		
		
		//Rede.caminhoMenorCust(caminho, noFonte, noDestino);
		//Rede.printCaminho();
		
	
		
		System.out.println("");
		
		
		
	

	} // FIM do Main


}

//  =========================  FIM Main =====================