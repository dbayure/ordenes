
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

import uy.com.workflow.ordenes.model.Tarea;


@RequestScoped
public class TareaListProducer {
	
   @Inject
   private EntityManager em;

   private List<Tarea> tareas;


   @Produces
   @Named
   public List<Tarea> getTareas() {
      return tareas;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Tarea tarea) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Tarea> criteria = cb.createQuery(Tarea.class);
      Root<Tarea> tarea = criteria.from(Tarea.class);
      criteria.select(tarea).orderBy(cb.asc(tarea.get("id")));
      tareas = em.createQuery(criteria).getResultList();
   }
}
