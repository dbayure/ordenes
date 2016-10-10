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

import uy.com.workflow.ordenes.model.LogPuesto;



@Stateful
@Model
public class RegistroLogPuesto {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<LogPuesto> logPuestoEventSrc;

	   private LogPuesto newLogPuesto;

	   @Produces
	   @Named
	   public LogPuesto getNewLogPuesto() {
	      return newLogPuesto;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newLogPuesto.getId());
	      em.persist(newLogPuesto);
	      logPuestoEventSrc.fire(newLogPuesto);
	      initNewLogPuesto();
	   }
	   
	   public void modificar(LogPuesto logPuesto) throws Exception {
		   log.info("Modifico " + logPuesto);
		   em.merge(logPuesto);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   LogPuesto logPuesto = em.find(LogPuesto.class, id);
		   em.remove(logPuesto);
		   logPuestoEventSrc.fire(newLogPuesto);
	   }

	   public LogPuesto buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   LogPuesto logPuesto = em.find(LogPuesto.class, id);
		   return logPuesto;
	   }
	   
	   @PostConstruct
	   public void initNewLogPuesto() {
		   newLogPuesto = new LogPuesto();
	   }
}