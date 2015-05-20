package icaro.aplicaciones.informacion.minions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.Subobjetivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.RecursosAplicacion;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class ArbolObjetivos {
    
    // ############################ ENUMERADOS Y CLASES #####################################
    
    public enum EstadoNodo {
        Pendiente, Resuelto, Validado, Realizado, Irresoluble
    }
    
    public class NodoArbol{
        
        private Subobjetivo obj;
        private NodoArbol padre;
        private List<NodoArbol> hijos;
        private EstadoNodo estado;
        
        private String owner;
        private List<String> failedOwners;
        
        protected NodoArbol(Subobjetivo obj, NodoArbol padre){
            
            this.obj = obj;
            this.hijos = new ArrayList<NodoArbol>();
            this.failedOwners = new ArrayList<String>();
            this.estado = EstadoNodo.Pendiente;
            
            if(padre != null){
                this.padre = padre;
                this.obj.setParent(padre.obj);
            }
            
        }
        
        public String getOwner(){
            return this.owner;
        }
        
        public void setNewOwner(String newOwner){
            if(this.owner != null)
                this.failedOwners.add(this.owner);
            
            this.owner = newOwner;
        }
        
        public void ownerFailedSolving(){
            if(this.owner != null)
                this.failedOwners.add(this.owner);
            
            this.owner = null;
            this.setEstado(EstadoNodo.Pendiente);
        }
        
        public EstadoNodo getEstado(){
            return estado;
        }
        
        public void setEstado(EstadoNodo estado){
            if(this.estado == estado) // No state change
                return;
            
            // ########## Estados excepcionales #########
            if(this.estado == EstadoNodo.Realizado)
                throw new IllegalArgumentException("No se permite transitar de realizado a cualquier otro estado.");
            
            // ######### Transiciones ###########
            
            // ========= REALIZADO ==========
            if(estado == EstadoNodo.Realizado){
                this.estado = EstadoNodo.Realizado;
            // ========= VALIDADO ===========
            } else if(estado == EstadoNodo.Validado){
                // Si no hay hijos, se auto-valida
                boolean todosHijosValidados = hijos.size() == 0;
                for(NodoArbol hijo: hijos){
                    // Al menos un hijo debe estar validado
                    if(hijo.getEstado() == EstadoNodo.Validado)
                        todosHijosValidados = true;
                    
                    // Aceptamos dos estados, pues que estén realizados o sean irresolubles nos da igual.
                    // Los nodos irresolubles hay que conservarlos para asegurarnos de que no reintentamos algo imposible.
                    if(!(hijo.getEstado() == EstadoNodo.Validado 
                            || hijo.getEstado() == EstadoNodo.Realizado 
                            || hijo.getEstado() == EstadoNodo.Irresoluble)){
                        todosHijosValidados = false;
                        break;
                    }
                }
                
                if(todosHijosValidados){
                    this.estado = EstadoNodo.Validado;
                    // Intentamos validar el padre propagando la validación al arbol completo
                    if(this.padre != null)
                        this.padre.setEstado(EstadoNodo.Validado);
                }
            // ========= IRRESOLUBLE ==========  
            } else if(estado == EstadoNodo.Irresoluble){
                this.estado = EstadoNodo.Irresoluble;
                if(this.padre != null)
                    this.padre.setEstado(EstadoNodo.Pendiente);
                
            // ========= PENDIENTE ========
            } else if(estado == EstadoNodo.Pendiente) {
                this.estado = EstadoNodo.Pendiente;
                if(this.padre != null && this.padre.getEstado() == EstadoNodo.Validado){
                    this.padre.setEstado(EstadoNodo.Resuelto);
                }
            // ======== RESUELTO ========
            } else if(estado == EstadoNodo.Resuelto){
                this.estado = EstadoNodo.Resuelto;
                if(this.padre.getEstado() == EstadoNodo.Validado)
                    this.padre.setEstado(EstadoNodo.Resuelto);
            }
        }
    }
    
    public class ListaIntegrantes {
        private Set<String> listaNombres;
        
        public ListaIntegrantes(String nombre){
            listaNombres = new LinkedHashSet<String>();
        }
        
        public boolean mezclarCon(ListaIntegrantes other){
            int previous = listaNombres.size();
            listaNombres.addAll(other.listaNombres);
            return listaNombres.size() != previous;
        }
        
        public Set<String> getLista(){
            return listaNombres;
        }
    }
    
    // #################################### ATRIBUTOS Y MÉTODOS ###################################
    
    private NodoArbol root;
    private ListaIntegrantes listaIntegrantes;
    
    public NodoArbol getNextPendingNode(){
        return getLeftestPendingNode(root);
    }
    
    private NodoArbol getLeftestPendingNode(NodoArbol nodo){
        NodoArbol deepest = null;
        
        if(nodo.getEstado() == EstadoNodo.Pendiente)
            deepest = nodo;
        
        if(nodo.hijos.size() > 0){
            
            NodoArbol n = null;
            for(NodoArbol hijo : nodo.hijos){
                n = getLeftestPendingNode(hijo);
                if(n != null){
                    deepest = nodo;
                    break;
                }
            }
        }
        
        return deepest;
    }
    
    public boolean isValidated(){
        return root.getEstado() == EstadoNodo.Validado;
    }
    
    public boolean isDone(){
        return root.getEstado() == EstadoNodo.Realizado;
    }
    
    // ##################### CONSTRUCTOR ######################
    
    public ArbolObjetivos(Subobjetivo objetivo, ListaIntegrantes integrantes){
        this.root = new NodoArbol(objetivo, null);
        this.listaIntegrantes = integrantes;
    }
    
    // ##################### METODOS AUXILIARES DE TRATAMIENTO DE AGENTES ########################
    
    public void enviarEncuestas(AgenteCognitivo emisor, NodoArbol nodoActual) throws Exception{
        
        ItfUsoRepositorioInterfaces repo = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
        
        for(String agente : listaIntegrantes.getLista()){
            EncuestaNodo encuesta = new EncuestaNodo(agente, nodoActual, emisor.getIdentAgente());
            MensajeSimple ms = new MensajeSimple(encuesta, emisor.getIdentAgente(), agente);
            
            ((ItfUsoAgenteCognitivo ) repo.obtenerInterfazUso(agente)).aceptaMensaje(ms);

        }
        
    }
    
    public void solicitarSolucionAlUsuario(NodoArbol nodoActual){
        
    }
    
}
