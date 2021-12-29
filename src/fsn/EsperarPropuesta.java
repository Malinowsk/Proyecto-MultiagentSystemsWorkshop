package fsn;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuesta extends Behaviour {

    int event = 1;
    boolean recibido = false;

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchPerformative(ACLMessage.CANCEL)));

        if (msg != null) {  // Si recibi el mensaje, lo proceso
            recibido = true;
            if(msg.getPerformative() == ACLMessage.CANCEL){
                event = 0;
            }
            else{
                this.getDataStore().put("Mensaje entrante", msg);
                event = 1;
            }
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

    @Override
    public int onEnd() {
        return event;
    }

}
