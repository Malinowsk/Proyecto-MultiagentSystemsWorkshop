package fsn;

import jade.core.behaviours.Behaviour;

public class Acuerdo extends Behaviour {

    @Override
    public void action() {
        System.out.println("Hubo Acuerdo");
    }

    @Override
    public boolean done() {
        return true;
    }
}
