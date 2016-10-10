package uy.com.workflow.ordenes.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

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

	   private Tarea newTarea;

	   @Produces
	   @Named
	   public Tarea getNewTarea() {
	      return newTarea;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newTarea.getDescripcion());
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
}