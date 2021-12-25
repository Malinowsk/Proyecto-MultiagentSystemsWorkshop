package fsn;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnviarZeuthen extends Behaviour {


    private String[] comidas;
    private Integer[] utilidades;


    public EnviarZeuthen (){
        comidas = (String[]) getDataStore().get("ArregloComidas");
        utilidades = (Integer[]) getDataStore().get("ArregloUtilidades");
    }

    @Override
    public void action() {

        String propuestaDelOtro =(String) getDataStore().get("PropuestaDelOtro");
        Integer indiceMejorPropuesta = (Integer) getDataStore().get("IndiceComidas");

        int Zeuthen = 0;

        ACLMessage msg = (ACLMessage) getDataStore().get("Mensaje entrante");

        ACLMessage resp = msg.createReply();
        resp.setPerformative(ACLMessage.INFORM);
        resp.setConversationId("CONV-" + myAgent.getName());
        resp.setReplyWith(myAgent.getName() + System.currentTimeMillis());
        resp.setContent(zeuthen);

        this.getDataStore().put("ZeuthenNuestro", Zeuthen);

        myAgent.send(resp);


    }

    @Override
    public boolean done() {
        return true;
    }
}
