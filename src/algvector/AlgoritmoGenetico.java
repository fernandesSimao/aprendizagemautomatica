package algvector;

//import gui.JanelaSudoku;
import java.util.Random;
import input.StandardInput;
import java.util.LinkedList;
import java.util.List;
import principal.ClassePrincipal;
import gui.*;

public class AlgoritmoGenetico {


    public static Random aleatorio;
    public static final int DIMENSAO = 9;
    public int tamanhoPopulacao;
    public int tamanhoIndividuos;
    public int maximoGeracoes;
    private Populacao populacaoActual;
    private Populacao proximaPopulacao;
    private MetodoSeleccao metodoSeleccao;
    private Recombinacao operadorRecombinacao;
    private Mutacao operadorMutacao;
    private int geracao;    //Estatísticas
    private Individuo melhorIndividuoGeracao;
    private int numOcorrenciasMesmoFitness, numMutacoesEspeciais = 0;
    private double melhorIndividuoGeracaoAnterior, antigaMutacao;
    private double mediaGeracao;
    private Individuo melhorIndividuoRun;
    private int geracaoMelhorIndividuoRun;
    private JanelaSudoku janelaSudoku;
    private boolean cancelou = false;
    private int[][] enigma;
    

    public AlgoritmoGenetico(JanelaSudoku _janelaSudoku, long seed, int[][] _enigma, int tamanhoPopulacao, int tamanhoIndividuos, int maximoGeracoes, MetodoSeleccao metodoSeleccao, Recombinacao operadorRecombinacao, Mutacao operadorMutacao, int elite) {
    //public AlgoritmoGenetico(long seed, int tamanhoPopulacao, int tamanhoIndividuos, int maximoGeracoes, MetodoSeleccao metodoSeleccao, Recombinacao operadorRecombinacao, Mutacao operadorMutacao, int elite) {
        this.janelaSudoku = _janelaSudoku;
        aleatorio = new Random(seed);
        this.enigma = _enigma;
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.tamanhoIndividuos = tamanhoIndividuos;
        this.maximoGeracoes = maximoGeracoes;
        this.metodoSeleccao = metodoSeleccao;
        this.operadorRecombinacao = operadorRecombinacao;
        this.operadorMutacao = operadorMutacao;

        this.melhorIndividuoGeracaoAnterior = 0;
        this.numOcorrenciasMesmoFitness = 0;
        
//        janela.setVisible(true);
//        janela.trataString("Sudoku S&T");
        
    }

    public Individuo executar() {
        populacaoActual = new Populacao(tamanhoPopulacao, tamanhoIndividuos, enigma);
        proximaPopulacao = new Populacao(tamanhoPopulacao);
        

        geracao = 0;
        avaliar(populacaoActual);
        while (!criterioParagem(populacaoActual, geracao) && !isCancelou()) {
            seleccao(populacaoActual, proximaPopulacao);
            recombinacao(proximaPopulacao);
            mutacao(proximaPopulacao);
            populacaoActual = criarNovaPopulacao(populacaoActual, proximaPopulacao);

            //allTogether();
            geracao++;
            avaliar(populacaoActual);
            
            if (melhorIndividuoGeracaoAnterior == melhorIndividuoGeracao.getFitness()) {
                numOcorrenciasMesmoFitness++;
            }
            melhorIndividuoGeracaoAnterior = melhorIndividuoGeracao.getFitness();

            
//            if (numOcorrenciasMesmoFitness >= 400) {
//                //antigaMutacao = operadorMutacao.getProbabilidade();
//                //operadorMutacao.setProbabilidadePerc(2);
//                //numMutacoesEspeciais = 5;
//                //numOcorrenciasMesmoFitness = 0;
//                
//                populacaoActual = null;
//                proximaPopulacao = null;
//                populacaoActual = new Populacao(tamanhoPopulacao, tamanhoIndividuos);
//                proximaPopulacao = new Populacao(tamanhoPopulacao);
//                numOcorrenciasMesmoFitness = 0;
//            }
//            if(numMutacoesEspeciais > 0) {
//                numMutacoesEspeciais--;
//                if(numMutacoesEspeciais <= 0) {
//                    operadorMutacao.setProbabilidade(antigaMutacao);
//                }
//            }
        }
        return melhorIndividuoRun;
    }

    private void allTogether() {
        LinkedList<Individuo> novaPopulacao = new LinkedList<Individuo>();

        seleccao(populacaoActual, proximaPopulacao);
        populacaoActual = criarNovaPopulacao(populacaoActual, proximaPopulacao);

        /* Recombinação. */
        for (int i = 0; i < tamanhoPopulacao - 1; i += 2) {
            Individuo filho1 = populacaoActual.getIndividuo(i).clone();
            Individuo filho2 = populacaoActual.getIndividuo(i + 1).clone();

            novaPopulacao.add(populacaoActual.getIndividuo(i));
            novaPopulacao.add(populacaoActual.getIndividuo(i + 1));

            operadorRecombinacao.executar(filho1, filho2);

            novaPopulacao.add(filho1);
            novaPopulacao.add(filho2);
        }

        /* Mutação. */
        Populacao pp = new Populacao(novaPopulacao.size());
        pp.setPopulacao(novaPopulacao);
        mutacao(pp);

        /* Avaliação. */
        avaliar(pp);

        /* Seleccção. */
        populacaoActual.setPopulacao(Populacao.seleccionaPopulacaoElite(pp, pp.getTamanho() * (populacaoActual.getTamanho() / pp.getTamanho())));

    //seleccao(populacaoActual, proximaPopulacao);
    //populacaoActual = criarNovaPopulacao(populacaoActual, proximaPopulacao);
    }

    private void avaliar(Populacao populacao) {
        //melhorFitnessGeracaoAnterior = melhorFitnessGeracaoActual;

        double somaFitness = 0;

        melhorIndividuoGeracao = (Individuo) populacao.getIndividuo(0).clone();

        for (int i = 0; i < populacao.getTamanho(); i++) {
            somaFitness += populacao.getIndividuo(i).calculaFitness();
            if (melhorIndividuoGeracao.getFitness() > populacao.getIndividuo(i).getFitness()) {
                melhorIndividuoGeracao = (Individuo) populacao.getIndividuo(i).clone();
            }
        }

        mediaGeracao = somaFitness / (double) tamanhoPopulacao;

        if (geracao == 0 || melhorIndividuoRun.getFitness() > melhorIndividuoGeracao.getFitness()) {
            melhorIndividuoRun = (Individuo) melhorIndividuoGeracao.clone();
            geracaoMelhorIndividuoRun = geracao;
        }
        mostra();

        //melhorFitnessGeracaoActual = melhorIndividuoGeracao.getFitness();
    }

    private void seleccao(Populacao populacaoActual, Populacao proximaPopulacao) {
        metodoSeleccao.executar(populacaoActual, proximaPopulacao);
    //
    }

    private boolean criterioParagem(Populacao populacao, int geracaoActual) {
        if (geracaoActual == maximoGeracoes) {
            return true;
        }
        //if(geracaoActual == 2000){
        //    restartPopulation();
        //}
        for (int i = 0; i < populacao.getTamanho(); i++) {
            if (populacao.getIndividuo(i).solucaoFinal()) {
//                System.out.println("\nSOLUÇÃO FINAL:\n");
//                System.out.println(populacao.getIndividuo(i).toString());
//                System.out.println("\nSOLUÇÃO FINAL:\n");
                janelaSudoku.mostraJanelaGreatSuccess();
                return true;
            }
        }
        return false;
    }

    private void restartPopulation() {
        aleatorio.setSeed(System.nanoTime());

        this.populacaoActual = null;
        this.proximaPopulacao = null;
        this.populacaoActual = new Populacao(tamanhoPopulacao, tamanhoIndividuos, enigma);
        this.proximaPopulacao = new Populacao(tamanhoPopulacao);
    }

    void recombinacao(Populacao populacao) {
        for (int i = 0; i < populacao.getTamanho() - 1; i += 2) {
            if (aleatorio.nextDouble() < operadorRecombinacao.getProbabilidade()) {
                operadorRecombinacao.executar(populacao.getIndividuo(i), populacao.getIndividuo(i + 1));
            }
        }
    }

    void mutacao(Populacao populacao) {
        for (int i = 0; i < populacao.getTamanho(); i++) {
            operadorMutacao.executar(populacao.getIndividuo(i));
        }
    }

    public Populacao criarNovaPopulacao(Populacao populacaoActual, Populacao proximaPopulacao) {
        Populacao aux = proximaPopulacao;
        this.proximaPopulacao = populacaoActual;
        return aux;
    }

    private void mostra() {
        int contador = 0;
        //System.out.println(populacaoActual);
        /*
        System.out.println("Melhor indivíduo da geração actual\n" + melhorIndividuoGeracao);
        System.out.println("\nMédia da geração actual: " + mediaGeracao);
        
        System.out.println("\nMelhor indivíduo do run (geração: " + geracaoMelhorIndividuoRun + ")");
        System.out.println(melhorIndividuoRun);
        
        System.out.println("\n**************************************************************************\n");
        StandardInput.readString();
         */
        
        janelaSudoku.setAreaTextoSudoku(melhorIndividuoRun.toString());
        janelaSudoku.setInfoBoxes(Integer.toString(geracao), Double.toString(melhorIndividuoGeracao.getFitness()), Double.toString(melhorIndividuoRun.getFitness()));

        //System.out.println("Geração: " + Integer.toString(geracao) + " | Melhor Fitness Geração: " + melhorIndividuoGeracao.getFitness() + " | Melhor Fitness Total: " + melhorIndividuoRun.getFitness());
    }

    public boolean isCancelou() {
        return cancelou;
    }

    public void setCancelou(boolean cancelou) {
        this.cancelou = cancelou;
    }
}
