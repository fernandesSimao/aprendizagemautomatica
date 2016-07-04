package algmatriz;

public class RecombinacaoUmCorteBoloco3 extends Recombinacao {

    public RecombinacaoUmCorteBoloco3(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int pontoCorte = AlgoritmoGenetico.aleatorio.nextInt(2) + 1;

        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();
        
        // Em blocos de 3.
        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = 0; j < pontoCorte * 3; j++) {
                individuo1.getGene(i, j).setAlelo(pai2.getGene(i, j).getAlelo());
                individuo2.getGene(i, j).setAlelo(pai1.getGene(i, j).getAlelo());
            }
        }
    }
}