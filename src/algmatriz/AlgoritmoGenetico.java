package algmatriz;

import gui.JanelaSudoku;
import principal.IInterface;

import java.util.Random;
import java.util.LinkedList;

public class AlgoritmoGenetico {

    public static Random aleatorio;
    public int tamanhoPopulacao;
    public int maximoGeracoes;
    private long seed;
    private Populacao populacaoActual;
    private Populacao proximaPopulacao;
    private MetodoSeleccao metodoSeleccao;
    private Recombinacao operadorRecombinacao;
    private Mutacao operadorMutacao;
    private int geracao;    //Estatísticas
    private Individuo melhorIndividuoGeracao;
    private int numOcorrenciasMesmoFitness, numMutacoesEspeciais = 0;
    private double melhorIndividuoGeracaoAnterior, antigaMutacao;
    double mediaGeracao;
    Individuo melhorIndividuoRun;
    int geracaoMelhorIndividuoRun;
    private IInterface janelaSudoku;
    private boolean cancelou = false;
    private int[][] enigma;
    private String nomeFx;

    public AlgoritmoGenetico(IInterface janelaSudoku, long seed, int[][] _enigma, int tamanhoPopulacao, int maximoGeracoes, MetodoSeleccao metodoSeleccao, Recombinacao operadorRecombinacao, Mutacao operadorMutacao, String nomeFx) {
        this.janelaSudoku = janelaSudoku;
        aleatorio = new Random(seed);
        this.seed = seed;
        this.enigma = _enigma;
        this.tamanhoPopulacao = tamanhoPopulacao;        
        this.maximoGeracoes = maximoGeracoes;
        this.metodoSeleccao = metodoSeleccao;
        this.operadorRecombinacao = operadorRecombinacao;
        this.operadorMutacao = operadorMutacao;
        this.nomeFx = nomeFx;
        
        
        this.melhorIndividuoGeracaoAnterior = 0;
        this.numOcorrenciasMesmoFitness = 0;
    }

    public Individuo executar() {
        populacaoActual = new Populacao(tamanhoPopulacao, enigma);
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
            
            
            numOcorrenciasMesmoFitness = melhorIndividuoGeracaoAnterior == melhorIndividuoRun.getFitness() ? numOcorrenciasMesmoFitness + 1 : 0;
            melhorIndividuoGeracaoAnterior = melhorIndividuoRun.getFitness();
            
            if (numOcorrenciasMesmoFitness >= 400) {
                antigaMutacao = operadorMutacao.getProbabilidade();
                operadorMutacao.setProbabilidadePerc(1);
                numMutacoesEspeciais = 5;
                numOcorrenciasMesmoFitness = 0;
            }
            else if (numOcorrenciasMesmoFitness >= 1000) {	//TODO dead code
            	populacaoActual = null;
                proximaPopulacao = null;
                populacaoActual = new Populacao(tamanhoPopulacao, enigma);
                proximaPopulacao = new Populacao(tamanhoPopulacao);
                numOcorrenciasMesmoFitness = 0;
            }
            if(numMutacoesEspeciais-- > 0) {
                if(numMutacoesEspeciais <= 0) {
                    operadorMutacao.setProbabilidade(antigaMutacao);
                }
            }
        }
        return melhorIndividuoRun;
    }

    private void allTogether() {
        LinkedList<Individuo> novaPopulacao = new LinkedList<Individuo>();

        /* Selecção. */
        seleccao(populacaoActual, proximaPopulacao);
        this.populacaoActual = criarNovaPopulacao(populacaoActual, proximaPopulacao);

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

        this.populacaoActual.setPopulacao(Populacao.seleccionaPopulacaoElite(pp, pp.getTamanho() * (populacaoActual.getTamanho() / pp.getTamanho())));
    }

    private void avaliar(Populacao populacao) {
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
    }

    private void seleccao(Populacao populacaoActual, Populacao proximaPopulacao) {
        metodoSeleccao.executar(populacaoActual, proximaPopulacao);
    }

    private boolean criterioParagem(Populacao populacao, int geracaoActual) {
        if (geracaoActual == maximoGeracoes) {
        	janelaSudoku.imprimeGreatSuccess(false, this.nomeFx, geracao, melhorIndividuoRun.getFitness(), this.seed, this.tamanhoPopulacao, this.operadorRecombinacao.getProbabilidade(), this.operadorMutacao.getProbabilidade());
            return true;
        }
        //if(geracaoActual == 2000){
        //    restartPopulation();
        //}
        for (int i = 0; i < populacao.getTamanho(); i++) {
            if (populacao.getIndividuo(i).solucaoFinal()) {
            	
            	
            	
                janelaSudoku.imprimeGreatSuccess(true, this.nomeFx, geracao, melhorIndividuoRun.getFitness(), this.seed, this.tamanhoPopulacao, this.operadorRecombinacao.getProbabilidade(), this.operadorMutacao.getProbabilidade());
                return true;
            }
        }
        return false;
    }

    private void restartPopulation() {
        aleatorio.setSeed(System.nanoTime());

        this.populacaoActual = null;
        this.proximaPopulacao = null;
        this.populacaoActual = new Populacao(tamanhoPopulacao, enigma);
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
        //System.out.println(populacaoActual);
        /*
        System.out.println("Melhor indivíduo da geração actual\n" + melhorIndividuoGeracao);
        System.out.println("\nMédia da geração actual: " + mediaGeracao);
        
        System.out.println("\nMelhor indivíduo do run (geração: " + geracaoMelhorIndividuoRun + ")");
        System.out.println(melhorIndividuoRun);
        
        System.out.println("\n**************************************************************************\n");
        StandardInput.readString();
         */

        //System.out.println(melhorIndividuoRun);
        //System.out.println("Geração: " + geracao + " | Melhor Fitness Geração: " + melhorIndividuoGeracao.getFitness() + " | Melhor Fitness Total: " + melhorIndividuoRun.getFitness());
        
    	
    	janelaSudoku.imprimePuzzleAtual(melhorIndividuoRun.toString());
        janelaSudoku.imprimeInfoFitness(geracao, melhorIndividuoGeracao.getFitness(), melhorIndividuoRun.getFitness());		
    }
    
    public boolean isCancelou() {
        return cancelou;
    }

    public void setCancelou(boolean cancelou) {
        this.cancelou = cancelou;
    }
}
