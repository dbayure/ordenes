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

import uy.com.workflow.ordenes.model.Cliente;


@Stateful
@Model
public class RegistroCliente {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;

	   @Inject
	   private Event<Cliente> clienteEventSrc;

	   private Cliente newCliente;

	   @Produces
	   @Named
	   public Cliente getNewCliente() {
	      return newCliente;
	   }

	   public void registro() throws Exception {
	      log.info("Registro " + newCliente.getNombre());
	      em.persist(newCliente);
	      clienteEventSrc.fire(newCliente);
	      initNewCliente();
	   }
	   
	   public void modificar(Cliente cliente) throws Exception {
		   log.info("Modifico " + cliente);
		   em.merge(cliente);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Cliente cliente = em.find(Cliente.class, id);
		   em.remove(cliente);
		   clienteEventSrc.fire(newCliente);
	   }

	   public Cliente buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Cliente cliente = em.find(Cliente.class, id);
		   return cliente;
	   }
	   
	   @PostConstruct
	   public void initNewCliente() {
		   newCliente = new Cliente();
	   }
}