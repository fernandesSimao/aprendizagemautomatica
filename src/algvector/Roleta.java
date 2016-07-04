package algvector;

public class Roleta extends MetodoSeleccao {

    public void executar(Populacao populacaoOrigem, Populacao populacaoResultante) {
        double acumulado = 0;

        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            acumulado = acumulado + populacaoOrigem.getIndividuo(i).getFitness();
            populacaoOrigem.getIndividuo(i).setAcumulado(acumulado);
        }

        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            populacaoOrigem.getIndividuo(i).setAcumulado(populacaoOrigem.getIndividuo(i).getAcumulado() / (double) acumulado);
        }

        for (int i = 0; i < populacaoOrigem.getTamanho(); i++) {
            populacaoResultante.setIndividuo(roleta(populacaoOrigem), i);
        }
    }

    private Individuo roleta(Populacao populacao) {
        int i = 0;
        boolean escolhido = false;
        double probabilidade = AlgoritmoGenetico.aleatorio.nextDouble();

        do {
            if (probabilidade <= populacao.getIndividuo(i).getAcumulado()) {
                escolhido = true;
            } else {
                i++;
            }
        } while (!escolhido);

        return (Individuo) populacao.getIndividuo(i).clone();
    }
}
