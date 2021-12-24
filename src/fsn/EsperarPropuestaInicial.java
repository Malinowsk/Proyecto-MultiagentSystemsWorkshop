package fsn;

import fsm.MCPBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuestaInicial extends Behaviour {

    @Override
    public void action() {
        ACLMessage prop_ini = myAgent.receive(
                MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchInReplyTo("")));

        if (prop_ini != null) {
            this.getDataStore().put("Mensaje entrante", prop_ini);
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
