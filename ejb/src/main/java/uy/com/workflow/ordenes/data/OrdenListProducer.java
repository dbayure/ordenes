
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

import uy.com.workflow.ordenes.model.Orden;


@RequestScoped
public class OrdenListProducer {
	
   @Inject
   private EntityManager em;

   private List<Orden> ordens;


   @Produces
   @Named
   public List<Orden> getOrdens() {
      return ordens;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Orden orden) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Orden> criteria = cb.createQuery(Orden.class);
      Root<Orden> orden = criteria.from(Orden.class);
      criteria.select(orden).orderBy(cb.asc(orden.get("id")));
      ordens = em.createQuery(criteria).getResultList();
   }
}
