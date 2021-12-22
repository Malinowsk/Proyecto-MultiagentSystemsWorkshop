package fsm;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnviarPropuestaBehaviour extends Behaviour {   // Clase que representa el comportamiento de enviar una propuesta

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private int comidaActual = 0;
    private AID idReceptor;
    String comidaPropuesta = "";


    public EnviarPropuestaBehaviour (AID aid){ // contructor recibe como parametro el id del que recibe la propuesta
        this.idReceptor = aid;
    }

    @Override
    public void action() {

        if (comidaActual < comidas.length){
            comidaPropuesta = comidas[comidaActual];
            comidaActual++;
        }else{
            comidaPropuesta = comidas[0];
            comidaActual = 1;
        }
        System.out.println("Menu propuesto: " + comidaPropuesta);

        ACLMessage prop = new ACLMessage(ACLMessage.PROPOSE);
        prop.addReceiver(idReceptor);                           // El agente receptor debe llamarse AR
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

