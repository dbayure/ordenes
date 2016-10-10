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


@Stateful
@Model
public class RegistroEstado {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Estado> estadoEventSrc;

	   private Estado newEstado;

	   @Produces
	   @Named
	   public Estado getNewEstado() {
	      return newEstado;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newEstado.getnombre());
	      em.persist(newEstado);
	      estadoEventSrc.fire(newEstado);
	      initNewEstado();
	   }
	   
	   public void modificar(Estado estado) throws Exception {
		   log.info("Modifico " + estado);
		   em.merge(estado);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Estado estado = em.find(Estado.class, id);
		   em.remove(estado);
		   estadoEventSrc.fire(newEstado);
	   }

	   public Estado buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Estado estado = em.find(Estado.class, id);
		   return estado;
	   }
	   
	   @PostConstruct
	   public void initNewEstado() {
		   newEstado = new Estado();
	   }
}