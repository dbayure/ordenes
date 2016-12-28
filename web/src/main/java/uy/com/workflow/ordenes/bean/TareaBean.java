package uy.com.workflow.ordenes.bean;

import java.util.ArrayList;
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

import uy.com.workflow.ordenes.controller.RegistroTarea;
import uy.com.workflow.ordenes.model.Estado;
import uy.com.workflow.ordenes.model.Tarea;


@ManagedBean
@ViewScoped
public class TareaBean {

	@Inject
	private RegistroTarea registroTarea;
	
	private Tarea tareaSeleccionada;
	
	private List<Tarea> tareasDisponibles;
	
	private List<Tarea> tareasSeleccionadas;
	
	private List<Tarea> tareasSeleccionadasQuitar;
	
	private List<Tarea> tareasFiltradas;
	
	private List<String> EstadosString = new ArrayList<String>();
	
	public Tarea getTareaSeleccionada() {
		return tareaSeleccionada;
	}

	public void setTareaSeleccionada(Tarea tareaSeleccionada) {
		this.tareaSeleccionada = tareaSeleccionada;
	}

	public List<Tarea> getTareasDisponibles() {
		return tareasDisponibles;
	}

	public void setTareasDisponibles(List<Tarea> tareasDisponibles) {
		this.tareasDisponibles = tareasDisponibles;
	}
	
	public List<Tarea> getTareasSeleccionadas() {
		return tareasSeleccionadas;
	}

	public void setTareasSeleccionadas(List<Tarea> tareas) {
		this.tareasSeleccionadas = tareas;
		System.out.println("Cantidad de tareas seteadas : " + tareasSeleccionadas.size());
	}
	
	public List<Tarea> getTareasSeleccionadasQuitar() {
		return tareasSeleccionadasQuitar;
	}

	public void setTareasSeleccionadasQuitar(List<Tarea> tareasSeleccionadasQuitar) {
		this.tareasSeleccionadasQuitar = tareasSeleccionadasQuitar;
	}

	public List<Tarea> getTareasFiltradas() {
		return tareasFiltradas;
	}

	public void setTareasFiltradas(List<Tarea> tareasFiltradas) {
		this.tareasFiltradas = tareasFiltradas;
	}
	
	public List<String> getEstadosString(){
		for (Estado e : registroTarea.getEstados()) {
			EstadosString.add(e.getnombre());
		}
		return EstadosString;
	}
	
	public void setEstadosString(List<String> estados){
		EstadosString = estados;
	}
	
	public void registrar() {
		try {
			registroTarea.registro();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registró ", "con éxito!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch (Exception e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al registrar ", "");  
        FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	
	public void onEdit(RowEditEvent event) {  
		Tarea tarea = ((Tarea) event.getObject());
           
            try {
            	registroTarea.modificar(tarea);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó ", tarea.getDescripcion());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al modificar ", tarea.getDescripcion());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
    }
	
	public void onCellEdit(CellEditEvent event) {  
		Object oldValue = event.getOldValue();
	    Object newValue = event.getNewValue();
	        try {
	        	if(newValue != null && !newValue.equals(oldValue)) {
	        	    DataTable d = (DataTable) event.getSource();
	        	    Tarea t = (Tarea) d.getRowData();
	        	    registroTarea.modificar(t);
	            }
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La Tarea fue modificada exitosamente" , "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar la Tarea", "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
	}
	
	public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Se canceló modificar ", ((Tarea) event.getObject()).getDescripcion());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
	
	public void eliminar(Long id) {
		try {
			registroTarea.eliminar(id);
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
			registroTarea.buscar(id);
			FacesMessage msg = new FacesMessage("Se encontró ", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch(Exception e) {
			FacesMessage msg = new FacesMessage("Error al buscar", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		  
	}
	
    public void onRowSelect(SelectEvent event) {
        Long idTarea = ((Tarea) event.getObject()).getId();
        Long idOrden = ((Tarea) event.getObject()).getOrden().getId();
        generearListaTareasDisponibles(idOrden, idTarea);
    }
    
    public void agregarTareasPredecesoras(){
    	System.out.println("datos de la tarea seleccionada : " + tareaSeleccionada.getDescripcion());
    	System.out.println("se agregaran " + tareasSeleccionadas.size() + " -  tareas");
		registroTarea.agregarTareasPredecesoras(tareasSeleccionadas, tareaSeleccionada.getId());
		generearListaTareasDisponibles(tareaSeleccionada.getOrden().getId(), tareaSeleccionada.getId());
	}
	
	public void quitarTareasPredecesoras(){
		registroTarea.quitarTareasPredecesoras(tareasSeleccionadasQuitar, tareaSeleccionada);
		System.out.println("se agregaron " + tareasSeleccionadasQuitar.size() + " -  tareas");
		generearListaTareasDisponibles(tareaSeleccionada.getOrden().getId(), tareaSeleccionada.getId());
	}
	
    public void generearListaTareasDisponibles(Long idOrden, Long idTarea){
		List<Tarea> tareaInicial = new ArrayList<Tarea>();
		List<Tarea> tareasFinal = new ArrayList<Tarea>();
		tareaInicial = registroTarea.obtenerTareasDisponiblesPorOrden(idOrden,idTarea);
		for (Tarea tarea : tareaInicial) {
			boolean agregar = true;
			System.out.println("Tarea actual: " + tarea.getId());
			for (Tarea tareaPred : tarea.getTareasPredecesoras()) {
				System.out.println("Predecesora " +  tareaPred.getId() );
				if(tareaSeleccionada.getId() == tareaPred.getId()){
					agregar = false;					
				}
			}
			for(Tarea tar : tareaSeleccionada.getTareasPredecesoras()){
				if(tar.getId() == tarea.getId()){
					System.out.println("Comparo predecesora " +  tarea.getId() );
					agregar = false;
				}
			}
			System.out.println("Se agrega la tarea  :" + tarea.getId() + " - " + agregar);
			if(agregar == true){
				tareasFinal.add(tarea);
			}
		}
        setTareasDisponibles(tareasFinal);
	}
    
}
