package algvector;

import java.util.Arrays;

public class MutacaoSwap5 extends Mutacao{

    private static final int DIMENSAO = 9;

    public MutacaoSwap5(double probabilidade) {
        super(probabilidade);
    }

    public void executar(Individuo individuo) {
        int[] v = {0, 9, 18, 27, 36, 45, 54, 63, 72};  //de zero a oito
        int deslocamento = v[AlgoritmoGenetico.aleatorio.nextInt(v.length)];
        int numTrocas = 0, indiceGene1 = 0, indiceGene2 = 0, aux = 0;
        int subMatriz = 0, numOcorrencias = 0;
        int[] coordenadaGene1 = new int[2], coordenadaGene2 = new int[2], coordenadaMatriz = new int[2];
        int[] linhaGene1 = new int[DIMENSAO], colunaGene1 = new int[DIMENSAO], linhaGene2 = new int[DIMENSAO], colunaGene2 = new int[DIMENSAO];

        for (int indiceCromossoma = 0; indiceCromossoma < individuo.getNumGenes(); indiceCromossoma += 9) {
            double teste = AlgoritmoGenetico.aleatorio.nextDouble();
            if (teste <= probabilidade) {
                numTrocas = AlgoritmoGenetico.aleatorio.nextInt(5) + 1;
                //numTrocas = 1;
                //para o num de trocas sorteado
                for (int j = 0; j < numTrocas; j++) {
                    do {
                        indiceGene1 = deslocamento + AlgoritmoGenetico.aleatorio.nextInt(9);
                        indiceGene2 = deslocamento + AlgoritmoGenetico.aleatorio.nextInt(9);
                    } while (indiceGene1 == indiceGene2);

                    //se um dos Genes sorteados for fixo
                    if (individuo.getGene(indiceGene1).isFixo() || individuo.getGene(indiceGene2).isFixo()) {
                        //ignora as restantes trocas
                        break;
                    } else {    //se nao houver numeros fixos implicados
                        //troca as posicoes
                        aux = individuo.getGene(indiceGene1).getAlelo();
                        individuo.getGene(indiceGene1).setAlelo(individuo.getGene(indiceGene2).getAlelo());
                        individuo.getGene(indiceGene2).setAlelo(aux);
                        
                        subMatriz = indiceCromossoma / 9;
                        //subMatriz = indiceGene1 / 9;
                        
                        coordenadaGene1 = calculaCoordenadas(indiceGene1 % 9);
                        coordenadaMatriz = calculaCoordenadas(indiceGene1 / 9);
                        linhaGene1 = individuo.getLinha(coordenadaGene1[0] + (coordenadaMatriz[0] * 3));
                        colunaGene1 = individuo.getColuna(coordenadaGene1[1] + (coordenadaMatriz[1] * 3));
                        
                        coordenadaGene2 = calculaCoordenadas(indiceGene2 % 9);
                        coordenadaMatriz = calculaCoordenadas(indiceGene2 / 9);
                        linhaGene2 = individuo.getLinha(coordenadaGene2[0] + (coordenadaMatriz[0] * 3));
                        colunaGene2 = individuo.getColuna(coordenadaGene2[1] + (coordenadaMatriz[1] * 3));
                        
                        
                        Arrays.sort(linhaGene1);
                        Arrays.sort(linhaGene2);
                        Arrays.sort(colunaGene1);
                        Arrays.sort(colunaGene2);
                        
                        numOcorrencias += Arrays.binarySearch(linhaGene1, individuo.getGene(indiceGene1).getAlelo());
                        numOcorrencias += Arrays.binarySearch(linhaGene2, individuo.getGene(indiceGene2).getAlelo());
                        numOcorrencias += Arrays.binarySearch(colunaGene1, individuo.getGene(indiceGene1).getAlelo());
                        numOcorrencias += Arrays.binarySearch(colunaGene2, individuo.getGene(indiceGene2).getAlelo());
                        
                        if(numOcorrencias <= 3){
                            break;
                        }
                    }

                }
            }
        }
    }
    private int[] calculaCoordenadas(int valor){
        
        int[] coordenada = new int[2];
        switch(valor){
            case 0: coordenada[0] = 0;
                    coordenada[1] = 0;
            break;
            case 1: coordenada[0] = 0;
                    coordenada[1] = 1;
            break;
            case 2: coordenada[0] = 0;
                    coordenada[1] = 2;
            break;
            case 3: coordenada[0] = 1;
                    coordenada[1] = 0;
            break;
            case 4: coordenada[0] = 1;
                    coordenada[1] = 1;
            break;
            case 5: coordenada[0] = 1;
                    coordenada[1] = 2;
            break;
            case 6: coordenada[0] = 2;
                    coordenada[1] = 0;
            break;
            case 7: coordenada[0] = 2;
                    coordenada[1] = 1;
            break;
            case 8: coordenada[0] = 2;
                    coordenada[1] = 2;
            break;

        }
        return coordenada;
    }
    /*
    //ATENCAO!! codigo repetido
    public int[][] listaParaMatriz(Individuo individuo) {
    int[] subSudoku = new int[DIMENSAO], coordenadas = new int[2];
    int[][] matriz = new int[DIMENSAO][DIMENSAO];
    
    Iterator it = individuo.getCromossomaLista();
    int aux = 0, indice = 0;
    
    
    while (it.hasNext()) {
    subSudoku[indice++] = ((Gene) it.next()).getAlelo();
    
    aux++;
    
    if (indice % 9 == 0) {
    switch (aux) {
    case 9:
    coordenadas[0] = 0;
    coordenadas[1] = 0;
    break;
    case 18:
    coordenadas[0] = 0;
    coordenadas[1] = 3;
    break;
    case 27:
    coordenadas[0] = 0;
    coordenadas[1] = 6;
    break;
    case 36:
    coordenadas[0] = 3;
    coordenadas[1] = 0;
    break;
    case 45:
    coordenadas[0] = 3;
    coordenadas[1] = 3;
    break;
    case 54:
    coordenadas[0] = 3;
    coordenadas[1] = 6;
    break;
    case 63:
    coordenadas[0] = 6;
    coordenadas[1] = 0;
    break;
    case 72:
    coordenadas[0] = 6;
    coordenadas[1] = 3;
    break;
    case 81:
    coordenadas[0] = 6;
    coordenadas[1] = 6;
    break;
    }
    indice = 0;
    matriz = preencheSubMatriz(coordenadas, subSudoku, matriz);
    }
    }
    return matriz;
    //return 0.0;
    }
    
    private int[][] preencheSubMatriz(int[] coordenadas, int[] subSudoku, int[][] matriz) {
    
    int indice = 0;
    for (int i = coordenadas[0]; i < coordenadas[0] + 3; i++) {
    for (int j = coordenadas[1]; j < coordenadas[1] + 3; j++) {
    matriz[i][j] = subSudoku[indice++];
    }
    }
    return matriz;
    }
    
    private int[] obterLinha(int[][] matriz, int indice) {
    int[] arrayAux = new int[matriz.length];
    for (int i = 0; i < matriz.length; i++) {
    if (i == indice) {
    for (int j = 0; j < matriz.length; j++) {
    arrayAux[j] = matriz[i][j];
    }
    }
    continue;
    }
    return arrayAux;
    }
    
    private int[] obterColuna(int[][] matriz, int indice) {
    int[] arrayAux = new int[matriz.length];
    for (int i = 0; i < matriz.length; i++) {
    if (i == indice) {
    for (int j = 0; j < matriz.length; j++) {
    arrayAux[j] = matriz[j][i];
    }
    }
    }
    return arrayAux;
    }
    //fim: codigo repetido
     */
}