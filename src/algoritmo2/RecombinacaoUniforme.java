package algoritmo2;

public class RecombinacaoUniforme extends Recombinacao {

    public RecombinacaoUniforme(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();

        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = 0; j < Individuo.DIMENSAO; j++) {
                if (AlgoritmoGenetico.aleatorio.nextInt(2) == 0) {
                    if (!individuo1.getGene(i, j).isFixo()) {
                        individuo1.getGene(i, j).setAlelo(pai2.getGene(i, j).getAlelo());
                        individuo2.getGene(i, j).setAlelo(pai1.getGene(i, j).getAlelo());
                    }
                }
            }
        }
    }
}
