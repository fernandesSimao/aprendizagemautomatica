package principal;

import java.util.ArrayList;
import java.util.List;


public class ClassePrincipal implements IInterface {

    public static void main(String[] args) {
    	
    	List<String> ficheiros = HandlerFicheiro.getPathsAbsolutasFicheiros();
    	//listFilesForFolder(pastaSudokus);
    	int[][] sudoku;
    	for (String ficheiro : ficheiros) {
    		sudoku = converteSudoku(ficheiro);
    		executaAlgoritmoGenetico(sudoku, ficheiro.substring(ficheiro.lastIndexOf("/")));
		}
//    	HandlerFicheiro.escreveEmFx();
    }
    
    private static void executaAlgoritmoGenetico(int[][] sudoku, String nomeFx) {
    	
    	long seed = 1;
    	int tamanhoPopulacao = 200;  	  	
  	  	int maximoGeracoes = 70000;
  	  	
  	  	int tamanhoTorneio = 2;	  
  	  	algmatriz.MetodoSeleccao metodoSeleccao = 
			  //new algmatriz.Roleta();
			  //new algmatriz.Torneio(tamanhoTorneio);
  	  			new algmatriz.TorneioComPopElite();
  	  	double probabilidadeRecombinacao = 0.7;
  	  	algmatriz.Recombinacao operadorRecombinacao = 
  	  			new algmatriz.RecombinacaoUmCorteAleatorio(probabilidadeRecombinacao);
  	  	double probabilidadeMutacao = 0.03234567;
  	  	algmatriz.Mutacao operadorMutacao = new algmatriz.MutacaoAleatoria(probabilidadeMutacao);
    		  
  	  	algmatriz.AlgoritmoGenetico ag = new algmatriz.AlgoritmoGenetico(
  	  			new ClassePrincipal(), 
  	  			seed, 
  	  			sudoku,
  	  			tamanhoPopulacao,     		  
  	  			maximoGeracoes, 
  	  			metodoSeleccao, 
  	  			operadorRecombinacao, 
  	  			operadorMutacao,
  	  			//40,
  	  			nomeFx);
  	  	
  	  	new Thread(new Runnable() {
  	  		@Override
  	  		public void run() {
  	  			ag.executar();
  	  		}
  	  	}).start();
    }
    
    private static int[][] converteSudoku(String pathFx) {    	
    	
    	StringBuffer sudokuStringBuffer = HandlerFicheiro.abrirFicheiro(pathFx);
    	
    	int dimensao = 9;
    	int[][] matrixAux = new int[dimensao][dimensao];
    	List<Integer> listaInteiros = new ArrayList<>();
    	String aux;
    	
    	for (int i = 0; i < sudokuStringBuffer.toString().length(); i++) {
			if (sudokuStringBuffer.charAt(i) == ' ') {
				listaInteiros.add(0);
			}
			else if(!String.valueOf(sudokuStringBuffer.charAt(i)).matches(".")) {	//new line - update: nao funciona
				continue;
			}
			else {
				aux = sudokuStringBuffer.substring(i, i + 1);
				try {
					listaInteiros.add(Integer.parseInt(aux));
				} catch (Exception e) {
					//throw new RuntimeException();				//por causa dos new lines
				}
			}
		}
    	int k = 0;
    	for (int i = 0; i < dimensao; i++) {
    		for (int j = 0; j < dimensao; j++) {
    			matrixAux[i][j] = listaInteiros.get(k++);
			}			
		}
    	return matrixAux;
    }

	@Override
	public void imprimePuzzleAtual(String melhorIndividuoRun) {
		// Nao faz nada		
	}

	@Override
	public void imprimeInfoFitness(int geracao, double fitnessMelhorIndividuoGeracao,
			double fitnessMelhorIndividuoRun) {
		System.out.println(String.format("Geracao #%d | Melhor fitness: da geracao: %.2f / do run: %.2f", geracao, fitnessMelhorIndividuoGeracao, fitnessMelhorIndividuoRun));
	}

	@Override
	public void imprimeGreatSuccess(String nomeFx, int geracao, double fitnessMelhorIndividuoGeracao, double fitnessMelhorIndividuoRun, long seed, int tamanhoPopulacao, Integer tamanhoTorneio, double probabilidadeRecombinacao, double probabilidadeMutacao) {
		
		String info = String.format("%s;%d;%d;%d;%.3f;%d;%.3f", nomeFx, geracao, seed, tamanhoPopulacao, probabilidadeRecombinacao, tamanhoTorneio, probabilidadeMutacao);
		HandlerFicheiro.escreveEmFx(info);
		System.out.println("Great success para " + nomeFx + " em " + geracao + " geracoes");
		
		
	}
}
