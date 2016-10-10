
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

import uy.com.workflow.ordenes.model.Accion;


@RequestScoped
public class AccionListProducer {
	
   @Inject
   private EntityManager em;

   private List<Accion> acciones;


   @Produces
   @Named
   public List<Accion> getAcciones() {
      return acciones;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Accion accion) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Accion> criteria = cb.createQuery(Accion.class);
      Root<Accion> accion = criteria.from(Accion.class);
      criteria.select(accion).orderBy(cb.asc(accion.get("id")));
      acciones = em.createQuery(criteria).getResultList();
   }
}
