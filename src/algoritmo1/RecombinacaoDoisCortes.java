package algoritmo1;

public class RecombinacaoDoisCortes extends Recombinacao {

    public RecombinacaoDoisCortes(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        Gene auxG;
        int[] v = {9, 18, 27, 36, 45, 54, 63, 72};
        //int pontoCorte1 = AlgoritmoGenetico.aleatorio.nextInt(individuo1.getNumGenes());
        //int pontoCorte2 = AlgoritmoGenetico.aleatorio.nextInt(individuo1.getNumGenes());
        int pontoCorte1 = v[AlgoritmoGenetico.aleatorio.nextInt(8)];
        int pontoCorte2 = v[AlgoritmoGenetico.aleatorio.nextInt(8)];
        int aux;

        if (pontoCorte1 > pontoCorte2) {
            aux = pontoCorte1;
            pontoCorte1 = pontoCorte2;
            pontoCorte2 = aux;
        }

        for (int g = pontoCorte1; g < pontoCorte2; g++) {
            auxG = individuo1.getGene(g);
            if (!auxG.isFixo()) {
                //individuo1.setGene(g, individuo2.getGene(g).getAlelo());
                //individuo2.setGene(g, auxG.getAlelo());
                individuo1.getGene(g).setAlelo(individuo2.getGene(g).getAlelo());
                individuo2.getGene(g).setAlelo(auxG.getAlelo());
            }
        }
    }
}
