package algmatriz;

public class TorneioSimplesBatota extends MetodoSeleccao {

    private int tamanho;

//    public TorneioSimplesBatota() {
//        this(2);
//    }

    public TorneioSimplesBatota(int tamanho) {
        this.tamanho = tamanho;
    }

    public void executar(Populacao populacaoOrigem, Populacao populacaoResultante) {
        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            populacaoResultante.setIndividuo(torneio(populacaoOrigem), i);
        }
    }

    private Individuo torneio(Populacao populacao) {
        int aux, melhorIndividuo = AlgoritmoGenetico.aleatorio.nextInt(populacao.getTamanho());
        double batota = AlgoritmoGenetico.aleatorio.nextDouble();

        if (batota >= 0.8) {
            return populacao.getIndividuo(melhorIndividuo).clone();
        }
        for (int i = 1; i < tamanho; i++) {
            aux = AlgoritmoGenetico.aleatorio.nextInt(populacao.getTamanho());
            if (populacao.getIndividuo(aux).getFitness() < populacao.getIndividuo(melhorIndividuo).getFitness()) {
                melhorIndividuo = aux;
            }
        }
        return populacao.getIndividuo(melhorIndividuo).clone();
    }
}
