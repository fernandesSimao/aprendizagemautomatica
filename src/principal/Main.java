package principal;

import principal.ClassePrincipal.MetodosSeleccao;
import principal.ClassePrincipal.OperadoresMutacao;
import principal.ClassePrincipal.OperadoresRecombinacao;

public class Main {

	public static void main(String[] args) {
		long seed = 0;
    	int tamanhoPopulacao = 0;    	
    	
    	
    	MetodosSeleccao metodoSeleccao = null;
    	OperadoresRecombinacao operadorRecombinacao = null;
    	OperadoresMutacao operadorMutacao = null;
    	
    	Integer tamanhoTorneio = null;
    	Double percentagemPopulacaoElite = null;
    	
    	
    	double probabilidadeRecombinacao = 0;
    	
    	
    	double probabilidadeMutacao = 0;
    	
    	int maximoGeracoes = 0;
    	
    	for (int i = 0; i < args.length; i++) {
			if (i % 2 != 0) {
				continue;
			}
			else {
				try {
					switch (args[i]) {
					case "-seed": 
					case "-s": {
						seed = Long.parseLong(args[i + 1]);
						break;
					}
					case "-tamanhoPopulacao": 
					case "-tp": {
						tamanhoPopulacao = Integer.parseInt(args[i + 1]);
						break;
					}
					//-->
					case "-metodoSeleccao": 
					case "-ms": {
						switch (args[i + 1]) {
						case "roleta":
						case "r":
							metodoSeleccao = MetodosSeleccao.Roleta;
							break;
						case "torneio":
						case "t":
							metodoSeleccao = MetodosSeleccao.Torneio;
							break;
						case "torneioComPopulacaoElite":
						case "tcpe":
							metodoSeleccao = MetodosSeleccao.TorneioComPopulacaoElite;
							break;						
						default: throw new UnsupportedOperationException();
						}
						break;
					}
					case "-tamanhoTorneio": 
					case "-tt": {
						tamanhoTorneio = Integer.parseInt(args[i + 1]);
						break;
					}
					case "-percentagemPopulacaoEliteTorneio": 
					case "-ppet": {
						percentagemPopulacaoElite = Double.parseDouble(args[i + 1]);
						break;
					}
					//-->
					
					case "-operadorRecombinacao":
					case "-or": {
						switch (args[i + 1]) {
						case "doisCortes":
						case "dc":
							operadorRecombinacao = OperadoresRecombinacao.DoisCortes;
							break;
						case "umCorteAleatorio":
						case "uca":
							operadorRecombinacao = OperadoresRecombinacao.UmCorteAleatorio;
							break;
						case "umCorteBlocos3":
						case "ucb3":
							operadorRecombinacao = OperadoresRecombinacao.UmCorteBlocos3;
							break;
						case "umcorteSemRepeticao":
						case "ucsr":
							operadorRecombinacao = OperadoresRecombinacao.UmCorteSemRepeticao;
							break;
						case "uniforme":
						case "u":
							operadorRecombinacao = OperadoresRecombinacao.Uniforme;
							break;
						default: throw new UnsupportedOperationException();
						}
						break;
					}
					case "-probabilidadeRecombinacao": 
					case "-pr": {
						probabilidadeRecombinacao = Double.parseDouble(args[i + 1]);
						break;
					}
					//-->
					
					case "-probabilidadeMutacao": 
					case "-pm": {
						probabilidadeMutacao = Double.parseDouble(args[i + 1]);
						break;
					}
					
					case "-operadorMutacao": 
					case "-om": {
						switch (args[i + 1]) {
						case "aleatoria":
						case "a":
							operadorMutacao = OperadoresMutacao.Aleatoria;
							break;
						case "linhaTroca2Genes":
						case "lt2g":
							operadorMutacao = OperadoresMutacao.LINHA_TROCA_2_GENES;
							break;
						default: throw new UnsupportedOperationException();
						}
						break;
					}
					case "-maximoGeracoes":
					case "-mg": {
						maximoGeracoes = Integer.parseInt(args[i + 1]);
						break;
					}
					default: throw new UnsupportedOperationException(args[i]);
					}
				} catch (Exception e) { e.printStackTrace(); }
			}			
		}

    	ClassePrincipal classePrincipal = 
    			new ClassePrincipal(
    					seed, 
    					tamanhoPopulacao, 
    					metodoSeleccao, 
    					tamanhoTorneio, 
    					percentagemPopulacaoElite, 
    					operadorRecombinacao, 
    					probabilidadeRecombinacao, 
    					operadorMutacao, 
    					probabilidadeMutacao, 
    					maximoGeracoes);
    	classePrincipal.executa();    
	}

}
