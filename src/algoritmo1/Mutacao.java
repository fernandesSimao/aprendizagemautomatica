package algoritmo1;

public abstract class Mutacao extends OperadorGenetico {

    public Mutacao(double probabilidade) {
        super(probabilidade);
    }

    public abstract void executar(Individuo individuo);
}