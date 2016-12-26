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
import uy.com.workflow.ordenes.data.TareaListProducer;
import uy.com.workflow.ordenes.model.Estado;
import uy.com.workflow.ordenes.model.Orden;
import uy.com.workflow.ordenes.model.Tarea;


@Stateful
@Model
public class RegistroTarea {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Tarea> tareaEventSrc;
	   
	   @Inject
	   private TareaListProducer tlp;
	   
	   @Inject
	   private EstadoListProducer elp;

	   private Tarea newTarea;
	
	   @Produces
	   @Named
	   public Tarea getNewTarea() {
	      return newTarea;
	   }

	   public void registro() throws Exception {
	      em.persist(newTarea);
	      tareaEventSrc.fire(newTarea);
	      initNewTarea();
	   }
	   
	   public void modificar(Tarea tarea) throws Exception {
		   log.info("Modifico " + tarea);
		   em.merge(tarea);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Tarea tarea = em.find(Tarea.class, id);
		   em.remove(tarea);
		   tareaEventSrc.fire(newTarea);
	   }

	   public Tarea buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Tarea tarea = em.find(Tarea.class, id);
		   return tarea;
	   }
	   
	   @PostConstruct
	   public void initNewTarea() {
		   newTarea = new Tarea();
	   }
	   
	   public List<Tarea> obtenerTareasDisponiblesPorOrden(Long idOrden, Long idTarea){
		   List<Tarea> tareasDisponibles = new ArrayList<Tarea>();
		   tareasDisponibles = tlp.obtenerTareasDisponiblesPorCliente(idOrden, idTarea);
		   return tareasDisponibles;
	   }
	   
	   public void agregarTareasPredecesoras(List<Tarea> tareas, Tarea tareaSeleccionada){
		   for (Tarea t : tareas) {
			   System.out.println("agrego orden : " + t.getDescripcion());
			   tareaSeleccionada.getTareasPredecesoras().add(t);
		   }
		   System.out.println("Cantidad de ordenes agregadas " + tareas.size());
		   em.merge(tareaSeleccionada);
	   }
	   
	   public void quitarTareasPredecesoras(List<Tarea> tareas, Tarea tareaSeleccionada){
		   tareaSeleccionada.getTareasPredecesoras().removeAll(tareas);
		   System.out.println("Cantidad de ordenes eliminadas " + tareas.size());
		   for (Tarea tarea : tareaSeleccionada.getTareasPredecesoras()) {
			System.out.println("Orden que se va a guardar " + tarea.getDescripcion());
		   }
		   em.merge(tareaSeleccionada);
	   }
	   
	   public List<Estado> getEstados(){
		   return elp.getEstados();
	   }
	   
	   public void agregarTarea(Orden orden) throws Exception{
		   System.out.println("Id : " + newTarea.getId());
//		   System.out.println("Pueto : " + newTarea.getPuesto().getDescripcion());
		   System.out.println("Estado : " + newTarea.getEstado().getDescripcion());
		   System.out.println("Descripcion : " + newTarea.getDescripcion());
		   System.out.println("Orden seleccionada : " + orden.getDetalle());
		   newTarea.setOrden(orden);
		   orden.getTareas().add(newTarea);
		   em.merge(orden);
	   }
}