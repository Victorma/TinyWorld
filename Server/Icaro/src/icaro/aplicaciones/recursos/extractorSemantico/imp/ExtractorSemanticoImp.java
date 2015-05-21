package icaro.aplicaciones.recursos.extractorSemantico.imp;

import dasi.util.LogUtil;
import gate.Corpus;
import gate.CorpusController;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class ExtractorSemanticoImp {

    private final File ficheroProcesador;
    private CorpusController extractor;
    private Corpus corpus = null;
    private HashSet tiposAnotacionesRelevantes;

    public ExtractorSemanticoImp(String rutaProcesador) throws PersistenceException {
        // validar  las rutas
        ficheroProcesador = new File(rutaProcesador);
        if (!ficheroProcesador.exists()) {
            throw new PersistenceException("El fichero especificado en la ruta : " + rutaProcesador + " No existe ");
        }
        // definimos un conjunto con las  anotaciones que nos interesa buscar
        tiposAnotacionesRelevantes = new HashSet<>();
        tiposAnotacionesRelevantes.add("Lookup");
        tiposAnotacionesRelevantes.add("InicioPeticion");
    }

    public void incializar() {
        try {
            // initialise GATE - this must be done before calling any GATE APIs
            Gate.init();
        } catch (GateException ex) {
            LogUtil.logger(ExtractorSemanticoImp.class, ex);
        }
        try {
            extractor = (CorpusController) PersistenceManager.loadObjectFromFile(ficheroProcesador);
        } catch (PersistenceException | IOException | ResourceInstantiationException ex) {
            LogUtil.logger(ExtractorSemanticoImp.class, ex);
        }
        try {
            corpus = Factory.newCorpus("BatchProcessApp Corpus");
        } catch (ResourceInstantiationException ex) {
            LogUtil.logger(ExtractorSemanticoImp.class, ex);
        }
        extractor.setCorpus(corpus);
    }

    public HashSet extraerAnotaciones(HashSet annotTypesRequired, String textoUsuario) {
        Document doc = null;
        try {
            doc = Factory.newDocument(textoUsuario);
        } catch (ResourceInstantiationException ex) {
            LogUtil.logger(ExtractorSemanticoImp.class, ex);
        }
        corpus.add(doc);
        try {
            extractor.execute();
        } catch (ExecutionException ex) {
            LogUtil.logger(ExtractorSemanticoImp.class, ex);
        }
        corpus.clear();
        if (annotTypesRequired == null) {
            annotTypesRequired = tiposAnotacionesRelevantes;
        }
        if (doc == null) {
            return new HashSet();
        } else {
            return new HashSet(doc.getAnnotations().get(annotTypesRequired));
        }
    }
}
