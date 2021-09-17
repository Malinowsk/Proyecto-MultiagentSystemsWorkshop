package AgenteResponder;

import jade.core.Agent;

public class AgenteResponder extends Agent {

    protected void setup() {
        this.addBehaviour(new FSMResponder());
    }
}
