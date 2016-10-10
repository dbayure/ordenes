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

import uy.com.workflow.ordenes.model.Accion;


@Stateful
@Model
public class RegistroAccion {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Accion> accionEventSrc;

	   private Accion newAccion;

	   @Produces
	   @Named
	   public Accion getNewAccion() {
	      return newAccion;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newAccion.getnombre());
	      em.persist(newAccion);
	      accionEventSrc.fire(newAccion);
	      initNewAccion();
	   }
	   
	   public void modificar(Accion accion) throws Exception {
		   log.info("Modifico " + accion);
		   em.merge(accion);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Accion accion = em.find(Accion.class, id);
		   em.remove(accion);
		   accionEventSrc.fire(newAccion);
	   }

	   public Accion buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Accion accion = em.find(Accion.class, id);
		   return accion;
	   }
	   
	   @PostConstruct
	   public void initNewAccion() {
		   newAccion = new Accion();
	   }
}