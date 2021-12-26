package fsn;

import Ontologia.EsMiZeuthen;
import Ontologia.PedirComida;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RecibirZeuthen extends Behaviour {

    Boolean recibido = false;
    int event = 0;


    @Override
    public void action() {

        ACLMessage msgDataStore = (ACLMessage) getDataStore().get("");

        ACLMessage msg = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchConversationId(msgDataStore.getConversationId()),
                MessageTemplate.MatchInReplyTo(msgDataStore.getReplyWith())));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            System.out.println("Respuesta del cliente: " + msg.getContent());
            ContentElement ce = null;
            try {
                ce = myAgent.getContentManager().extractContent(msg);

                EsMiZeuthen pc = (EsMiZeuthen) ce;

                recibido = true;

                if (pc.getValor() > (float)getDataStore().get("ZeuthenNuestro")) {
                    event = 0;
                } else {
                    event = 1;
                }

            } catch (Codec.CodecException | OntologyException e) {
                e.printStackTrace();
            }

        } else {
            block();
        }

    }

    @Override
    public int onEnd() {
        return event;
    }   // ls condicion

    @Override
    public boolean done() {
        return recibido;
    }


    @Override
    public void reset() {
        recibido = false;
    }
}
