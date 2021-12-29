package fsn;

import Ontologia.PedirComida;
import fsn.FSMProtocolo;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EsperarPropuestaInicial extends Behaviour {

    private String[] comidas = {"Pollo","Fideos","Milanesa","Pizza"};
    private Integer[] utilidades = {7,4,2,2};
    private boolean recibido = false;

    public EsperarPropuestaInicial(DataStore ds) {
        ds.put("ArregloComidas", comidas);
        ds.put("ArregloUtilidades", utilidades);
        setDataStore(ds);
    }

    @Override
    public void action() {

        Codec codec = (Codec) getDataStore().get("Codec");
        Ontology ontology = (Ontology) getDataStore().get("Ontology");

        ACLMessage prop_ini = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),MessageTemplate.MatchOntology(ontology.getName())));
        if (prop_ini != null) {
            recibido=true;
            getDataStore().put("Mensaje entrante", prop_ini);
            myAgent.addBehaviour(new FSMProtocolo(prop_ini,getDataStore()));
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return recibido;
    }

}
