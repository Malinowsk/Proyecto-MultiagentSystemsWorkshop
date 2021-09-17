package AgenteResponder;

import jade.core.behaviours.Behaviour;

public class BehaviourFin extends Behaviour {

    @Override
    public void action() {
        System.out.println("Finalizo Responder");
    }

    @Override
    public boolean done() {
        return true;
    }
}
