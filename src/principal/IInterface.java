package principal;

public interface IInterface {
	void imprimePuzzleAtual(String melhorIndividuoRun);
	void imprimeInfoFitness(int geracao, double fitnessMelhorIndividuoGeracao, double fitnessMelhorIndividuoRun);
	void imprimeGreatSuccess(String nomeFx, int geracao, double fitnessMelhorIndividuoGeracao, double fitnessMelhorIndividuoRun, long seed, int tamanhoPopulacao, Integer tamanhoTorneio, double probabilidadeRecombinacao, double probabilidadeMutacao);
}
