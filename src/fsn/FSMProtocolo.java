package fsn;

import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class FSMProtocolo extends FSMBehaviour{

    public static final String AID_OPONENTE = "aid-oponente";
    public static final String REQUEST_INITIAL = "request-initial";
    private static final String ENVIAR_PROPUESTA = "enviar-propuesta";
    private static final String EVALUAR_PROPUESTA = "evaluar-propuesta";
    private static final String ESPERAR_RESPUESTA = "esperar_respuesta";
    private static final String ENVIAR_ZEUTHEN = "enviar_zeuthen";
    private static final String RECIBIR_ZEUTHEN = "recibir_zeuthen";
    private static final String ESPERAR_PROPUESTA = "esperar_propuesta";
    private static final String ACUERDO = "acuerdo";
    private static final String CONFLICTO = "conflicto";

    // INICIATOR
    public FSMProtocolo(DataStore ds) {

        this.crearFSM(ds, true);

    }

    // RESPONDER
    public FSMProtocolo(ACLMessage proposeInicial, DataStore ds) {

        ds.put(REQUEST_INITIAL, proposeInicial);

        this.crearFSM(ds, false);

    }

    private void crearFSM(DataStore ds, boolean ini) {

        // Instanciamos los comportamientos
        EnviarPropuesta enviar_propuesta = new EnviarPropuesta();
        enviar_propuesta.setDataStore(ds);

        EsperarRespuesta esperar_respuesta = new EsperarRespuesta();
        esperar_respuesta.setDataStore(ds);

        EnviarZeuthen enviar_zeuthen = new EnviarZeuthen();
        enviar_zeuthen.setDataStore(ds);

        RecibirZeuthen recibir_zeuthen = new RecibirZeuthen();
        recibir_zeuthen.setDataStore(ds);

        EsperarPropuesta esperar_propuesta = new EsperarPropuesta();
        esperar_propuesta.setDataStore(ds);

        EvaluarPropuesta evaluar_propuesta = new EvaluarPropuesta();
        evaluar_propuesta.setDataStore(ds);

        Acuerdo acuerdo = new Acuerdo();
        acuerdo.setDataStore(ds);

        Conflicto conflicto = new Conflicto();
        conflicto.setDataStore(ds);

        // Definir los estados de la FSM
        if (ini) { // Initiator
            this.registerFirstState(enviar_propuesta, ENVIAR_PROPUESTA);
            this.registerState(evaluar_propuesta, EVALUAR_PROPUESTA);
        }
        else { // Responder
            this.registerFirstState(evaluar_propuesta, EVALUAR_PROPUESTA);
            this.registerState(enviar_propuesta, ENVIAR_PROPUESTA);
        }

        this.registerState(esperar_respuesta, ESPERAR_RESPUESTA);

        this.registerState(enviar_zeuthen, ENVIAR_ZEUTHEN);

        this.registerState(recibir_zeuthen, RECIBIR_ZEUTHEN);

        this.registerState(esperar_propuesta, ESPERAR_PROPUESTA);

        this.registerLastState(acuerdo, ACUERDO);

        this.registerLastState(conflicto, CONFLICTO);

        // Definir transiciones

        String[] ToReset = {RECIBIR_ZEUTHEN, ESPERAR_RESPUESTA};

        this.registerTransition(ENVIAR_PROPUESTA, ESPERAR_RESPUESTA, 0);
        this.registerTransition(ENVIAR_PROPUESTA, CONFLICTO, 1); // No tengo más propuestas y debo conceder

        this.registerTransition(ESPERAR_RESPUESTA, ENVIAR_ZEUTHEN, 1 , ToReset); // Reject
        this.registerTransition(ESPERAR_RESPUESTA, ACUERDO, 0 , ToReset); // Accept

        this.registerDefaultTransition(ENVIAR_ZEUTHEN, RECIBIR_ZEUTHEN);

        this.registerTransition(RECIBIR_ZEUTHEN, ESPERAR_PROPUESTA, 1,ToReset); // Mi Z es Mayor
        this.registerTransition(RECIBIR_ZEUTHEN, ENVIAR_PROPUESTA, 0,ToReset); // Mi Z es Menor

        this.registerTransition(ESPERAR_PROPUESTA, EVALUAR_PROPUESTA, 0); // Recibi propuesta
        this.registerTransition(ESPERAR_PROPUESTA, CONFLICTO, 1); // Recibi Cancel

        this.registerTransition(EVALUAR_PROPUESTA, ENVIAR_ZEUTHEN, 1); // Reject
        this.registerTransition(EVALUAR_PROPUESTA, ACUERDO, 0); // Accept


    }

}