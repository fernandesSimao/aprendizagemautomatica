package algoritmo2;

public class MutacaoLinhaDuasPosicoes extends Mutacao {

    public MutacaoLinhaDuasPosicoes(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo) {
        int aux = 0;

        // Linha a Linha, troca duas posições eleatórias.
        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            for (int j = 0; j < Individuo.DIMENSAO; j++) {
                double teste = AlgoritmoGenetico.aleatorio.nextDouble();
                int linha = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);

                if (teste <= probabilidade) {
                    int coluna;
                    do {
                        coluna = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
                    } while (coluna == j);

                    //se nao houver numeros fixos implicados
                    if ((!individuo.getGene(linha, j).isFixo()) && (!individuo.getGene(linha, coluna).isFixo())) {
                        //troca as posicoes
                        aux = individuo.getGene(linha, j).getAlelo();
                        individuo.getGene(linha, j).setAlelo(individuo.getGene(linha, coluna).getAlelo());
                        individuo.getGene(linha, coluna).setAlelo(aux);
                    }
                }
            }
        }
    }
}