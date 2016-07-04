package algvector;

public class RecombinacaoUniforme extends Recombinacao {

    public RecombinacaoUniforme(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        /* Exercicio 1 - b) */
        //
        Gene gene;

        for (int i = 0; i < individuo1.getNumGenes(); i += 9) {

            if (AlgoritmoGenetico.aleatorio.nextInt(2) == 0) {
                for (int j = i; j < i + 9; j++) {
                    gene = individuo1.getGene(j);
                    if (!gene.isFixo()) {
                        //individuo1.setGene(j, individuo2.getGene(i).getAlelo());
                        //individuo2.setGene(j, gene.getAlelo());
                        individuo1.getGene(j).setAlelo(individuo2.getGene(i).getAlelo());
                        individuo2.getGene(j).setAlelo(gene.getAlelo());
                    }
                }
            }
        }
    }
}
