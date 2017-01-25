
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

import uy.com.workflow.ordenes.model.Notificacion;


@RequestScoped
public class NotificacionListProducer {
	
   @Inject
   private EntityManager em;

   private List<Notificacion> notificaciones;


   @Produces
   @Named
   public List<Notificacion> getNotificaciones() {
      return notificaciones;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Notificacion notificacion) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Notificacion> criteria = cb.createQuery(Notificacion.class);
      Root<Notificacion> notificacion = criteria.from(Notificacion.class);
      criteria.select(notificacion).orderBy(cb.asc(notificacion.get("descripcion")));
      notificaciones = em.createQuery(criteria).getResultList();
   }
}
