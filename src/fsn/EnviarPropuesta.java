package fsn;

import Ontologia.Comida;
import Ontologia.PedirComida;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;

public class EnviarPropuesta extends Behaviour {

    private String[] comidas;
    private Integer event = 0;
    private int comidaActual = 0;
    private AID idReceptor;
    String comidaPropuesta = "";
    private boolean esPropuestaInicial = true;


    public EnviarPropuesta (DataStore ds){


        if((AID) ds.get(FSMProtocolo.AID_OPONENTE) == null){
            esPropuestaInicial=false;
        }
        else
        {
            idReceptor = (AID) ds.get(FSMProtocolo.AID_OPONENTE);
        }
        comidas = (String[]) ds.get("ArregloComidas");
        setDataStore(ds);
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

            Codec codec = (Codec) getDataStore().get("Codec");
            Ontology ontology = (Ontology) getDataStore().get("Ontology");

            prop.setLanguage(codec.getName());
            prop.setOntology(ontology.getName());

            Comida comida = new Comida(comidaPropuesta);

            try {
                myAgent.getContentManager().fillContent(prop,new PedirComida(comida));
            } catch (Codec.CodecException | OntologyException e) { e.printStackTrace(); }

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

            Codec codec = (Codec) getDataStore().get("Codec");
            Ontology ontology = (Ontology) getDataStore().get("Ontology");

            resp.setLanguage(codec.getName());
            resp.setOntology(ontology.getName());

            Comida comida = new Comida(comidaPropuesta);

            try {
                myAgent.getContentManager().fillContent(resp,new PedirComida(comida));
            } catch (Codec.CodecException | OntologyException e) { e.printStackTrace(); }

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
