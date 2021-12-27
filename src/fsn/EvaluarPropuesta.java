package fsn;

import Ontologia.PedirComida;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class EvaluarPropuesta extends Behaviour {

    int event = 0;
    private String[] comidas;
    private Integer[] utilidades;

    public EvaluarPropuesta (){
        comidas = (String[]) getDataStore().get("ArregloComidas");
        utilidades = (Integer[]) getDataStore().get("ArregloUtilidades");
        System.out.println("u");
    }

    @Override
    public void action() {



        ACLMessage msg = (ACLMessage) getDataStore().get(FSMProtocolo.REQUEST_INITIAL);

        try {
            System.out.println("2");
            ContentElement ce;
            ce = myAgent.getContentManager().extractContent((ACLMessage)getDataStore().get("Mensaje entrante"));

            System.out.println("2");

            PedirComida pc = (PedirComida) ce;

            String contenido = pc.getComida().getNombre();

            Integer indiceMejorPropuesta = (Integer) getDataStore().get("IndiceComidas");

            System.out.println(indiceMejorPropuesta);


            int i = 0;
            while(contenido!= comidas[i]){
                i++;
            }
            int indiceContenido = i;

            System.out.println(indiceContenido);

            if (utilidades[indiceMejorPropuesta] <= utilidades[indiceContenido]) {

                ACLMessage resp = msg.createReply();
                resp.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                resp.setContent("Aceptado");
                event = 0;
                // Envío la respuesta
                myAgent.send(resp);

            } else {
                ACLMessage resp = msg.createReply();
                resp.setPerformative(ACLMessage.REJECT_PROPOSAL);
                resp.setContent(comidas[indiceMejorPropuesta]);
                this.getDataStore().put("PropuestaDelOtro", contenido);
                event = 1;
                // Envío la respuesta
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
