package fsn;

import fsn.FSMProtocolo;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuestaInicial extends Behaviour {

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private Integer[] utilidades = {7,4,2,2};
    private DataStore ds;

    public EsperarPropuestaInicial(DataStore ds) {
        ds.put("ArregloComidas", comidas);
        ds.put("ArregloUtilidades", utilidades);
        this.ds = ds;
    }

    @Override
    public void action() {
        ACLMessage prop_ini = myAgent.receive(
                MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchInReplyTo("")));

        if (prop_ini != null) {
            ds.put("Mensaje entrante", prop_ini);
            myAgent.addBehaviour(new FSMProtocolo(prop_ini,ds));
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return true;
    }

}
