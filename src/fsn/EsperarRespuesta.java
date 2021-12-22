package fsn;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarRespuesta extends Behaviour {

    boolean recibido = false;
    int event = 0;

    @Override
    public void action() {

        ACLMessage msgDataStore = (ACLMessage) getDataStore().get("Propuesta");

        ACLMessage msg = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(msgDataStore.getConversationId()),
                MessageTemplate.MatchInReplyTo(msgDataStore.getReplyWith())));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            System.out.println("Respuesta del cliente: " + msg.getContent());
            recibido = true;

            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                event = 0;
            } else {
                event = 1;
            }

        } else {
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

    @Override
    public int onEnd() {
        return event;
    }
}
