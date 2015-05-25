package icaro.aplicaciones.informacion.minions;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArbolObjetivos {

    // ############################ ENUMERADOS Y CLASES #####################################
    public enum EstadoNodo {

        Pendiente, Resuelto, Validado, Realizado, Irresoluble
    }

    public class NodoArbol {

        private Subobjetivo obj;
        private NodoArbol padre;
        private List<NodoArbol> hijos;
        private EstadoNodo estado;

        private String owner;
        private List<String> failedOwners;

        protected NodoArbol(Subobjetivo obj, NodoArbol padre) {

            this.obj = obj;
            this.hijos = new ArrayList<NodoArbol>();
            this.failedOwners = new ArrayList<String>();
            this.estado = EstadoNodo.Pendiente;

            if (padre != null) {
                this.padre = padre;
                this.obj.setParent(padre.obj);
            }

        }

        public String getOwner() {
            return this.owner;
        }

        public void setNewOwner(String newOwner) {
            if (this.owner != null) {
                this.failedOwners.add(this.owner);
            }

            this.owner = newOwner;
            this.obj.setOwner(newOwner);
        }

        public void setSubobjetivo(Subobjetivo obj) {
            this.obj = obj;
        }

        public Subobjetivo getSubobjetivo() {
            return obj;
        }

        public void addHijo(Subobjetivo subobjetivo) {
            this.hijos.add(new NodoArbol(subobjetivo, this));
        }

        public List<NodoArbol> getHijos() {
            return this.hijos;
        }

        public boolean isReady() {
            boolean ready = true;

            for (NodoArbol nodo : hijos) {
                if (nodo.estado == EstadoNodo.Validado) {
                    ready = false;
                    break;
                }
            }

            return ready;
        }

        public NodoArbol getPadre() {
            return padre;
        }

        public void setPadre(NodoArbol padre) {
            this.padre = padre;
        }

        public void ownerFailedSolving() {
            if (this.owner != null) {
                this.failedOwners.add(this.owner);
            }

            this.owner = null;
            this.setEstado(EstadoNodo.Pendiente);
        }

        public EstadoNodo getEstado() {
            return estado;
        }

        public void setEstado(EstadoNodo estado) {
            if (this.estado == estado) // No state change
            {
                return;
            }

            // ########## Estados excepcionales #########
            if (this.estado == EstadoNodo.Realizado) {
                throw new IllegalArgumentException("No se permite transitar de realizado a cualquier otro estado.");
            }

            // ######### Transiciones ###########
            // ========= REALIZADO ==========
            if (estado == EstadoNodo.Realizado) {
                this.estado = EstadoNodo.Realizado;
                // ========= VALIDADO ===========
            } else if (estado == EstadoNodo.Validado) {
                // Si no hay hijos, se auto-valida
                boolean todosHijosValidados = hijos.size() == 0;
                for (NodoArbol hijo : hijos) {
                    // Al menos un hijo debe estar validado
                    if (hijo.getEstado() == EstadoNodo.Validado) {
                        todosHijosValidados = true;
                    }

                    // Aceptamos dos estados, pues que estén realizados o sean irresolubles nos da igual.
                    // Los nodos irresolubles hay que conservarlos para asegurarnos de que no reintentamos algo imposible.
                    if (!(hijo.getEstado() == EstadoNodo.Validado
                            || hijo.getEstado() == EstadoNodo.Realizado
                            || hijo.getEstado() == EstadoNodo.Irresoluble)) {
                        todosHijosValidados = false;
                        break;
                    }
                }

                if (todosHijosValidados) {
                    this.estado = EstadoNodo.Validado;
                    // Intentamos validar el padre propagando la validación al arbol completo
                    if (this.padre != null) {
                        this.padre.setEstado(EstadoNodo.Validado);
                    }
                }
                // ========= IRRESOLUBLE ==========  
            } else if (estado == EstadoNodo.Irresoluble) {
                this.estado = EstadoNodo.Irresoluble;
                if (this.padre != null) {
                    this.padre.setEstado(EstadoNodo.Pendiente);
                }

                // ========= PENDIENTE ========
            } else if (estado == EstadoNodo.Pendiente) {
                this.estado = EstadoNodo.Pendiente;
                if (this.padre != null && this.padre.getEstado() == EstadoNodo.Validado) {
                    this.padre.setEstado(EstadoNodo.Resuelto);
                }
                // ======== RESUELTO ========
            } else if (estado == EstadoNodo.Resuelto) {
                this.estado = EstadoNodo.Resuelto;
                if (this.padre != null && this.padre.getEstado() == EstadoNodo.Validado) {
                    this.padre.setEstado(EstadoNodo.Resuelto);
                }
            }
        }
    }

    public class ListaIntegrantes {

        private Set<String> listaNombres;

        public ListaIntegrantes() {
            listaNombres = new LinkedHashSet<String>();
        }

        public ListaIntegrantes(String nombre) {
            listaNombres = new LinkedHashSet<String>();
            listaNombres.add(nombre);
        }

        public boolean mezclarCon(ListaIntegrantes other) {
            int previous = listaNombres.size();
            listaNombres.addAll(other.listaNombres);
            return listaNombres.size() != previous;
        }

        public Set<String> getLista() {
            return listaNombres;
        }

        public void reenviarATodosSalvoA(String yo) throws Exception {
            ItfUsoRepositorioInterfaces repo = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

            // Genero un clon para reenviarlo y asegurar de que el agente acepta la nueva lista
            ListaIntegrantes clon = this.clona();

            for (String agente : this.listaNombres) {
                if (!agente.equalsIgnoreCase(yo)) {
                    MensajeSimple ms = new MensajeSimple(clon, yo, agente);
                    ((ItfUsoAgenteCognitivo) repo.obtenerInterfazUso(agente)).aceptaMensaje(ms);
                }
            }
        }

        private String jefeEncuestas;

        public String getJefeEncuestas() {
            if (jefeEncuestas == null && listaNombres.size() > 0) {
                jefeEncuestas = listaNombres.iterator().next();
            }

            return jefeEncuestas;

        }

        public void setJefeEncuestas(String nuevoJefe) {
            if (this.listaNombres.contains(nuevoJefe)) {
                this.jefeEncuestas = nuevoJefe;
            }
        }

        public ListaIntegrantes clona() {
            ListaIntegrantes clon = new ListaIntegrantes();
            clon.mezclarCon(this);
            return clon;
        }

        @Override
        public String toString() {
            return "ListaIntegrantes: " + listaNombres.toString();
        }
    }

    // #################################### ATRIBUTOS Y MÉTODOS ###################################
    public NodoArbol root;
    public ListaIntegrantes listaIntegrantes;

    public NodoArbol getRoot() {
        return root;
    }

    public void setRoot(NodoArbol root) {
        this.root = root;
    }

    public ListaIntegrantes getListaIntegrantes() {
        return listaIntegrantes;
    }

    public void setListaIntegrantes(ListaIntegrantes listaIntegrantes) {
        if (this.listaIntegrantes == null) {
            this.listaIntegrantes = listaIntegrantes;
        }
    }

    public NodoArbol getNextPendingNode() {
        return getLeftestPendingNode(root);
    }

    private NodoArbol getLeftestPendingNode(NodoArbol nodo) {
        NodoArbol deepest = null;

        if (nodo.getEstado() == EstadoNodo.Pendiente) {
            deepest = nodo;
        }

        if (nodo.hijos.size() > 0) {

            NodoArbol n = null;
            for (NodoArbol hijo : nodo.hijos) {
                n = getLeftestPendingNode(hijo);
                if (n != null) {
                    deepest = n;
                    break;
                }
            }
        }

        return deepest;
    }

    public NodoArbol getNextUndoneNodeFor(String owner) {
        return getLeftestUndoneNodeFor(owner, root);
    }

    private NodoArbol getLeftestUndoneNodeFor(String owner, NodoArbol nodo) {
        NodoArbol deepest = null;

        if (nodo.estado != EstadoNodo.Validado) {
            return null;
        }

        if (nodo.hijos.size() > 0) {

            NodoArbol n = null;
            for (NodoArbol hijo : nodo.hijos) {
                n = getLeftestUndoneNodeFor(owner, hijo);
                if (n != null) {
                    deepest = n;
                    break;
                }
            }
        }

        if (deepest == null && nodo.getEstado() == EstadoNodo.Validado && nodo.getOwner().equalsIgnoreCase(owner)) {
            deepest = nodo;
        }

        return deepest;
    }

    public boolean isValidated() {
        return root.getEstado() == EstadoNodo.Validado;
    }

    public boolean isDone() {
        return root.getEstado() == EstadoNodo.Realizado;
    }

    // ##################### CONSTRUCTOR ######################
    public ArbolObjetivos(Subobjetivo objetivo) {
        this.root = new NodoArbol(objetivo, null);
    }

    // ##################### METODOS AUXILIARES DE TRATAMIENTO DE AGENTES ########################
    public void enviarEncuestas(String emisor, NodoArbol nodoActual) throws Exception {

        ItfUsoRepositorioInterfaces repo = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

        for (String agente : listaIntegrantes.getLista()) {
            EncuestaNodo encuesta = new EncuestaNodo(agente, nodoActual, emisor);
            MensajeSimple ms = new MensajeSimple(encuesta, emisor, agente);

            ((ItfUsoAgenteCognitivo) repo.obtenerInterfazUso(agente)).aceptaMensaje(ms);

        }

    }

    private Integer estadoActualizacion = 0;
    private long momentoActualizacion;

    public void enviarArbolActualizado(String emisor) throws Exception {

        ItfUsoRepositorioInterfaces repo = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

        synchronized (estadoActualizacion) {
            estadoActualizacion++;
            momentoActualizacion = System.currentTimeMillis();
            for (String agente : listaIntegrantes.getLista()) {
                GameEvent eventoParche = new GameEvent("ActualizaArbol");
                eventoParche.setParameter("arbol", this);
                MensajeSimple ms = new MensajeSimple(eventoParche, emisor, agente);
                ((ItfUsoAgenteCognitivo) repo.obtenerInterfazUso(agente)).aceptaMensaje(ms);
            }
        }

    }

    public static ArbolObjetivos SeleccionaArbolMasActualizado(ArbolObjetivos arbol1, ArbolObjetivos arbol2) {
        ArbolObjetivos masActualizado = null,
                menosActualizado = null;

        if (arbol1.estadoActualizacion > arbol2.momentoActualizacion) {
            masActualizado = arbol1;
            menosActualizado = arbol2;
        } else {
            masActualizado = arbol2;
            menosActualizado = arbol1;
        }

        if (masActualizado.momentoActualizacion < menosActualizado.momentoActualizacion) {
            throw new RuntimeException("Error al seleccionar el arbol más actualizado, un arbol se actualizó de forma paralela a otro.");
        }

        return (arbol1.estadoActualizacion > arbol2.estadoActualizacion) ? arbol1 : arbol2;
    }

    public void solicitarSolucionAlUsuario(NodoArbol nodoActual) {

    }

}
