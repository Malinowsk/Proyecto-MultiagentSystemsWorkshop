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
        this.registerFirstState(eRecive, "Recive");
        this.registerState(eRes, "Responder");
        this.registerLastState(eFin, "Fin");

        // TRANSICIONES
        String[] toReset = {"Recive"};

        this.registerDefaultTransition("Recive", "Responder", toReset);

        this.registerTransition("Responder", "Recive", 1);
        this.registerTransition("Responder", "Fin", 0);

    }
}
