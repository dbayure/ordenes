package uy.com.workflow.ordenes.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import uy.com.workflow.ordenes.data.PuestoListProducer;
import uy.com.workflow.ordenes.model.Puesto;
import uy.com.workflow.ordenes.model.Usuario;


@Stateful
@Model
public class RegistroUsuario {

	   @Inject
	   private Logger log;

	   @Inject
	   private EntityManager em;
	   
	   @Inject
	   private PuestoListProducer plp;

	   @Inject
	   private Event<Usuario> usuarioEventSrc;

	   private Usuario newUsuario;

	   @Produces
	   @Named
	   public Usuario getNewUsuario() {
	      return newUsuario;
	   }
	   
	   public void registro() throws Exception {
	      log.info("Registro " + newUsuario.getNombre());
	      Set<Puesto> puestos = new HashSet<Puesto>();
	      for (Puesto p : newUsuario.getPuestos()) {
	    	  Puesto puesto = new Puesto();
	    	  puesto = p;
	    	  puesto.getUsuarios().add(newUsuario);
	    	  puestos.add(puesto);
	      }
	      newUsuario.getPuestos().addAll(puestos);
	      em.persist(newUsuario);
	      usuarioEventSrc.fire(newUsuario);
	      initNewUsuario();
	   }
	  	   
	   public void modificar(Usuario usuario) throws Exception {
		   log.info("Modifico " + usuario);
		   em.merge(usuario);
	   }
	   
	   public void eliminar(Long id) throws Exception {
		   log.info("Elimino " + id);
		   Usuario usuario = em.find(Usuario.class, id);
		   em.remove(usuario);
		   usuarioEventSrc.fire(newUsuario);
	   }

	   public Usuario buscar(Long id) throws Exception {
		   log.info("Buscar " + id);
		   Usuario usuario = em.find(Usuario.class, id);
		   return usuario;
	   }
	   	   
	   @PostConstruct
	   public void initNewUsuario() {
		   newUsuario = new Usuario();
	   }
	   
	   public List<Puesto> cargarPuestos(long idUsuario){
		   Usuario usuario = em.find(Usuario.class, idUsuario);
		   List<Puesto> puestos = new ArrayList<Puesto>();
		   puestos.addAll(usuario.getPuestos());
		   return puestos;
	   }
	   
	   public void quitarPuestosAlUsuario(Long idUsuario, List<Puesto> puestos){
		   Usuario usuario = new Usuario(); 
		   usuario = em.find(Usuario.class, idUsuario);
		   for (Puesto puesto : puestos) {
			   Puesto p = new Puesto();
			   p = em.find(Puesto.class, puesto.getId());
			   p.getUsuarios().remove(usuario);
			   usuario.getPuestos().remove(p);
		   }
		   em.merge(usuario);
	   }
	   
	   public List<Puesto> generarListaPuestosDisponibles(Long idUsuario){
		   Usuario usuario = em.find(Usuario.class, idUsuario);
		   Set<Puesto> puestosUsuario = usuario.getPuestos();
		   List<Puesto> puestos = plp.getPuestos();
		   List<Puesto> disponibles = new ArrayList<Puesto>();
		   for (Puesto puesto : puestos) {
			   boolean agregar = true;
				for (Puesto pu : puestosUsuario) {
					if(puesto.getId() == pu.getId()){
						agregar = false;
					}
				}
				if (agregar == true){
					System.out.println("Agrego el puesto: " + puesto.getDescripcion() + " a la lista de puestos disponibles");
					disponibles.add(puesto);
				}
		   }
		   return disponibles;
	   }
	   
	   public void agregarListaPuestosAlUsuario(Long idUsuario, List<Puesto> puestos){
		   Usuario usuario = new Usuario(); 
		   usuario = em.find(Usuario.class, idUsuario);
		   usuario.getPuestos().addAll(puestos);
		   for (Puesto puesto : puestos) {
			   puesto.getUsuarios().add(usuario);
		   }
		   em.merge(usuario);
	   }
	   
	   public void cambiarContraseña(Long idUsuario){
		   Usuario u = new Usuario();
		   u = em.find(Usuario.class, idUsuario);
		   System.out.println("usuario :" +  u.getNombre() + " contraseña actual " + u.getPassword() + " contraseña nueva " + newUsuario.getPassword());
		   u.setPassword(newUsuario.getPassword());
		   em.merge(u);
	   }
}