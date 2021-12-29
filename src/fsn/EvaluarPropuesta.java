package fsn;

import Ontologia.PedirComida;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


public class EvaluarPropuesta extends Behaviour {

    int event = 0;

    public EvaluarPropuesta (){
    }

    @Override
    public void action() {

        ACLMessage msg = (ACLMessage) getDataStore().get("Mensaje entrante"); // fijarse que pasa para initiator

        try {

            String[] comidas = (String[]) getDataStore().get("ArregloComidas");
            Integer[] utilidades = (Integer[]) getDataStore().get("ArregloUtilidades");

            // extraer el contenido del mensaje que nos propusieron
            ContentElement ce = myAgent.getContentManager().extractContent(msg);
            Action ac = (Action) ce;
            PedirComida pc = (PedirComida) ac.getAction();
            String contenido = pc.getComida().getNombre();

            int i = 0;
            while(!contenido.equals(comidas[i])){
                i++;
            }
            int indiceContenido = i;

            // indice de mi mejor propuesta
            Integer indiceMejorPropuesta = (Integer) getDataStore().get("IndiceComidas");

            if(indiceMejorPropuesta==null)
                indiceMejorPropuesta=0;

            if (utilidades[indiceMejorPropuesta] <= utilidades[indiceContenido]) {

                ACLMessage resp = msg.createReply();
                resp.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                resp.setContent("Aceptado");
                event = 0;
                // Envío la respuesta
                myAgent.send(resp);
                this.getDataStore().put("MensajeSaliente", resp);
            } else {

                ACLMessage resp = msg.createReply();
                resp.setPerformative(ACLMessage.REJECT_PROPOSAL);
                event = 1;
                // Envío la respuesta
                this.getDataStore().put("PropuestaDelOtro", contenido);
                this.getDataStore().put("proponedor", false);
                this.getDataStore().put("MensajeSaliente", resp);
                myAgent.send(resp);
            }

        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean done() {
        return true;
    }

    @Override
    public int onEnd() {
        return event;
    }
}
