package AgenteInitiator;

import jade.core.behaviours.Behaviour;

public class BehaviourFin extends Behaviour {

    @Override
    public void action() {
        System.out.println("Finalizo Initiator");
    }

    @Override
    public boolean done() {
        return true;
    }
}
