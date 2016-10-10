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

import uy.com.workflow.ordenes.model.EstadoNotificacion;


@Stateful
@Model
public class RegistroEstadoNotificacion {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<EstadoNotificacion> estadoNotificacionEventSrc;

	   private EstadoNotificacion newEstadoNotificacion;

	   @Produces
	   @Named
	   public EstadoNotificacion getNewEstadoNotificacion() {
	      return newEstadoNotificacion;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newEstadoNotificacion.getnombre());
	      em.persist(newEstadoNotificacion);
	      estadoNotificacionEventSrc.fire(newEstadoNotificacion);
	      initNewEstadoNotificacion();
	   }
	   
	   public void modificar(EstadoNotificacion estadoNotificacion) throws Exception {
		   log.info("Modifico " + estadoNotificacion);
		   em.merge(estadoNotificacion);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   EstadoNotificacion estadoNotificacion = em.find(EstadoNotificacion.class, id);
		   em.remove(estadoNotificacion);
		   estadoNotificacionEventSrc.fire(newEstadoNotificacion);
	   }

	   public EstadoNotificacion buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   EstadoNotificacion estadoNotificacion = em.find(EstadoNotificacion.class, id);
		   return estadoNotificacion;
	   }
	   
	   @PostConstruct
	   public void initNewEstadoNotificacion() {
		   newEstadoNotificacion = new EstadoNotificacion();
	   }
}