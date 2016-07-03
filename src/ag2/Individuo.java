package ag2;

import java.util.Arrays;
import java.util.LinkedList;

public class Individuo {

    public static int DIMENSAO = 9;
    private int[][] matrizInicial;
    private Gene[][] cromossoma = new Gene[DIMENSAO][DIMENSAO];
    protected double fitness = Double.MAX_VALUE;
    protected double acumulado;

    public Individuo(int[][] _enigma) {
        this.matrizInicial = _enigma;
        setAleatorioMatriz();
    }

    public Individuo(Gene[][] cromossoma, double fitness) {
        for (int i = 0; i < cromossoma.length; i++) {
            for (int j = 0; j < cromossoma.length; j++) {
                this.cromossoma[i][j] = new Gene(cromossoma[i][j].getAlelo(), cromossoma[i][j].isFixo());
            }
        }
        this.fitness = fitness;
    }

    /**
     * Inicializa a mariz inicial com valores aleatórios de modo a que não se repitam na linha da matriz.
     */
    public void setAleatorioMatriz() {
        for (int i = 0; i < DIMENSAO; i++) {
            LinkedList<Integer> lista = new LinkedList<Integer>();
            Integer aleatorio = 0;

            for (int j = 0; j < DIMENSAO; j++) {
                lista.add(matrizInicial[i][j]);
            }
            for (int j = 0; j < DIMENSAO; j++) {
                if (lista.get(j) == 0) {
                    do {
                        aleatorio = (AlgoritmoGenetico.aleatorio.nextInt(DIMENSAO)) + 1;
                    } while (lista.contains(aleatorio));
                    lista.set(j, aleatorio);
                    cromossoma[i][j] = new Gene(aleatorio, false);
                } else {
                    cromossoma[i][j] = new Gene(lista.get(j), true);
                }
            }
        }
    }

    /*
    public double calculaFitness() {
    //int somatorioLinha = 0;                         //Gi1(X)
    int somatorioSomatoriosLinhas = 0;              //E(Gi1(X))
    //int somatorioColuna = 0;                        //Gj1(X)
    int somatorioSomatorioColunas = 0;              //E(Gj1(X))
    //double multiplicatorioLinha = 1.0;              //Gi2(X)
    //double multiplicatorioColuna = 1.0;             //Gj2(X)
    double somatorioMultiplicatorioLinha = 0.0;     //Raiz(Gi2(X))
    double somatorioMultiplicatorioColuna = 0.0;    //Raiz(Gj2(X))
    int numeroDeNumerosEmFalta = 0;
    
    Gene linha[] = null;
    Gene coluna[] = null;
    
    //calcula o somatorio do resultado do calculo de cada LINHA e COLUNA ao mesmo TEMPO.
    for (int i = 0; i < DIMENSAO; i++) {
    //linha = getLinha(i);
    linha = getSubMatriz(i);
    coluna = getColuna(i);
    
    somatorioSomatoriosLinhas += somatorioVector(linha);
    somatorioSomatorioColunas += somatorioVector(coluna);
    }
    
    //calcula o somatorio dos multplicatorios de cada LINHA e COLUNA ao mesmo TEMPO.
    for (int i = 0; i < DIMENSAO; i++) {
    //linha = getLinha(i);
    linha = getSubMatriz(i);
    coluna = getColuna(i);
    
    somatorioMultiplicatorioLinha += multilicatorioVector(linha);
    somatorioMultiplicatorioColuna += multilicatorioVector(coluna);
    }
    somatorioMultiplicatorioLinha = Math.sqrt(somatorioMultiplicatorioLinha);
    somatorioMultiplicatorioColuna = Math.sqrt(somatorioMultiplicatorioColuna);
    
    for (int i = 0; i < DIMENSAO; i++) {
    //numeroDeNumerosEmFalta += contaNumerosEmFalta(getLinha(i));
    numeroDeNumerosEmFalta += contaNumerosEmFalta(getSubMatriz(i));
    numeroDeNumerosEmFalta += contaNumerosEmFalta(getColuna(i));
    }
    
    fitness = 10 * (somatorioSomatoriosLinhas + somatorioSomatorioColunas) + (somatorioMultiplicatorioLinha + somatorioMultiplicatorioColuna) + 50 * (numeroDeNumerosEmFalta);
    
    return fitness;
    }*/
    /**/
    public double calculaFitness() {
        fitness = 0;
        for (int i = 0; i < DIMENSAO; i++) {
            fitness += contaNumerosRepetidos(getLinha(i));
            fitness += contaNumerosRepetidos(getColuna(i));
            fitness += contaNumerosRepetidos(getSubMatriz(i));
        }
        return fitness;
    }/**/


    private int contaNumerosEmFalta(Gene[] v) {
        int conta = 0;
        LinkedList<Integer> lista = new LinkedList<Integer>();

        for (int i = 0; i < v.length; i++) {
            lista.add(v[i].getAlelo());
        }
        for (int i = 0; i < Individuo.DIMENSAO; i++) {
            if (!lista.contains((i + 1))) {
                conta++;
            }
        }
        return conta;
    }

    private int contaNumerosRepetidos(Gene[] v) {
        int conta = 0;
        LinkedList<Integer> lista = new LinkedList<Integer>();

        for (int i = 0; i < v.length; i++) {
            lista.add(v[i].getAlelo());
        }
        for (int i = 0; i < DIMENSAO; i++) {
            int aux = lista.pop();
            if (lista.contains(aux)) {
                conta++;
            }
        }
        return conta;
    }

    private int somatorioVector(Gene[] v) {
        int sum = 0;

        for (int j = 0; j < v.length; j++) {
            sum += v[j].getAlelo();
        }
        return Math.abs(45 - sum);
    }

    private double multilicatorioVector(Gene[] v) {
        double sum = 1;
        int noveFactorial = 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2;

        for (int j = 0; j < v.length; j++) {
            sum *= v[j].getAlelo();
        }
        return Math.abs(noveFactorial - sum);
    }

    public Gene[] getLinha(int index) {
        Gene aux[] = new Gene[DIMENSAO];

        for (int i = 0; i < cromossoma.length; i++) {
            aux[i] = new Gene(cromossoma[index][i].getAlelo(), cromossoma[index][i].isFixo());
        }
        return aux;
    }

    public Gene[] getColuna(int index) {
        Gene aux[] = new Gene[DIMENSAO];

        for (int i = 0; i < cromossoma.length; i++) {
            aux[i] = new Gene(cromossoma[i][index].getAlelo(), cromossoma[i][index].isFixo());
        }
        return aux;
    }

    public boolean solucaoFinal() {
        for (int i = 0; i < DIMENSAO; i++) {
            if (verificaRepeticoesVectorOrdenado(ordenaVector(getLinha(i)))) {
                return false;
            }
            if (verificaRepeticoesVectorOrdenado(ordenaVector(getColuna(i)))) {
                return false;
            }
            if (verificaRepeticoesVectorOrdenado(ordenaVector(getSubMatriz(i)))) {
                return false;
            }
        }

        /* Verifica para todas as 81 posições da matriz se existem repetições de números nas Linhas e Colunas.
        int count = 0;
        
        for (int i = 0; i < cromossoma.length; i++) {
        for (int j = 0; j < cromossoma.length; j++) {
        for (int k = 0; k < cromossoma.length; k++) {
        if (cromossoma[i][j].getAlelo() == cromossoma[i][k].getAlelo()) {
        count++;
        }
        if (cromossoma[i][j].getAlelo() == cromossoma[k][j].getAlelo()) {
        count++;
        }
        }
        if (count != 2) {
        return false;
        }
        count = 0;
        }
        }*/
        return true;
    }

    private boolean verificaRepeticoesVectorOrdenado(int[] vector) {
        for (int i = 1; i < vector.length + 1; i++) {
            if ((vector[i - 1] != i)) {
                return true;
            }
        }
        return false;
    }

    private int[] ordenaVector(Gene[] vector) {
        int aux[] = new int[DIMENSAO];
        for (int i = 0; i < vector.length; i++) {
            aux[i] = vector[i].getAlelo();
        }
        Arrays.sort(aux);
        return aux;
    }

    public Gene[] getSubMatriz(int index) {
        int aux = 0;
        for (int i = 0; i < DIMENSAO; i = i + 3) {
            for (int j = 0; j < DIMENSAO; j = j + 3) {
                if (index == aux) {
                    return getSubMatriz(i, i + 2, j, j + 2);
                }
                aux++;
            }
        }
        return new Gene[0];
    }

    private Gene[] getSubMatriz(int linhaI, int linhaF, int colunaI, int colunaF) {
        Gene aux[] = new Gene[DIMENSAO];
        int index = 0;

        for (int i = linhaI; i <= linhaF; i++) {
            for (int j = colunaI; j <= colunaF; j++) {
                aux[index] = new Gene(cromossoma[i][j].getAlelo(), cromossoma[i][j].isFixo());
                index++;
            }
        }
        return aux;
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

    public Gene[][] getCromossoma() {
        return cromossoma;
    }

    public Gene getGene(int linha, int coluna) {
        return cromossoma[linha][coluna];
    }

    /*
    public void setGene(int linha, int coluna, int alelo) {
    cromossoma[linha][coluna] = new Gene(alelo, false);
    }
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < cromossoma.length; i++) {
            for (int j = 0; j < cromossoma.length; j++) {
                if (j == 3 || j == 6) {
                    sb.append("|");
                }
                sb.append(cromossoma[i][j].getAlelo());
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
        return new Individuo(cromossoma, fitness);
    }
}
