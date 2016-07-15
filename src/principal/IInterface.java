package principal;

public interface IInterface {
	void imprimePuzzleAtual(String melhorIndividuoRun);
	void imprimeInfoFitness(int geracao, double fitnessMelhorIndividuoGeracao, double fitnessMelhorIndividuoRun);
	void imprimeGreatSuccess(Boolean sucesso, String nomeFx, int geracao, double fitnessMelhorIndividuoRun, long seed, int tamanhoPopulacao, double probabilidadeRecombinacao, double probabilidadeMutacao);
}
