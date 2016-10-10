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

	   @Produces
	   @Named
	   public Orden getNewOrden() {
	      return newOrden;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newOrden.getDetalle());
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
}