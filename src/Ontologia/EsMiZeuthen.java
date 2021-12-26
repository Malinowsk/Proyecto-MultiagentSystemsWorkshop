package Ontologia;
import jade.content.Predicate;

public class EsMiZeuthen implements Predicate{


    public EsMiZeuthen(Float valor) {
        this.valor = valor;
    }

    Float valor;

    public Float getValor() {
        return valor;
    }
    public void setValor(Float valor) {
        this.valor = valor;
    }
}
