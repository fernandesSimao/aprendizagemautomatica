package ag2;

import java.util.LinkedList;

public class RecombinacaoUmCorteSemRepeticao extends Recombinacao {

    public RecombinacaoUmCorteSemRepeticao(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo1, Individuo individuo2) {
        int pontoCorte = AlgoritmoGenetico.aleatorio.nextInt(Individuo.DIMENSAO);
        
        //System.out.println("Ponto de Corte: " + pontoCorte);

        Individuo pai1 = individuo1.clone();
        Individuo pai2 = individuo2.clone();

        Gene[] linhaFilho1 = new Gene[Individuo.DIMENSAO], linhaFilho2 = new Gene[Individuo.DIMENSAO];
        Gene[] linhaTmpFilho1 = new Gene[Individuo.DIMENSAO], linhaTmpFilho2 = new Gene[Individuo.DIMENSAO];

        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            linhaFilho1 = individuo1.getLinha(i);
            linhaTmpFilho1 = individuo1.getLinha(i);
            linhaFilho2 = individuo2.getLinha(i);
            linhaTmpFilho2 = individuo2.getLinha(i);

            recombinaLinhas(linhaTmpFilho1, linhaTmpFilho2, pontoCorte);
            removeRepeticoes(linhaTmpFilho1, linhaTmpFilho2, pontoCorte, linhaFilho1, linhaFilho2);

            for (int j = 0; j < Individuo.DIMENSAO; j++) {
                individuo1.getGene(i, j).setAlelo(linhaTmpFilho1[j].getAlelo());
                individuo2.getGene(i, j).setAlelo(linhaTmpFilho2[j].getAlelo());
            }
        }
    }

    private void recombinaLinhas(Gene[] linhaFilho1, Gene[] linhaFilho2, int pontoCorte) {
        for (int i = 0; i < pontoCorte; i++) {
            if (!linhaFilho1[i].isFixo()) {
                int aux = linhaFilho1[i].getAlelo();

                linhaFilho1[i].setAlelo(linhaFilho2[i].getAlelo());
                linhaFilho2[i].setAlelo(aux);
            }
        }
    }

    private void removeRepeticoes(Gene[] linhaTmpFilho1, Gene[] linhaTmpFilho2, int pontoCorte, Gene[] linhaFilho1, Gene[] linhaFilho2) {
        LinkedList<Integer> listaTmpFilho1 = new LinkedList<Integer>();
        LinkedList<Integer> listaTmpFilho2 = new LinkedList<Integer>();

        // Adiciona a uma lista temporária os elementos da Primeira parte do vector que foram substituidos.
        for (int i = 0; i < pontoCorte; i++) {
            listaTmpFilho1.add(linhaTmpFilho1[i].getAlelo());
            listaTmpFilho2.add(linhaTmpFilho2[i].getAlelo());
        }

        // Pesquisa na lista a Segunda parte do vector para verificar as repetições.
        for (int i = pontoCorte; i < Individuo.DIMENSAO; i++) {
            // [Vector 1] - Se o valor já existir na primeira parte do vector.
            if (listaTmpFilho1.contains(linhaFilho1[i].getAlelo())) {
                for (int j = 0; j < Individuo.DIMENSAO; j++) {
                    // Selecciona no vector temporário o Gene a substituir.
                    if ((linhaTmpFilho1[j].getAlelo() == linhaFilho1[i].getAlelo()) && (i != j)) {
                        // Se o Gene da primeira parte do vector temporário for fixo, substitui na segunda.
                        if (linhaTmpFilho1[j].isFixo()) {
                            for (int k = 0; k < Individuo.DIMENSAO; k++) {
                                if (!listaTmpFilho1.contains(linhaFilho2[k].getAlelo())) {
                                    linhaTmpFilho1[i].setAlelo(linhaFilho2[k].getAlelo());
                                    listaTmpFilho1.add(linhaFilho2[k].getAlelo());
                                    break;
                                }
                            }
                        } else {
                            for (int k = 0; k < Individuo.DIMENSAO; k++) {
                                if (!listaTmpFilho1.contains(linhaFilho2[k].getAlelo())) {
                                    linhaTmpFilho1[j].setAlelo(linhaFilho2[k].getAlelo());
                                    listaTmpFilho1.add(linhaFilho2[k].getAlelo());
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } else {
                // Se o valor ainda não existe na Primeira parte do vector.
                listaTmpFilho1.add(linhaFilho1[i].getAlelo());
            }


            // [Vector 2] - Se o valor já existir na primeira parte do vector.
            if (listaTmpFilho2.contains(linhaFilho2[i].getAlelo())) {
                for (int j = 0; j < Individuo.DIMENSAO; j++) {
                    // Selecciona no vector temporário o Gene a substituir.
                    if ((linhaTmpFilho2[j].getAlelo() == linhaFilho2[i].getAlelo()) && (i != j)) {
                        // Se o Gene da primeira parte do vector temporário for fixo, substitui na segunda.
                        if (linhaTmpFilho2[j].isFixo()) {
                            for (int k = 0; k < Individuo.DIMENSAO; k++) {
                                if (!listaTmpFilho2.contains(linhaFilho1[k].getAlelo())) {
                                    linhaTmpFilho2[i].setAlelo(linhaFilho1[k].getAlelo());
                                    listaTmpFilho2.add(linhaFilho1[k].getAlelo());
                                    break;
                                }
                            }
                        } else {
                            for (int k = 0; k < Individuo.DIMENSAO; k++) {
                                if (!listaTmpFilho2.contains(linhaFilho1[k].getAlelo())) {
                                    linhaTmpFilho2[j].setAlelo(linhaFilho1[k].getAlelo());
                                    listaTmpFilho2.add(linhaFilho1[k].getAlelo());
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } else {
                // Se o valor ainda não existe na Primeira parte do vector.
                listaTmpFilho2.add(linhaFilho2[i].getAlelo());
            }
        }
    }
}