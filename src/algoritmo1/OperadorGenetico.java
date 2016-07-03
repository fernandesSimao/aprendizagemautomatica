package algoritmo1;

public class OperadorGenetico {

    protected double probabilidade;

    public OperadorGenetico(double probabilidade) {
        this.probabilidade = probabilidade;
    }

    public double getProbabilidade() {
        return probabilidade;
    }
    
    public void setProbabilidadePerc(double percentil) {
        this.probabilidade =this.probabilidade * percentil;
    }
    public void setProbabilidade(double _probabilidade) {
        this.probabilidade = _probabilidade;
    }
}
