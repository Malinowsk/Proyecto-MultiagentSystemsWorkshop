package fsn;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;

public class EnviarPropuesta extends Behaviour {

    private String[] comidas;
    private Integer event = 0;
    private int comidaActual = 0;
    private AID idReceptor;
    String comidaPropuesta = "";
    private boolean esPropuestaInicial = true;


    public EnviarPropuesta (){
        if((AID) getDataStore().get(FSMProtocolo.AID_OPONENTE) == null){
            esPropuestaInicial=false;
        }
        else
        {
            idReceptor = (AID) getDataStore().get(FSMProtocolo.AID_OPONENTE);
        }
        comidas = (String[]) getDataStore().get("ArregloComidas");
    }

    @Override
    public void action() {

        if (comidaActual < comidas.length){
            comidaPropuesta = comidas[comidaActual];
            comidaActual++;
        }else{
            event = 1;
        }
        System.out.println("Menu propuesto: " + comidaPropuesta);
        getDataStore().put("IndiceComidas", comidaActual);

        if(esPropuestaInicial){

            ACLMessage prop = new ACLMessage(ACLMessage.PROPOSE);
            prop.addReceiver(idReceptor);
            prop.setConversationId("CONV-" + myAgent.getName());
            prop.setReplyWith(myAgent.getName() + System.currentTimeMillis());
            prop.setContent(comidaPropuesta);

            this.getDataStore().put("Propuesta", prop);

            esPropuestaInicial = false;

            myAgent.send(prop);
        }
        else
        {
            ACLMessage msg = (ACLMessage) getDataStore().get("Mensaje entrante");

            ACLMessage resp = msg.createReply();
            resp.setPerformative(ACLMessage.PROPOSE);
            resp.setConversationId("CONV-" + myAgent.getName());
            resp.setReplyWith(myAgent.getName() + System.currentTimeMillis());
            resp.setContent(comidaPropuesta);

            this.getDataStore().put("Propuesta", resp);

            myAgent.send(resp);
        }
    }

    @Override
    public boolean done() {
        return true;
    }  // Se ejecuta y termina

    @Override
    public int onEnd() {
        return event;
    }   // ls condicion

}
