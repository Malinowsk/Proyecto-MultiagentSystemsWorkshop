package AgenteResponder;

import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;

public class FSMResponder extends FSMBehaviour {

    public FSMResponder() {
        DataStore ds = new DataStore();
        BehaviourReceive eRecive = new BehaviourReceive();
        eRecive.setDataStore(ds);
        BehaviourResponder eRes = new BehaviourResponder();
        eRes.setDataStore(ds);
        BehaviourFin eFin = new BehaviourFin();
        eFin.setDataStore(ds);
        this.registerFirstState(eRecive, "Receive");
        this.registerState(eRes, "Responder");
        this.registerLastState(eFin, "Fin");

        // TRANSICIONES
        String[] toReset = {"Receive"};

        this.registerDefaultTransition("Receive", "Responder", toReset);

        this.registerTransition("Responder", "Receive", 1);
        this.registerTransition("Responder", "Fin", 0);

    }
}
