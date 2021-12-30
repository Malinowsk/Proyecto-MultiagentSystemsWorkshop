package fsn;

import jade.core.behaviours.Behaviour;

public class Acuerdo extends Behaviour {

    @Override
    public void action() {
        System.out.println("Hubo Acuerdo del comensal " + myAgent.getLocalName());
    }

    @Override
    public boolean done() {
        return true;
    }
}
