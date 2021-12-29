package fsn;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuesta extends Behaviour {

    boolean recibido = false;

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            recibido = true;
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+ msg.getSender());
            System.out.println("cont del mensaje " +msg.getContent());
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
