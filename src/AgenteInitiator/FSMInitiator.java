package AgenteInitiator;

import AgenteInitiator.BehaviourFin;
import AgenteInitiator.BehaviourPropose;
import AgenteInitiator.BehaviourReceive;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;

public class FSMInitiator extends FSMBehaviour {

    public FSMInitiator() {
        DataStore ds = new DataStore();
        BehaviourPropose ePro = new BehaviourPropose();
        ePro.setDataStore(ds);
        BehaviourReceive eReceive = new BehaviourReceive();
        eReceive.setDataStore(ds);
        BehaviourFin eFin = new BehaviourFin();
        eFin.setDataStore(ds);

        this.registerFirstState(ePro, "Propone");
        this.registerState(eReceive, "Recibe");
        this.registerLastState(eFin, "Fin");

        // TRANSICIONES
        String[] toReset = {"Recibe"};

        this.registerDefaultTransition("Propone", "Recibe");

        this.registerTransition("Recibe", "Propone", 1, toReset);
        this.registerTransition("Recibe", "Fin", 0);

    }
}
