package algvector_DEP;

public class RecombinacaoUmCorteSemRepeticao extends Recombinacao {

    public RecombinacaoUmCorteSemRepeticao(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int[] v = {9, 18, 27, 36, 45, 54, 63, 72};  //de zero a sete        
        int pontoCorte = v[AlgoritmoGenetico.aleatorio.nextInt(v.length)];

        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();

        for (int g = 0; g < pontoCorte; g++) {
            if (!individuo1.getGene(g).isFixo()) {
                individuo1.getGene(g).setAlelo(pai2.getGene(g).getAlelo());
                individuo2.getGene(g).setAlelo(pai1.getGene(g).getAlelo());
            }
        }
    }
    
}