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

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza"};
    private Integer[] utilidades = {7,4,2,2};
    private DataStore ds;
    private boolean recibido = false;

    public EsperarPropuestaInicial(DataStore ds) {
        ds.put("ArregloComidas", comidas);
        ds.put("ArregloUtilidades", utilidades);
        this.ds = ds;
    }

    @Override
    public void action() {

        Codec codec = (Codec) ds.get("Codec");
        Ontology ontology = (Ontology) ds.get("Ontology");

        ACLMessage prop_ini = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchLanguage(codec.getName()),MessageTemplate.MatchOntology(ontology.getName())));
        if (prop_ini != null) {
            ContentElement ce;
            try {
                ce = myAgent.getContentManager().extractContent(prop_ini);
                PedirComida pc = (PedirComida) ce;
                String contenido = pc.getComida().getNombre();
                System.out.println("66");
            } catch (Codec.CodecException e) {
                e.printStackTrace();
            } catch (OntologyException e) {
                e.printStackTrace();
            }

            recibido=true;
            ds.put("Mensaje entrante", prop_ini);
            myAgent.addBehaviour(new FSMProtocolo(prop_ini,ds));
        }
        else
            block();
    }

    @Override
    public boolean done() {
        return recibido;
    }

}
