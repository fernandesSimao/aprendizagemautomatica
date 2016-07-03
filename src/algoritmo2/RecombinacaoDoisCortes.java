package algoritmo2;

public class RecombinacaoDoisCortes extends Recombinacao {

    public RecombinacaoDoisCortes(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int pontoCorte1, pontoCorte2;
        int aux;

        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();

        do {
            pontoCorte1 = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
            pontoCorte2 = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
        } while (pontoCorte1 == pontoCorte2);

        if (pontoCorte1 > pontoCorte2) {
            aux = pontoCorte1;
            pontoCorte1 = pontoCorte2;
            pontoCorte2 = aux;
        }

        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = pontoCorte1; j < pontoCorte2; j++) {
                if (!individuo1.getGene(i, j).isFixo()) {
                    individuo1.getGene(i, j).setAlelo(pai2.getGene(i, j).getAlelo());
                    individuo2.getGene(i, j).setAlelo(pai1.getGene(i, j).getAlelo());
                }
            }
        }
    }
}
