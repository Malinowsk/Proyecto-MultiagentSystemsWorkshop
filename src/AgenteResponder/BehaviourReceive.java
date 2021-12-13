package AgenteResponder;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourReceive extends Behaviour {

    boolean recibido = false;

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            recibido = true;
            this.getDataStore().put("Mensaje entrante", msg);
        }else{
                block();
            }

    }

    @Override
    public void reset() {
        recibido = false;
    }

    @Override
    public boolean done() {
        return recibido;
    }
}
