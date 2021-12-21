package Ontologia;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class MCPOntology extends Ontology {
    private static final long serialVersionUID = 314831391539979184L;

    // Nombre de la ontología
    public static final String ONTOLOGY_NAME = "ontologia-negociacion-comida";

    // Constantes, nombres de los esquemas y los slots (atributos) de la ontología
    public static final String COMIDA = "comida";
    public static final String PEDIR_COMIDA = "pedir-comida";
    private static final String NOMBRE_COMIDA = "nombre";
    private static final String TIPO_COMIDA = "tipoComida";
    private static final String INGREDIENTES = "ingredientes";
    private static final String LUGAR = "lugar";
    private static final String ES_MI_ZEUTHEN = "es-mi-zeuthen";
    private static final String VALOR = "valor";

    private static Ontology instance = new MCPOntology();
    private static SLCodec codecInstance = new SLCodec();

    public static Ontology getInstance() { return instance; }

    public static SLCodec getCodecInstance() { return codecInstance; }

    public MCPOntology() {
        super(ONTOLOGY_NAME, BasicOntology.getInstance());
        try {

            ConceptSchema cs = new ConceptSchema(COMIDA);
            cs.add(NOMBRE_COMIDA, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.MANDATORY);
            cs.add(TIPO_COMIDA, (PrimitiveSchema)getSchema(BasicOntology.DATE), PrimitiveSchema.OPTIONAL);
            cs.add(INGREDIENTES, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.OPTIONAL);
            add(cs, Comida.class);

            AgentActionSchema aas = new AgentActionSchema(PEDIR_COMIDA);
            aas.add(COMIDA, (ConceptSchema)getSchema(COMIDA), PrimitiveSchema.MANDATORY);
            aas.add(LUGAR, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.OPTIONAL);
            add(aas, PedirComida.class);

            PredicateSchema ps = new PredicateSchema(ES_MI_ZEUTHEN);
            ps.add(VALOR, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.MANDATORY);
            add(ps, EsMiZeuthen.class);
            // Comida, PedirComida, y EsMiZeuthen son las clases que implementan los esquemas de la ontología
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}