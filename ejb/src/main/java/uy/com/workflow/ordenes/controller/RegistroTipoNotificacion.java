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

import uy.com.workflow.ordenes.model.TipoNotificacion;


@Stateful
@Model
public class RegistroTipoNotificacion {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<TipoNotificacion> tipoNotificacionEventSrc;

	   private TipoNotificacion newTipoNotificacion;

	   @Produces
	   @Named
	   public TipoNotificacion getNewTipoNotificacion() {
	      return newTipoNotificacion;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newTipoNotificacion.getnombre());
	      em.persist(newTipoNotificacion);
	      tipoNotificacionEventSrc.fire(newTipoNotificacion);
	      initNewTipoNotificacion();
	   }
	   
	   public void modificar(TipoNotificacion tipoNotificacion) throws Exception {
		   log.info("Modifico " + tipoNotificacion);
		   em.merge(tipoNotificacion);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   TipoNotificacion tipoNotificacion = em.find(TipoNotificacion.class, id);
		   em.remove(tipoNotificacion);
		   tipoNotificacionEventSrc.fire(newTipoNotificacion);
	   }

	   public TipoNotificacion buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   TipoNotificacion tipoNotificacion = em.find(TipoNotificacion.class, id);
		   return tipoNotificacion;
	   }
	   
	   @PostConstruct
	   public void initNewTipoNotificacion() {
		   newTipoNotificacion = new TipoNotificacion();
	   }
}