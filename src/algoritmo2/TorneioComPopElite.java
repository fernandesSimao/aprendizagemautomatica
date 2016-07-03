package algoritmo2;

import java.util.LinkedList;

public class TorneioComPopElite extends MetodoSeleccao {

    private int tamanho;
    private double percentagemPopulacaoElite = 0.05;
    private int tamanhoPopulacaoElite;

    public TorneioComPopElite() {
        this(2);
    }

    public TorneioComPopElite(int tamanho) {
        this.tamanho = tamanho;
    }

    public void executar(Populacao populacaoOrigem, Populacao populacaoResultante) {
        tamanhoPopulacaoElite = Math.round((float) (populacaoOrigem.getTamanho() * percentagemPopulacaoElite));

        //lista com os melhores individuos de uma geração
        LinkedList<Individuo> lista = new LinkedList<Individuo>(Populacao.seleccionaPopulacaoElite(populacaoOrigem, tamanhoPopulacaoElite));

        //para os restante que faltam para completar a populacao
        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            lista.add(torneio(populacaoOrigem));
        }
        Populacao p = new Populacao(lista.size());
        p.setPopulacao(lista);
        populacaoResultante.setPopulacao(Populacao.seleccionaPopulacaoElite(p, populacaoOrigem.getTamanho()));
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
