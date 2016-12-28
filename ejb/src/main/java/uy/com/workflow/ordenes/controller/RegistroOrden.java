package uy.com.workflow.ordenes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import uy.com.workflow.ordenes.data.EstadoListProducer;
import uy.com.workflow.ordenes.data.OrdenListProducer;
import uy.com.workflow.ordenes.model.Cliente;
import uy.com.workflow.ordenes.model.Estado;
import uy.com.workflow.ordenes.model.Orden;


@Stateful
@Model
public class RegistroOrden {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Orden> ordenEventSrc;

	   private Orden newOrden;
	   
	   @Inject
	   private RegistroTarea rt;
	   	   
	   @Inject
	   private OrdenListProducer olp;
	   
	   @Inject
	   private EstadoListProducer elp;
	   
	   @Produces
	   @Named
	   public Orden getNewOrden() {
	      return newOrden;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newOrden.getDetalle() + " con el cliente " + newOrden.getCliente().getNombre());
	      Cliente cliente = new Cliente();
	      cliente = newOrden.getCliente();
	      Estado estado = new Estado();
	      estado = newOrden.getEstado();
	      newOrden.setEstado(estado);
	      newOrden.setCliente(cliente);
	      em.persist(newOrden);
	      ordenEventSrc.fire(newOrden);
	      initNewOrden();
	   }
	   
	   public void modificar(Orden orden) throws Exception {
		   log.info("Modifico " + orden);
		   em.merge(orden);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Orden orden = em.find(Orden.class, id);
		   em.remove(orden);
		   ordenEventSrc.fire(newOrden);
	   }

	   public Orden buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Orden orden = em.find(Orden.class, id);
		   return orden;
	   }
	   
	   @PostConstruct
	   public void initNewOrden() {
		   newOrden = new Orden();
	   }
	   
	   public List<Orden> obtenerOrdenesDisponiblesPorCliente(Long idOrden, Long idCli){
		   List<Orden> ordenesDisponibles = new ArrayList<Orden>();
		   ordenesDisponibles = olp.obtenerOrdenesDisponiblesPorCliente(idOrden, idCli);
		   return ordenesDisponibles;
	   }
	   
	   public void agregarOrdenesPredecesoras(List<Orden> ordenes, Orden ordenSeleccionada){
		   for (Orden o : ordenes) {
			   System.out.println("agrego orden : " + o.getDetalle());
			   ordenSeleccionada.getOrdenesPredecesoras().add(o);
		   }
		   System.out.println("Cantidad de ordenes agregadas " + ordenes.size());
		   em.merge(ordenSeleccionada);
	   }
	   
	   public void quitarOrdenesPredecesoras(List<Orden> ordenes, Orden ordenSeleccionada){
		  ordenSeleccionada.getOrdenesPredecesoras().removeAll(ordenes);
		   System.out.println("Cantidad de ordenes eliminadas " + ordenes.size());
		   for (Orden orden : ordenSeleccionada.getOrdenesPredecesoras()) {
			System.out.println("Orden que se va a guardar " + orden.getDetalle());
		   }
		   em.merge(ordenSeleccionada);
	   }
	   
	   public Orden getOrdenById(Long idOrden){
		   return olp.getOrdenById(idOrden);
	   }
	   
	   public List<Estado> getEstados(){
		   return elp.getEstados();
	   }
	   
		public void agregarTareasOrden(Long id) throws Exception{
			rt.agregarTarea(id);
		}
}