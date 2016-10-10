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

import uy.com.workflow.ordenes.model.Puesto;


@Stateful
@Model
public class RegistroPuesto {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Puesto> usuarioEventSrc;

	   private Puesto newPuesto;

	   @Produces
	   @Named
	   public Puesto getNewPuesto() {
	      return newPuesto;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newPuesto.getDescripcion());
	      em.persist(newPuesto);
	      usuarioEventSrc.fire(newPuesto);
	      initNewPuesto();
	   }
	   
	   public void modificar(Puesto usuario) throws Exception {
		   log.info("Modifico " + usuario);
		   em.merge(usuario);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Puesto usuario = em.find(Puesto.class, id);
		   em.remove(usuario);
		   usuarioEventSrc.fire(newPuesto);
	   }

	   public Puesto buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Puesto usuario = em.find(Puesto.class, id);
		   return usuario;
	   }
	   
	   @PostConstruct
	   public void initNewPuesto() {
		   newPuesto = new Puesto();
	   }
}