package Ontologia;
import jade.content.Predicate;

public class EsMiZeuthen implements Predicate{

    Float valor;

    public EsMiZeuthen() {
        this.valor = 0.0f;
    }

    public EsMiZeuthen(Float valor) {
        this.valor = valor;
    }

    public Float getValor() {
        return valor;
    }
    public void setValor(Float valor) {
        this.valor = valor;
    }
}
