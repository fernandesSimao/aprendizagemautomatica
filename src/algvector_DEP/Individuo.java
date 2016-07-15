package algvector_DEP;

import java.util.ArrayList;
import java.util.LinkedList;

public class Individuo {

    public static int DIMENSAO = 9;
    private int[][] matrizInicial;
    
    private ArrayList<Gene> cromossomaTotal = new ArrayList<Gene>();
    private int[][] cromossomaMatriz = new int[DIMENSAO][DIMENSAO];
    //private int pontos[] = {0, 0, 3, 5, 7, 11, 13, 17, 19, 23};
    protected double fitness = Double.MAX_VALUE;
    protected double acumulado;

    public Individuo(int[][] _enigma) {
        
        this.matrizInicial = _enigma;
        for (int i = 0; i < DIMENSAO; i = i + 3) {
            for (int j = 0; j < DIMENSAO; j = j + 3) {
                setAleatorioSubMatriz(i, i + 2, j, j + 2);
            }
        }
    }

    public Individuo(ArrayList<Gene> cromossomaTotal, int[][] cromossomaMatriz, double fitness) {
        for (int i = 0; i < cromossomaTotal.size(); i++) {
            this.cromossomaTotal.add(new Gene(cromossomaTotal.get(i).getAlelo(), cromossomaTotal.get(i).isFixo()));
        }
        for (int i = 0; i < cromossomaMatriz.length; i++) {
            for (int j = 0; j < cromossomaMatriz.length; j++) {
                this.cromossomaMatriz[i][j] = cromossomaMatriz[i][j];
            }
        }
        this.fitness = fitness;
    }

    /**
     * Inicializa a mariz inicial com valores aleatórios de modo a que não se repitam na sub-matriz.
     */
    public void setAleatorioSubMatriz(int linhaI, int linhaF, int colunaI, int colunaF) {
        LinkedList<Integer> lista = new LinkedList<Integer>();
        for (int i = linhaI; i <= linhaF; i++) {
            for (int j = colunaI; j <= colunaF; j++) {
                lista.add(matrizInicial[i][j]);
            }
        }

        Integer aleatorio;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) == 0) {
                do {
                    aleatorio = (AlgoritmoGenetico.aleatorio.nextInt(DIMENSAO)) + 1;
                } while (lista.contains(aleatorio));
                lista.set(i, aleatorio);
                cromossomaTotal.add(new Gene(aleatorio, false));
            } else {
                cromossomaTotal.add(new Gene(lista.get(i), true));
            }
        }
    }

    public double calculaFitness() {
        //fitness = 0;
        /* Fitness de Linhas e Colunas. */
        /*
        for (int i = 0; i < cromossoma.length; i++) {
        fitness += getFitnessVector(getLinha(i));
        fitness += getFitnessVector(getColuna(i));
        }
         */
        /**/
        updateCromossomaFromCromossomaTotal();

        //int somatorioLinha = 0;                         //Gi1(X)
        int somatorioSomatoriosLinhas = 0;              //E(Gi1(X))
        //int somatorioColuna = 0;                        //Gj1(X)
        int somatorioSomatorioColunas = 0;              //E(Gj1(X))
        //double multiplicatorioLinha = 1.0;              //Gi2(X)
        //double multiplicatorioColuna = 1.0;             //Gj2(X)
        double somatorioMultiplicatorioLinha = 0.0;     //Raiz(Gi2(X))
        double somatorioMultiplicatorioColuna = 0.0;    //Raiz(Gj2(X))
        int numeroDeNumerosEmFalta = 0;

        int linha[] = null;
        int coluna[] = null;

        //int[][] matrizTeste = listaParaMatriz();
        //-->teste
        /*for (int i = 0; i < DIMENSAO; i++) {
        for (int j = 0; j < DIMENSAO; j++) {
        System.out.print(matrizTeste[i][j]);
        }
        System.out.print("\n");
        }*/
        //StandardInput.readString();
        //-->

        //calcula o somatorio do resultado do calculo de cada LINHA e COLUNA ao mesmo TEMPO.
        for (int i = 0; i < DIMENSAO; i++) {
            linha = getLinha(i);
            coluna = getColuna(i);

            somatorioSomatoriosLinhas += somatorioVector(linha);
            somatorioSomatorioColunas += somatorioVector(coluna);
        }

        //calcula o somatorio dos multplicatorios de cada LINHA e COLUNA ao mesmo TEMPO.
        for (int i = 0; i < DIMENSAO; i++) {
            linha = getLinha(i);
            coluna = getColuna(i);

            somatorioMultiplicatorioLinha += multilicatorioVector(linha);
            somatorioMultiplicatorioColuna += multilicatorioVector(coluna);
        }
        somatorioMultiplicatorioLinha = Math.sqrt(somatorioMultiplicatorioLinha);
        somatorioMultiplicatorioColuna = Math.sqrt(somatorioMultiplicatorioColuna);

        //verificar faltas;
        //LinkedList<Integer> listaLinhas = new LinkedList<Integer>();
        //LinkedList<Integer> listaColunas = new LinkedList<Integer>();

        for (int i = 0; i < DIMENSAO; i++) {
            LinkedList<Integer> listaLinhas = new LinkedList<Integer>();
            LinkedList<Integer> listaColunas = new LinkedList<Integer>();

            linha = getLinha(i);
            coluna = getColuna(i);
            //numeroDeNumerosEmFalta = 0;

            for (int j = 0; j < linha.length; j++) {
                //se a lista das linhas nao contem valor
                if (!listaLinhas.contains(linha[j])) {
                    //valor é inserido
                    listaLinhas.add(linha[j]);
                }
                //se a lista das colunas nao contem valor
                if (!listaColunas.contains(coluna[j])) {
                    listaColunas.add(coluna[j]);
                }
            }
            numeroDeNumerosEmFalta += 9 - listaLinhas.size();
            numeroDeNumerosEmFalta += 9 - listaColunas.size();
        }

        fitness = 10 * (somatorioSomatoriosLinhas + somatorioSomatorioColunas) + somatorioMultiplicatorioLinha + somatorioMultiplicatorioColuna + 50 * (numeroDeNumerosEmFalta);
        /**/
        return fitness;
    }

    private int somatorioVector(int[] v) {
        int sum = 0;

        for (int j = 0; j < v.length; j++) {
            sum += v[j];
        }
        return Math.abs(45 - sum);
    }

    private double multilicatorioVector(int[] v) {
        double sum = 1;
        int noveFactorial = 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2;

        for (int j = 0; j < v.length; j++) {
            sum *= v[j];
        }
        return Math.abs(noveFactorial - sum);
    }

    public int[] getLinha(int index) {
        int aux[] = new int[DIMENSAO];
        int count = 0, inicio = index * 3;

        if (index >= 3 && index <= 5) {
            inicio = ((DIMENSAO * 3) + ((index - 3) * 3));
        } else {
            if (index >= 6 && index <= 8) {
                inicio = ((DIMENSAO * 6) + ((index - 6) * 3));
            }
        }

        for (int i = inicio; count < DIMENSAO; i = i + DIMENSAO) {
            for (int j = i; j < (i + 3); j++) {
                aux[count] = cromossomaTotal.get(j).getAlelo();
                count++;
            }
        }
        return aux;
    }

    public int[] getColuna(int index) {
        int aux[] = new int[DIMENSAO];
        int count = 0, tmp = 0, inicio = index;

        if (index >= 3 && index <= 5) {
            inicio = (DIMENSAO + (index - 3));
        } else {
            if (index >= 6 && index <= 8) {
                inicio = (DIMENSAO * 2) + (index - 6);
            }
        }

        for (int i = inicio; i < cromossomaTotal.size(); i = i + 3) {
            aux[count] = cromossomaTotal.get(i).getAlelo();
            count++;
            tmp++;
            if (tmp == 3) {
                i += 3 * 6;
                tmp = 0;
            }
        }
        return aux;
    }

    public boolean solucaoFinal() {
        int linha[], coluna[], submatriz[];
        int count = 0;

        updateCromossomaFromCromossomaTotal();

        /* Verifica se existem repetições de números nas Linhas e Colunas. */
        /*
        for (int i = 0; i < cromossoma.length; i++) {
        linha = getLinha(i);
        coluna = getColuna(i);
        Arrays.sort(linha);
        Arrays.sort(coluna);
        if ((verificaRepeticoesVectorOrdenado(linha)) || (verificaRepeticoesVectorOrdenado(coluna))) {
        return false;
        }
        }
         */

        /* Verifica se existem repetições de números nas Sub-Matrizes. */
        /*
        for (int i = 0; i < DIMENSAO; i = i + 3) {
        for (int j = 0; j < DIMENSAO; j = j + 3) {
        submatriz = getSubMatriz(i, i + 2, j, j + 2);
        Arrays.sort(submatriz);
        if (verificaRepeticoesVectorOrdenado(submatriz)) {
        return false;
        }
        }
        }
         */

        /* Verifica para todas as 81 posições da matriz se existem repetições de números nas Linhas e Colunas. */
        for (int i = 0; i < cromossomaMatriz.length; i++) {
            for (int j = 0; j < cromossomaMatriz.length; j++) {
                for (int k = 0; k < cromossomaMatriz.length; k++) {
                    if (cromossomaMatriz[i][j] == cromossomaMatriz[i][k]) {
                        count++;
                    }
                    if (cromossomaMatriz[i][j] == cromossomaMatriz[k][j]) {
                        count++;
                    }
                }
                if (count != 2) {
                    return false;
                }
                count = 0;
            }
        }
        return true;
    /*
    if(this.fitness == 0)
    return true;
    else
    return false;*/
    }

    private void updateCromossomaFromCromossomaTotal() {
        int index = 1;

        /* setSubMatriz(LinhaInicial, LinhaFinal, ColunaInicial, ColunaFinal, NumeroMatriz); */
        for (int i = 0; i < DIMENSAO; i = i + 3) {
            for (int j = 0; j < DIMENSAO; j = j + 3) {
                setSubMatrizCromossoma(i, i + 2, j, j + 2, index);
                index++;
            }
        }
    //teste
        /*System.out.print("\nda matriz cromossomaMatriz\n");
    for (int i = 0; i < cromossomaMatriz.length; i++) {
    for (int j = 0; j < cromossomaMatriz.length; j++) {
    System.out.print(cromossomaMatriz[i][j]);
    }
    System.out.print("\n");
    }
    System.out.print("\ndo array:\n");
    Iterator it = cromossomaTotal.iterator();
    int i = 0;
    while (it.hasNext()) {
    System.out.print(((Gene) it.next()).getAlelo());
    if (++i % 3 == 0) {
    System.out.print("\n");
    }
    }*/
    //StandardInput.readString();
    //-->
    }

    /**
     * Actualiza uma dada sub-matriz.
     */
    private void setSubMatrizCromossoma(int linhaI, int linhaF, int colunaI, int colunaF, int numeroMatriz) {
        int aux = (9 * numeroMatriz) - 9;
        for (int i = linhaI; i <= linhaF; i++) {
            for (int j = colunaI; j <= colunaF; j++) {
                cromossomaMatriz[i][j] = cromossomaTotal.get(aux).getAlelo();
                aux++;
            }
        }
    }

    public int[] getSubMatrizCromossoma(int numeroSubMatriz) {
        int j = 0, aux = (9 * numeroSubMatriz) - 9;
        int[] v = new int[DIMENSAO];

        for (int i = aux; i < aux + DIMENSAO; i++) {
            v[j] = cromossomaTotal.get(i).getAlelo();
            j++;
        }
        return v;
    }

    private boolean verificaRepeticoesVectorOrdenado(int[] vector) {
        for (int i = 1; i < vector.length + 1; i++) {
            if ((vector[i - 1] != i)) {
                return true;
            }
        }
        return false;
    }

    public double getFitness() {
        return fitness;
    }

    public void setAcumulado(double acumulado) {
        this.acumulado = acumulado;
    }

    public double getAcumulado() {
        return acumulado;
    }

    public int getNumGenes() {
        return cromossomaTotal.size();
    }

    public Gene getGene(int index) {
        return cromossomaTotal.get(index);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < cromossomaMatriz.length; i++) {
            for (int j = 0; j < cromossomaMatriz.length; j++) {
                if (j == 3 || j == 6) {
                    sb.append("|");
                }
                sb.append(cromossomaMatriz[i][j]);
            }
            if (i == 2 || i == 5) {
                sb.append("\n----+----+----");
            }
            sb.append("\n");
        }

        //sb.append("- " + representacaoDecimal() + " ");
        //sb.append("fitness: " + fitness + " acumulado: " + acumulado);
        //sb.append("fitness: " + fitness);
        return sb.toString();
    }

    public Individuo clone() {
        return new Individuo(cromossomaTotal, cromossomaMatriz, fitness);
    }
}
