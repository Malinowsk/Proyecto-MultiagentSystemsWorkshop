package AgenteInitiator;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class BehaviourPropose extends Behaviour {

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private int comidaActual = 0;
    String comidaPropuesta = "";

    @Override
    public void action() {

        if (comidaActual < comidas.length){
            comidaPropuesta = comidas[comidaActual];
            comidaActual++;
        }else{
            comidaPropuesta = comidas[0];
            comidaActual = 1;
        }
        System.out.println("Propongo: " + comidaPropuesta);

        ACLMessage prop = new ACLMessage(ACLMessage.PROPOSE);
        prop.addReceiver(new AID ("AR", AID.ISLOCALNAME));
        prop.setConversationId("CONV-" + myAgent.getName());
        prop.setReplyWith(myAgent.getName() + System.currentTimeMillis());
        prop.setContent(comidaPropuesta);

        this.getDataStore().put("Propuesta", prop);

        myAgent.send(prop);
    }

    @Override
    public boolean done() {
        return true;
    }
}
