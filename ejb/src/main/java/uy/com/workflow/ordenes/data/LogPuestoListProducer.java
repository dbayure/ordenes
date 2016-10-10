
package uy.com.workflow.ordenes.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import uy.com.workflow.ordenes.model.LogPuesto;


@RequestScoped
public class LogPuestoListProducer {
	
   @Inject
   private EntityManager em;

   private List<LogPuesto> logsPuesto;


   @Produces
   @Named
   public List<LogPuesto> getLogsPuesto() {
      return logsPuesto;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final LogPuesto logPuesto) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<LogPuesto> criteria = cb.createQuery(LogPuesto.class);
      Root<LogPuesto> logPuesto = criteria.from(LogPuesto.class);
      criteria.select(logPuesto).orderBy(cb.asc(logPuesto.get("id")));
      logsPuesto = em.createQuery(criteria).getResultList();
   }
}
