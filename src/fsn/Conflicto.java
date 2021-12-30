package fsn;

import jade.core.behaviours.Behaviour;

public class Conflicto extends Behaviour {

    @Override
    public void action() {
        System.out.println("Hubo Conflicto en el agente " + myAgent.getLocalName());
    }

    @Override
    public boolean done() {
        return true;
    }
}
