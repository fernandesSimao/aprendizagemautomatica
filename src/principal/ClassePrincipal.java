package principal;

import java.util.ArrayList;
import java.util.List;


public class ClassePrincipal implements IInterface {

	public enum MetodosSeleccao { Roleta, Torneio, TorneioComPopulacaoElite };
	public enum OperadoresRecombinacao { DoisCortes, UmCorteAleatorio, UmCorteBlocos3, UmCorteSemRepeticao, Uniforme };
	public enum OperadoresMutacao { Aleatoria,LINHA_TROCA_2_GENES };
	
	private long seed; 
	private int tamanhoPopulacao;
	private MetodosSeleccao metodosSeleccao;
	private Integer tamanhoTorneio;
	private Double percentagemPopulacaoElite;
	private OperadoresRecombinacao operadoresRecombinacao;
	private double probabilidadeRecombinacao;
	private OperadoresMutacao operadoresMutacao;
	private double probabilidadeMutacao;
	private int maximoGeracoes;
	
	List<String> ficheiros = null;
	
    public ClassePrincipal(
    		long _seed, 
    		int _tamanhoPopulacao,
			MetodosSeleccao _metodosSeleccao,
			Integer _tamanhoTorneio,
			Double _percentagemPopulacaoElite,
			OperadoresRecombinacao _operadoresRecombinacao,
			double _probabilidadeRecombinacao,
			OperadoresMutacao _operadoresMutacao,
			double _probabilidadeMutacao,
			int _maximoGeracoes) {
    	
    	this.seed = _seed; 
    	this.tamanhoPopulacao = _tamanhoPopulacao;
    	this.metodosSeleccao = _metodosSeleccao;
    	this.tamanhoTorneio = _tamanhoTorneio;
    	this.percentagemPopulacaoElite = _percentagemPopulacaoElite;
    	this.operadoresRecombinacao = _operadoresRecombinacao;
    	this.probabilidadeRecombinacao = _probabilidadeRecombinacao;
    	this.operadoresMutacao = _operadoresMutacao;
    	this.probabilidadeMutacao = _probabilidadeMutacao;
    	this.maximoGeracoes = _maximoGeracoes;
    	
    	ficheiros = //HandlerFicheiro.getPathsAbsolutasFicheiros();
				 HandlerFicheiro.getListaFicheiros();    	    
    }
    
    public void executa() {
    	
    	int[][] sudoku;
    	
    	for (String ficheiro : ficheiros) {
    		if (ficheiro.indexOf("!") != -1) {
				continue;
			}
    		sudoku = converteSudoku(ficheiro);
    		executaAlgoritmoGenetico(sudoku, ficheiro.substring(ficheiro.lastIndexOf("/")));//, 
		}    	
    }
    
    private void executaAlgoritmoGenetico(
    		int[][] sudoku, String nomeFx//,
//    		long seed,
//    		int tamanhoPopulacao,
//    		MetodosSeleccao metodosSeleccao,
//    		Integer tamanhoTorneio,
//    		Double percentagemPopulacaoElite,
//    		
//    		OperadoresRecombinacao operadoresRecombinacao,
//    		double probabilidadeRecombinacao,
//	
//    		OperadoresMutacao operadoresMutacao,
//    		double probabilidadeMutacao,
//    		int maximoGeracoes    		
    		) {
    	
  	  	algmatriz.MetodoSeleccao metodoSeleccao; 
  	  	switch (this.metodosSeleccao) {
		case Roleta:
			metodoSeleccao = new algmatriz.Roleta();
			break;
		case Torneio:
			metodoSeleccao = new algmatriz.TorneioSimples(tamanhoTorneio);
			break;
		case TorneioComPopulacaoElite:
			metodoSeleccao = new algmatriz.TorneioComPopElite(tamanhoTorneio, percentagemPopulacaoElite);
			break;
		default: throw new UnsupportedOperationException();
			
		}
  	  	
  	  	algmatriz.Recombinacao operadorRecombinacao;

  	  	switch(operadoresRecombinacao) {
	  	  	case DoisCortes:
	  	  		operadorRecombinacao = new algmatriz.RecombinacaoDoisCortes(probabilidadeRecombinacao);
	  	  		break;
	  	  	case UmCorteAleatorio:
	  	  		operadorRecombinacao = new algmatriz.RecombinacaoUmCorteAleatorio(probabilidadeRecombinacao);
	  	  		break;
	  	  	case UmCorteBlocos3:
	  	  		operadorRecombinacao = new algmatriz.RecombinacaoUmCorteBoloco3(probabilidadeRecombinacao);
	  	  		break;
	  	  	case UmCorteSemRepeticao:
	  	  		operadorRecombinacao = new algmatriz.RecombinacaoUmCorteSemRepeticao(probabilidadeRecombinacao);
	  	  		break;
	  	  	case Uniforme:
	  	  		operadorRecombinacao = new algmatriz.RecombinacaoUniforme(probabilidadeRecombinacao);
	  	  		break;
	  	  	default: throw new UnsupportedOperationException();
  	  	}
  	  	
  	  	algmatriz.Mutacao operadorMutacao;// = new algmatriz.MutacaoAleatoria(probabilidadeMutacao);
  	  	switch (operadoresMutacao) {
	  	  	case Aleatoria:
				operadorMutacao = new algmatriz.MutacaoAleatoria(probabilidadeMutacao);
				break;
	  	  	case LINHA_TROCA_2_GENES:
	  	  		operadorMutacao = new algmatriz.MutacaoLinhaDuasPosicoes(probabilidadeMutacao);
	  	  		break;
			default: throw new UnsupportedOperationException();
			
		}
    		  
  	  	algmatriz.AlgoritmoGenetico ag = new algmatriz.AlgoritmoGenetico(this, 
  	  			seed, 
  	  			sudoku, 
  	  			tamanhoPopulacao, 
  	  			maximoGeracoes, 
  	  			metodoSeleccao, 
  	  			operadorRecombinacao, 
  	  			operadorMutacao, 
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
	public void imprimeGreatSuccess(Boolean sucesso, String nomeFx, int geracao, double fitnessMelhorIndividuoRun, long seed, int tamanhoPopulacao, double probabilidadeRecombinacao, double probabilidadeMutacao) {
		
		String info = String.format("%b;%s;%d;%.2f;%d;%d;%s;%d;%.2f;%s;%.2f;%s;%.9f", 
				sucesso, 
				nomeFx.substring(1, nomeFx.length()), 
				geracao, 
				fitnessMelhorIndividuoRun,
				this.seed, 
				this.tamanhoPopulacao,
				this.metodosSeleccao.toString(),
				this.tamanhoTorneio,	
				this.percentagemPopulacaoElite,
				this.operadoresRecombinacao.toString(),
				this.probabilidadeRecombinacao, 
				this.operadoresMutacao.toString(),
				this.probabilidadeMutacao);
		HandlerFicheiro.escreveEmFx(info);
		System.out.println((!sucesso ? "Not so great" : "Great"  + " success para ") + nomeFx + " em " + geracao + " geracoes");
		
		
	}
}
