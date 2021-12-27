import Ontologia.MCPOntology;
import fsn.EsperarPropuestaInicial;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class AgenteResponder extends Agent {

    private Codec codec = new SLCodec();
    private Ontology ontology = MCPOntology.getInstance();

    protected void setup() {

        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        DataStore ds = new DataStore();

        ds.put("Codec", codec);
        ds.put("Ontology", ontology);

        this.addBehaviour(new EsperarPropuestaInicial(ds));

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("negociacion");
        sd.setName("comidas");
        sd.addLanguages(codec.getName());
        sd.addOntologies(ontology.getName());
        dfd.addServices(sd);
        dfd.addLanguages(codec.getName());
        dfd.addOntologies(ontology.getName());

        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }
}
