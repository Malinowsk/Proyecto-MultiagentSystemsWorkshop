package AgenteResponder;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class BehaviourResponder extends Behaviour {

    int event = 0;

    @Override
    public void action() {

        ACLMessage msg = (ACLMessage) getDataStore().get("Mensaje entrante");

        if (Math.random() > 0.9) {

            ACLMessage resp = msg.createReply();
            resp.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            resp.setContent("Aceptado");
            event = 0;

            // Envío la respuesta
            myAgent.send(resp);

        } else {
            ACLMessage resp = msg.createReply();
            resp.setPerformative(ACLMessage.REJECT_PROPOSAL);
            resp.setContent("No aceptado");
            event = 1;

            // Envío la respuesta
            myAgent.send(resp);
        }
    }

    @Override
    public boolean done() {
        return true;
    }

    @Override
    public int onEnd() {
        return event;
    }
}
