package fsn;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RecibirZeuthen extends Behaviour {

    Boolean recibido = false;


    @Override
    public void action() {

        ACLMessage msgDataStore = (ACLMessage) getDataStore().get("");

        ACLMessage msg = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(msgDataStore.getConversationId()),
                MessageTemplate.MatchInReplyTo(msgDataStore.getReplyWith())));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            System.out.println("Respuesta del cliente: " + msg.getContent());
            recibido = true;

            if (msg.getContent() > getDataStore().get("ZeuthenNuestro")) {
                event = 0;
            } else {
                event = 1;
            }

        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return recibido;
    }


    @Override
    public void reset() {
        recibido = false;
    }
}
