package algmatriz;

public class MutacaoAleatoria extends Mutacao {

    public MutacaoAleatoria(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo) {
        // Troca duas posições completamente aleatórias.
        int linha, coluna;
        int aux = 0;

        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = 0; j < Individuo.DIMENSAO; j++) {
                double teste = AlgoritmoGenetico.aleatorio.nextDouble();
                if (teste <= probabilidade) {
                    do {
                        linha = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
                        coluna = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
                    } while ((linha == i) && (coluna == j));

                    //se nao houver numeros fixos implicados
                    if ((!individuo.getGene(i, j).isFixo()) && (!individuo.getGene(linha, coluna).isFixo())) {
                        //troca as posicoes
                        aux = individuo.getGene(i, j).getAlelo();
                        individuo.getGene(i, j).setAlelo(individuo.getGene(linha, coluna).getAlelo());
                        individuo.getGene(linha, coluna).setAlelo(aux);
                    }
                }
            }
        }
    }
}