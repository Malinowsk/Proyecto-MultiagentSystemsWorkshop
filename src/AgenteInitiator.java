import fsn.FSMProtocolo;
import FIPA.AgentID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class AgenteInitiator extends Agent {

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private Integer[] utilidades = {5,4,2,1};


    protected void setup() {

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("negociacion");
        sd.setName("comidas");
        template.addServices(sd);
        try{
            DFAgentDescription[] result = DFService.search(this, template);
            if (result.length > 0){
                addBehaviour(new FSMProtocolo(result[0].getName(),comidas,utilidades));
            }
            else {
                addBehaviour(new SubscriptionInitiator(this,
                        DFService.createSubscriptionMessage(this, getDefaultDF(),
                                template, null)) {
                    protected void handleInform(ACLMessage inform) {
                        try {
                            DFAgentDescription[] result = DFService.decodeNotification(inform.getContent());
                            if (result[0].getAllServices().hasNext())
                                addBehaviour(new FSMProtocolo(result[0].getName(),comidas,utilidades));
                        } catch (FIPAException fe) { fe.printStackTrace();}
                    }
                });
            }
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }

    /*@Override
    protected void takeDown() {
        try {
            DFService.createCancelMessage(this, getDefaultDF(), ); /// Nos falta el ultimo parametro
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }*/

}
