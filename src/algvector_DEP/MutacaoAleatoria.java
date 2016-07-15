package algvector_DEP;

public class MutacaoAleatoria extends Mutacao {

    public MutacaoAleatoria(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo) {
        //int[] v = {0, 9, 18, 27, 36, 45, 54, 63, 72};  //de zero a oito
        //int indiceCromossoma = v[AlgoritmoGenetico.aleatorio.nextInt(v.length)];
        int indiceGene1 = 0, indiceGene2 = 0, aux = 0;

        double teste = AlgoritmoGenetico.aleatorio.nextDouble();
        if (teste <= probabilidade) {
            do {
                //indiceGene1 = indiceCromossoma + AlgoritmoGenetico.aleatorio.nextInt(9);
                //indiceGene2 = indiceCromossoma + AlgoritmoGenetico.aleatorio.nextInt(9);
                indiceGene1 = AlgoritmoGenetico.aleatorio.nextInt(81);
                indiceGene2 = AlgoritmoGenetico.aleatorio.nextInt(81);
            } while (indiceGene1 == indiceGene2);

            //se nao houver numeros fixos implicados
            if ((!individuo.getGene(indiceGene1).isFixo()) && (!individuo.getGene(indiceGene2).isFixo())) {
                //troca as posicoes
                aux = individuo.getGene(indiceGene1).getAlelo();
                individuo.getGene(indiceGene1).setAlelo(individuo.getGene(indiceGene2).getAlelo());
                individuo.getGene(indiceGene2).setAlelo(aux);
            }
        }
    }
}