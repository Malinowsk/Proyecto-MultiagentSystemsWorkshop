package AgenteInitiator;

import AgenteResponder.FSMResponder;
import jade.core.Agent;

public class AgenteInitiator extends Agent {

    protected void setup() {
        this.addBehaviour(new FSMInitiator());
    }
}
