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

import uy.com.workflow.ordenes.model.Estado;
import uy.com.workflow.ordenes.model.Notificacion;
import uy.com.workflow.ordenes.model.Tarea;
import uy.com.workflow.ordenes.model.TipoNotificacion;


@Stateful
@Model
public class RegistroNotificacion {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Notificacion> notificacionEventSrc;

	   private Notificacion newNotificacion;

	   @Produces
	   @Named
	   public Notificacion getNewNotificacion() {
	      return newNotificacion;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newNotificacion.getDescripcion());
	      Tarea tarea = new Tarea();
	      Estado estado = new Estado();
	      TipoNotificacion tn = new TipoNotificacion();
	      estado = em.find(Estado.class, newNotificacion.getEstado().getId());
	      tn = em.find(TipoNotificacion.class, newNotificacion.getTipoNotificacion().getId());
	      tarea = em.find(Tarea.class, newNotificacion.getTarea().getId());
	      tarea.getNotificaciones().add(newNotificacion);
	      newNotificacion.setTarea(tarea);
	      newNotificacion.setEstado(estado);
	      newNotificacion.setTipoNotificacion(tn);
	      em.persist(newNotificacion);
	      notificacionEventSrc.fire(newNotificacion);
	      initNewNotificacion();
	   }
	   
	   public void modificar(Notificacion notificacion) throws Exception {
		   log.info("Modifico " + notificacion);
		   em.merge(notificacion);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Notificacion notificacion = em.find(Notificacion.class, id);
		   Tarea tarea = em.find(Tarea.class, notificacion.getTarea().getId());
		   tarea.getNotificaciones().remove(notificacion);
		   em.remove(notificacion);
		   notificacionEventSrc.fire(newNotificacion);
	   }

	   public Notificacion buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Notificacion notificacion = em.find(Notificacion.class, id);
		   return notificacion;
	   }
	   
	   @PostConstruct
	   public void initNewNotificacion() {
		   newNotificacion = new Notificacion();
	   }
}