
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

import uy.com.workflow.ordenes.model.EstadoNotificacion;


@RequestScoped
public class EstadoNotificacionListProducer {
	
   @Inject
   private EntityManager em;

   private List<EstadoNotificacion> estadoNotificaciones;


   @Produces
   @Named
   public List<EstadoNotificacion> getEstadoNotificaciones() {
      return estadoNotificaciones;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final EstadoNotificacion estadoNotificacion) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<EstadoNotificacion> criteria = cb.createQuery(EstadoNotificacion.class);
      Root<EstadoNotificacion> estadoNotificacion = criteria.from(EstadoNotificacion.class);
      criteria.select(estadoNotificacion).orderBy(cb.asc(estadoNotificacion.get("id")));
      estadoNotificaciones = em.createQuery(criteria).getResultList();
   }
}
