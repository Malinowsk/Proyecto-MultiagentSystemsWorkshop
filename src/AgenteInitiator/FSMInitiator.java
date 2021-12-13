package AgenteInitiator;

import AgenteInitiator.BehaviourFin;
import AgenteInitiator.BehaviourPropose;
import AgenteInitiator.BehaviourReceive;
import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;

public class FSMInitiator extends FSMBehaviour {

    public FSMInitiator(AID aid) {
        DataStore ds = new DataStore();
        BehaviourPropose ePro = new BehaviourPropose(aid);
        ePro.setDataStore(ds);
        BehaviourReceive eReceive = new BehaviourReceive();
        eReceive.setDataStore(ds);
        BehaviourFin eFin = new BehaviourFin();
        eFin.setDataStore(ds);

        this.registerFirstState(ePro, "Propose");
        this.registerState(eReceive, "Receive");
        this.registerLastState(eFin, "Fin");

        // TRANSICIONES
        String[] toReset = {"Receive"};

        this.registerDefaultTransition("Propose", "Receive");

        this.registerTransition("Receive", "Propose", 1, toReset);
        this.registerTransition("Receive", "Fin", 0);

    }
}
