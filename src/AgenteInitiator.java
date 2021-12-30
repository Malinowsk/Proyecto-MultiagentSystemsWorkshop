import Ontologia.MCPOntology;
import fsn.FSMProtocolo;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class AgenteInitiator extends Agent {

    private String[] comidas = {"Milanesa","Fideos","Pollo","Pizza","Pure","Asado","Hamburguesa","sopa","Tarta","Empanadas"};
    private Integer[] utilidades = {19,16,15,13,8,8,6,5,1,0};
    private Codec codec = new SLCodec();
    private Ontology ontology = MCPOntology.getInstance();
    private DFAgentDescription mens ;

    protected void setup() {

        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("negociacion");
        sd.setName("comidas");
        sd.addLanguages(codec.getName());
        sd.addOntologies(ontology.getName());
        template.addServices(sd);
        template.addLanguages(codec.getName());
        template.addOntologies(ontology.getName());

        mens= template;
        DataStore ds = new DataStore();

        ds.put("ArregloComidas", comidas);
        ds.put("ArregloUtilidades", utilidades);
        ds.put("Codec", codec);
        ds.put("Ontology", ontology);
        try{
            DFAgentDescription[] result = DFService.search(this, template);
            if (result.length > 0){
                ds.put(FSMProtocolo.AID_OPONENTE, result[0].getName()); // seteamos en el ds el id del oponente
                addBehaviour(new FSMProtocolo(ds));
            }
            else {
                addBehaviour(new SubscriptionInitiator(this,
                        DFService.createSubscriptionMessage(this, getDefaultDF(),
                                template, null)) {
                    protected void handleInform(ACLMessage inform) {
                        try {
                            DFAgentDescription[] result = DFService.decodeNotification(inform.getContent());
                            if (result[0].getAllServices().hasNext()){
                                ds.put(FSMProtocolo.AID_OPONENTE, result[0].getName()); // seteamos en el ds el id del oponente
                                addBehaviour(new FSMProtocolo(ds));
                            }
                        } catch (FIPAException fe) { fe.printStackTrace();}
                    }
                });
            }
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }


    protected void takeDown() {
        DFService.createCancelMessage(this, getDefaultDF(), DFService.createSubscriptionMessage(this, getDefaultDF(), mens, null) ); /// Nos falta el ultimo parametro
    }

}
