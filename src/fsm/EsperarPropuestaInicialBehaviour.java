package fsm;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuestaInicialBehaviour extends Behaviour {

    @Override
    public void action() {
        ACLMessage prop_ini = myAgent.receive(
                MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchInReplyTo("")));

        if (prop_ini != null) {
            myAgent.addBehaviour(new MCPBehaviour(false, prop_ini));
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return true;
    }

}
