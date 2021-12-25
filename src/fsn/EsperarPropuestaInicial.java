package fsn;

import fsn.FSMProtocolo;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuestaInicial extends Behaviour {

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private Integer[] utilidades = {7,4,2,2};

    @Override
    public void action() {
        ACLMessage prop_ini = myAgent.receive(
                MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchInReplyTo("")));

        if (prop_ini != null) {
            this.getDataStore().put("Mensaje entrante", prop_ini);
            myAgent.addBehaviour(new FSMProtocolo(prop_ini,comidas,utilidades));
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return true;
    }

}
