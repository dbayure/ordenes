package uy.com.workflow.ordenes.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uy.com.workflow.ordenes.data.OrdenListProducer;
import uy.com.workflow.ordenes.model.Orden;


/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/ordenes")
@RequestScoped
public class OrdenResourceRESTService {
	
   @Inject
   private EntityManager em;
   
   @Inject
   private OrdenListProducer ordenes;

   @GET
   @Produces("application/json")
   public List<Orden> listAll() {
      // Use @SupressWarnings to force IDE to ignore warnings about "genericizing" the results of
      // this query
      @SuppressWarnings("unchecked")
      // We recommend centralizing inline queries such as this one into @NamedQuery annotations on
      // the @Entity class
      // as described in the named query blueprint:
      // https://blueprints.dev.java.net/bpcatalog/ee5/persistence/namedquery.html
      final List<Orden> results = ordenes.getOrdenes();
      return results;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Orden lookupById(@PathParam("id") long id) {
      return em.find(Orden.class, id);
   }
}
