package algoritmo2;

public class TorneioSimples extends MetodoSeleccao {

    private int tamanho;

    public TorneioSimples() {
        this(2);
    }

    public TorneioSimples(int tamanho) {
        this.tamanho = tamanho;
    }

    public void executar(Populacao populacaoOrigem, Populacao populacaoResultante) {
        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            populacaoResultante.setIndividuo(torneio(populacaoOrigem), i);
        }
    }

    private Individuo torneio(Populacao populacao) {
        int aux, melhorIndividuo = AlgoritmoGenetico.aleatorio.nextInt(populacao.getTamanho());

        for (int i = 1; i < tamanho; i++) {
            aux = AlgoritmoGenetico.aleatorio.nextInt(populacao.getTamanho());
            if (populacao.getIndividuo(aux).getFitness() < populacao.getIndividuo(melhorIndividuo).getFitness()) {
                melhorIndividuo = aux;
            }
        }
        return populacao.getIndividuo(melhorIndividuo).clone();
    }
}
