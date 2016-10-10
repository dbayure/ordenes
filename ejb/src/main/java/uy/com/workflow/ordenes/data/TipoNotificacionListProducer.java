
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

import uy.com.workflow.ordenes.model.TipoNotificacion;


@RequestScoped
public class TipoNotificacionListProducer {
	
   @Inject
   private EntityManager em;

   private List<TipoNotificacion> tiposNotificacion;


   @Produces
   @Named
   public List<TipoNotificacion> getTiposNotificacion() {
      return tiposNotificacion;
   }

   public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final TipoNotificacion tipoNotificacion) {
	      retrieveAllOrderedByName();
   }

   @PostConstruct
   public void retrieveAllOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<TipoNotificacion> criteria = cb.createQuery(TipoNotificacion.class);
      Root<TipoNotificacion> tipoNotificacion = criteria.from(TipoNotificacion.class);
      criteria.select(tipoNotificacion).orderBy(cb.asc(tipoNotificacion.get("id")));
      tiposNotificacion = em.createQuery(criteria).getResultList();
   }
}
