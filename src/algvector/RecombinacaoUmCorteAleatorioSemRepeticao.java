package algvector;

import java.util.LinkedList;

public class RecombinacaoUmCorteAleatorioSemRepeticao extends Recombinacao {

    public RecombinacaoUmCorteAleatorioSemRepeticao(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int[] v = {0, 9, 18, 27, 36, 45, 54, 63, 72, 81};  //de zero a sete
        int pontoCorte = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO * Individuo.DIMENSAO);
        int pontoCorteInicial = 0;
        int[] vI1 = new int[9], vI2 = new int[9], tmpvI1 = new int[9], tmpvI2 = new int[9];

        for (int i = 0; i < v.length; i++) {
            if (pontoCorte == v[i]) {
                pontoCorteInicial = pontoCorte;
                break;
            } else {
                if (pontoCorte < v[i]) {
                    vI1 = individuo1.getSubMatrizCromossoma(i);
                    vI2 = individuo2.getSubMatrizCromossoma(i);
                    pontoCorteInicial = v[i - 1];
                    break;
                }
            }
        }

        int corte = (pontoCorte - pontoCorteInicial);
        if (corte > 0) {
            LinkedList<Integer> listaTmpI1 = new LinkedList<Integer>();
            LinkedList<Integer> listaTmpI2 = new LinkedList<Integer>();
            for (int i = 0; i < Individuo.DIMENSAO; i++) {
                if (i < corte) {
                    tmpvI1[i] = vI2[i];
                    listaTmpI1.add(vI2[i]);

                    tmpvI2[i] = vI1[i];
                    listaTmpI2.add(vI1[i]);
                } else {
                    tmpvI1[i] = vI1[i];
                    tmpvI2[i] = vI2[i];
                }
            }

            /* Vai verificar as repetições e eliminá-las. */
            for (int i = corte; i < Individuo.DIMENSAO; i++) {
                /* Se existir um valor repetido na segunda parte do corte do vector vai procurar no outro vector um valor que ainda não exista e substitui-o. */
                if (listaTmpI1.contains(tmpvI1[i])) {
                    for (int j = 0; j < vI2.length; j++) {
                        /* Se encontrar um valor que ainda não tem. */
                        if (!listaTmpI1.contains(vI2[j])) {
                            /* Procura a posição no vector para substituir. */
                            for (int k = 0; k < tmpvI1.length; k++) {
                                if (tmpvI1[k] == tmpvI1[i]) {
                                    tmpvI1[k] = vI2[j];
                                    listaTmpI1.add(vI2[j]);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    listaTmpI1.add(tmpvI1[i]);
                }
                /* Se existir um valor repetido na segunda parte do corte do vector vai procurar no outro vector um valor que ainda não exista e substitui-o. */
                if (listaTmpI2.contains(tmpvI2[i])) {
                    for (int j = 0; j < vI1.length; j++) {
                        /* Se encontrar um valor que ainda não tem. */
                        if (!listaTmpI2.contains(vI1[j])) {
                            /* Procura a posição no vector para substituir. */
                            for (int k = 0; k < tmpvI2.length; k++) {
                                if (tmpvI2[k] == tmpvI2[i]) {
                                    tmpvI2[k] = vI1[j];
                                    listaTmpI2.add(vI1[j]);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    listaTmpI2.add(tmpvI2[i]);
                }
            }
        }





        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();

        for (int g = 0; g < pontoCorteInicial; g++) {
            if (!individuo1.getGene(g).isFixo()) {
                individuo1.getGene(g).setAlelo(pai2.getGene(g).getAlelo());
                individuo2.getGene(g).setAlelo(pai1.getGene(g).getAlelo());
            }
        }
        int aux = 0;
        for (int g = pontoCorteInicial; g < pontoCorte; g++) {
            if (!individuo1.getGene(g).isFixo()) {
                individuo1.getGene(g).setAlelo(tmpvI1[aux]);
                individuo2.getGene(g).setAlelo(tmpvI2[aux]);
            }
            aux++;
        }
    }
}