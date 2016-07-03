package ag2;

public class RecombinacaoUmCorteAleatorio extends Recombinacao {

    public RecombinacaoUmCorteAleatorio(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int pontoCorte = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
        
        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();
        
        // Totalmente aleatório.
        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = 0; j < pontoCorte; j++) {
                individuo1.getGene(i, j).setAlelo(pai2.getGene(i, j).getAlelo());
                individuo2.getGene(i, j).setAlelo(pai1.getGene(i, j).getAlelo());
            }
        }
    }
}