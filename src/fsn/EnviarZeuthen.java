package fsn;

import Ontologia.Comida;
import Ontologia.EsMiZeuthen;
import Ontologia.PedirComida;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class EnviarZeuthen extends Behaviour {


    private String[] comidas;
    private Integer[] utilidades;


    public EnviarZeuthen (){

    }

    @Override
    public void action() {

        String[] comidas = (String[]) getDataStore().get("ArregloComidas");
        Integer[] utilidades = (Integer[]) getDataStore().get("ArregloUtilidades");

        String propuestaDelOtro =(String) getDataStore().get("PropuestaDelOtro");

        Integer indiceMejorPropuesta = (Integer) getDataStore().get("IndiceComidas");
                                            // agente1 ->0 ; agente2 -> null(0)
                                            // agente1 ->0(1) ; agente1 ->0
                                            // agente1 ->1 ; agente2 ->0(1)
        if(indiceMejorPropuesta==null)
            indiceMejorPropuesta=0;

        int i = 0;
        float zeuthen;

        if (propuestaDelOtro == null)
            zeuthen=1;
        else {


            while (!propuestaDelOtro.equals(comidas[i])) {

                i++;
            }
            Integer indiceContenidoDelOtro = i;


            zeuthen = ((float) utilidades[indiceMejorPropuesta] - (float) utilidades[indiceContenidoDelOtro]) / (float) utilidades[indiceMejorPropuesta];
        }

        ACLMessage msg = (ACLMessage) getDataStore().get("Mensaje entrante");

        ACLMessage resp = msg.createReply();
        resp.setPerformative(ACLMessage.INFORM);


        Codec codec = (Codec) getDataStore().get("Codec");
        Ontology ontology = (Ontology) getDataStore().get("Ontology");

        resp.setLanguage(codec.getName());
        resp.setOntology(ontology.getName());


        try {
            myAgent.getContentManager().fillContent(resp,new EsMiZeuthen(zeuthen));
        } catch (Codec.CodecException | OntologyException e) { e.printStackTrace(); }


        this.getDataStore().put("ZeuthenNuestro", zeuthen);

        if ((boolean) getDataStore().get("proponedor"))
            getDataStore().put("IndiceComidas", indiceMejorPropuesta+1 );

        myAgent.send(resp);

    }

    @Override
    public boolean done() {
        return true;
    }
}
