package ag1;

public class Gene {

    private int alelo;
    private boolean fixo;

    public Gene(int alelo, boolean fixo) {
        this.alelo = alelo;
        this.fixo = fixo;
    }

    public int getAlelo() {
        return alelo;
    }

    public void setAlelo(int alelo) {
        if (!fixo) {
            this.alelo = alelo;
        }
    }

    public boolean isFixo() {
        return fixo;
    }
}
