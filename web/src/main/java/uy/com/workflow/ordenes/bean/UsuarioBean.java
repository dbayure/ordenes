package uy.com.workflow.ordenes.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import uy.com.workflow.ordenes.controller.RegistroUsuario;
import uy.com.workflow.ordenes.model.Puesto;
import uy.com.workflow.ordenes.model.Rol;
import uy.com.workflow.ordenes.model.Usuario;


@ManagedBean
@ViewScoped
public class UsuarioBean {

	@Inject
	private RegistroUsuario registroUsuario;
	
	private Usuario usrSeleccionado;
	private List<Puesto> puestosSeleccionadosQuitar;
	private List<Puesto> puestosSeleccionadosAgregar;
	private List<Puesto> puestosDisponibles;
	private List<Puesto> puestosAsignados;

	public List<Puesto> getPuestosSeleccionadosQuitar() {
		return puestosSeleccionadosQuitar;
	}

	public void setPuestosSeleccionadosQuitar(List<Puesto> puestosSeleccionadosQuitar) {
		this.puestosSeleccionadosQuitar = puestosSeleccionadosQuitar;
	}

	public List<Puesto> getPuestosSeleccionadosAgregar() {
		return puestosSeleccionadosAgregar;
	}

	public void setPuestosSeleccionadosAgregar(List<Puesto> puestosSeleccionados) {
		System.out.println("Puestos que vienen de la vista " + puestosSeleccionados);
		this.puestosSeleccionadosAgregar = puestosSeleccionados;
	}

	public Usuario getUsrSeleccionado() {
		return usrSeleccionado;
	}

	public void setUsrSeleccionado(Usuario usr) {
		this.usrSeleccionado = usr;
	}

	public List<Puesto> getPuestosDisponibles() {
		return puestosDisponibles;
	}

	public void setPuestosDisponibles(List<Puesto> puestosDisponibles) {
		this.puestosDisponibles = puestosDisponibles;
	}

	public List<Puesto> getPuestosAsignados() {
		return puestosAsignados;
	}

	public void setPuestosAsignados(List<Puesto> puestosAsignados) {
		this.puestosAsignados = puestosAsignados;
	}

	public void registrar() {
		try {
			registroUsuario.registro();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registró ", "con éxito!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch (Exception e) {
		e.printStackTrace();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al registrar ", "");  
        FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	
	public void onEdit(RowEditEvent event) {  
		Usuario usuario = ((Usuario) event.getObject());
           
            try {
            	registroUsuario.modificar(usuario);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó ", usuario.getNombre());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al modificar ", usuario.getNombre());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
    }
	
	public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Se canceló modificar ", ((Usuario) event.getObject()).getNombre());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
	
	public void eliminar(Long id) {
		try {
			registroUsuario.eliminar(id);
			FacesMessage msg = new FacesMessage("Se eliminó ", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch(Exception e) {
			FacesMessage msg = new FacesMessage("Error al eliminar", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		  
	}
	
	public void buscar(Long id) {
		try {
			registroUsuario.buscar(id);
			FacesMessage msg = new FacesMessage("Se encontró ", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch(Exception e) {
			FacesMessage msg = new FacesMessage("Error al buscar", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		  
	}
	
	public void onCellEdit(CellEditEvent event) {  
		Object oldValue = event.getOldValue();
	    Object newValue = event.getNewValue();
            try {
            	if(newValue != null && !newValue.equals(oldValue)) {
            	    DataTable d = (DataTable) event.getSource();
            	    Usuario usr = (Usuario) d.getRowData();
            	    String columna = event.getColumn().getHeaderText();
            	    if ( columna.equals("Rol")){   
            	    	Rol rol = (Rol) newValue;
            	    	usr.setRol(rol);
            	    }
            	    registroUsuario.modificar(usr);
                }
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario fue modificado exitosamente" , "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				e.printStackTrace();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar el usuario", "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
    }
	
	public void onRowSelect(SelectEvent event) {
	        this.setUsrSeleccionado((Usuario) event.getObject());
	        Long idUsuario = ((Usuario) event.getObject()).getId();
	        generarListaPuestosDisponibles(idUsuario);
	        generarListaPuestosAsignados(idUsuario);
	    }
	 
	public void generarListaPuestosAsignados(Long idUsuario){
		List<Puesto> puestos = registroUsuario.cargarPuestos(idUsuario);
		this.setPuestosAsignados(puestos);
		for (Puesto p : puestos) {
			System.out.println("Puesto: " + p.getNombre());
		}
	}
	
	public void quitarPuestosAlUsuario(){
		registroUsuario.quitarPuestosAlUsuario(usrSeleccionado.getId(), puestosSeleccionadosQuitar);
		generarListaPuestosDisponibles(usrSeleccionado.getId());
		generarListaPuestosAsignados(usrSeleccionado.getId());
	}
	
	public void generarListaPuestosDisponibles(Long idUsuario){
		this.setPuestosDisponibles(registroUsuario.generarListaPuestosDisponibles(idUsuario));
	}

	public void agregarPuestosAlUsuario(){
		registroUsuario.agregarListaPuestosAlUsuario(usrSeleccionado.getId(), puestosSeleccionadosAgregar);
		generarListaPuestosDisponibles(usrSeleccionado.getId());
		generarListaPuestosAsignados(usrSeleccionado.getId());
	}
	
	public void cambiarContraseña(){
		if (usrSeleccionado == null){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un usuario para poder cambiar su contraseña" , "");  
            FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		try{
			registroUsuario.cambiarContraseña(usrSeleccionado.getId());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La contraseña fue actualizada correctamente" , "");  
            FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		catch(Exception e){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No fue posible cambiar la cambiar su contraseña" , "");  
            FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		
	}
}
