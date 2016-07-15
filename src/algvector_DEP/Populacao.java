package algvector_DEP;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Populacao {

    private Individuo[] individuos;
    private double total;
    private double media;
    private int[][] enigma;

    public Populacao(int tamanhoPopulacao) {
        individuos = new Individuo[tamanhoPopulacao];
    }

    public Populacao(int tamanhoPopulacao, int[][] _enigma) {
        this.enigma = _enigma;
        individuos = new Individuo[tamanhoPopulacao];
        for (int i = 0; i < tamanhoPopulacao; i++) {
            individuos[i] = new Individuo(enigma);
        }
    }

    public boolean setPopulacao(Collection<Individuo> c) {
        if (c.size() != individuos.length) {
            return false;
        }
        c.toArray(individuos);
        return true;
    }

    //para o metodo de seleccao torneio.
    public static final List<Individuo> seleccionaPopulacaoElite(Populacao populacao, int tamanhoPopulacao) {
        LinkedList<Individuo> lista = new LinkedList<Individuo>();
        StringBuffer sb = new StringBuffer();

        //se o fitness do primeiro individuo for melhor que o fitness do segundo
        if (populacao.getIndividuo(0).getFitness() < populacao.getIndividuo(1).getFitness()) {
            lista.add(populacao.getIndividuo(0).clone());   //insere 1º o 1º individuo e dps o 2º individuo
            lista.add(populacao.getIndividuo(1).clone());
        } else {
            lista.add(populacao.getIndividuo(1).clone());   //insere 1º o 2º individuo e dps o 1º individuo
            lista.add(populacao.getIndividuo(0).clone());
        }

        //para cada individuo da populacao
        for (int i = 2; i < populacao.getTamanho(); i++) {
            //Percorre a lista até à última posição, se não encontrar um valor superior insere o indivídio no fim.
            for (int j = 1; j <= lista.size(); j++) {
                //se o fitness do corrente individuo da populacao for melhor que o individuo corrente da lista
                if (populacao.getIndividuo(i).getFitness() <= lista.get(j - 1).getFitness()) {
                    //insere o individuo na lista 
                    lista.add(j - 1, populacao.getIndividuo(i).clone());
                    break;
                } else {
                    if (j == lista.size()) {
                        lista.addLast(populacao.getIndividuo(i).clone());
                        break;
                    }
                }
            }   //fim 2º for
        }   //fim 1º for
        
        
        /* Imprime o FITNESS de cada Indivíduo. 
        for (int i = 0; i < lista.size(); i++) {
            //sb.append("I: " + i + " - " + lista.get(i).getFitness());
            sb.append(lista.get(i).toString());
            sb.append("\n");
        }
        System.out.println(sb.toString());
        */
        return lista.subList(0, tamanhoPopulacao);
    }

    public void setIndividuo(Individuo individuo, int indice) {
        individuos[indice] = individuo;
    }

    public Individuo getIndividuo(int indice) {
        return individuos[indice];
    }

    public int getTamanho() {
        return individuos.length;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int g = 0; g < individuos.length; g++) {
            sb.append(individuos[g] + "\n");
        }

        return sb.toString();
    }
}
