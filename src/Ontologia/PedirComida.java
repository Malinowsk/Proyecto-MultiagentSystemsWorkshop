package Ontologia;

import jade.content.AgentAction;

public class PedirComida implements AgentAction {

    Comida comida;
    String lugar;


    public PedirComida(Comida comida) {
        this.comida = comida;
    }

    public Comida getComida() {
        return comida;
    }
    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
