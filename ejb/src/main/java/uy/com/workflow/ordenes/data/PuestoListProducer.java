
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

import uy.com.workflow.ordenes.model.Puesto;


@RequestScoped
public class PuestoListProducer {
	
   @Inject
   private EntityManager em;

   private List<Puesto> puestos;


   @Produces
   @Named
   public List<Puesto> getPuestos() {
      return puestos;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Puesto puesto) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Puesto> criteria = cb.createQuery(Puesto.class);
      Root<Puesto> puesto = criteria.from(Puesto.class);
      criteria.select(puesto).orderBy(cb.asc(puesto.get("id")));
      puestos = em.createQuery(criteria).getResultList();
   }
}
