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

import uy.com.workflow.ordenes.controller.RegistroOrden;
import uy.com.workflow.ordenes.model.Estado;
import uy.com.workflow.ordenes.model.Orden;


@ManagedBean
@ViewScoped
public class OrdenBean {

	@Inject
	private RegistroOrden registroOrden;
	
	private Orden ordenSeleccionada;
	
	private List<Orden> ordenesSeleccionadas;
	
	private List<Orden> ordenesSeleccionadasQuitar;
	
	private List<Orden> ordenesDisponibles;
	    
	private List<Orden> ordenesFiltradas;
	
	private List<String> EstadosString = new ArrayList<String>();
	
	private boolean mostrarTabOrden = false;
	
	private boolean mostrarTabTarea = false;

	public RegistroOrden getRegistroOrden() {
		return registroOrden;
	}

	public void setRegistroOrden(RegistroOrden registroOrden) {
		this.registroOrden = registroOrden;
	}

	public Orden getOrdenSeleccionada() {
		return ordenSeleccionada;
	}
	
	public void setOrdenSeleccionada(Orden orden){
		ordenSeleccionada = orden;
	}

	public List<Orden> getOrdenesSeleccionadas(){
		return ordenesSeleccionadas;
	}
	
	public void setOrdenesSeleccionadas(List<Orden> ordenes){
		ordenesSeleccionadas = ordenes;
	}
	
	public List<Orden> getOrdenesSeleccionadasQuitar(){
		return ordenesSeleccionadasQuitar;
	}
	
	public void setOrdenesSeleccionadasQuitar(List<Orden> ordenes){
		ordenesSeleccionadasQuitar = ordenes;
	}

	public List<Orden> getOrdenesDisponibles(){
		return ordenesDisponibles;
	}
	
	public void setOrdenesDisponibles(List<Orden> ordenes){
		ordenesDisponibles = ordenes;
	}
	
	public List<Orden> getOrdenesFiltradas(){
		return ordenesFiltradas;
	}
	
	public void setOrdenesFiltradas(List<Orden> ordenes){
		ordenesFiltradas = ordenes;
	}

	public List<String> getEstadosString(){
		for (Estado e : registroOrden.getEstados()) {
			EstadosString.add(e.getnombre());
		}
		return EstadosString;
	}
	
	public void setEstadosString(List<String> estados){
		EstadosString = estados;
	}

	public boolean isMostrarTabOrden() {
		return mostrarTabOrden;
	}

	public void setMostrarTabOrden(boolean mostrarTabOrden) {
		this.mostrarTabOrden = mostrarTabOrden;
	}

	public boolean isMostrarTabTarea() {
		return mostrarTabTarea;
	}

	public void setMostrarTabTarea(boolean mostrarTabTarea) {
		this.mostrarTabTarea = mostrarTabTarea;
	}

	public void registrar() {
		try {
			registroOrden.registro();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registró ", "con éxito!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch (Exception e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al registrar ", "");  
        FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	
	public void onEdit(RowEditEvent event) {  
		Orden orden = ((Orden) event.getObject());
           
            try {
            	registroOrden.modificar(orden);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modificó ", orden.getDetalle());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al modificar ", orden.getDetalle());  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
    }
	
	public void onCellEdit(CellEditEvent event) {  
		Object oldValue = event.getOldValue();
	    Object newValue = event.getNewValue();
	        try {
	        	if(newValue != null && !newValue.equals(oldValue)) {
	        	    DataTable d = (DataTable) event.getSource();
	        	    Orden o = (Orden) d.getRowData();
	        	    registroOrden.modificar(o);
	            }
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La Orden fue modificado exitosamente" , "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar la Orden", "");  
	            FacesContext.getCurrentInstance().addMessage(null, msg); 
			}
	}
	
	public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Se canceló modificar ", ((Orden) event.getObject()).getDetalle());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
	
	public void eliminar(Long id) {
		try {
			registroOrden.eliminar(id);
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
			registroOrden.buscar(id);
			FacesMessage msg = new FacesMessage("Se encontró ", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		catch(Exception e) {
			FacesMessage msg = new FacesMessage("Error al buscar", id.toString());  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		  
	}

    public void onRowSelect(SelectEvent event) {
        Long idOrden = ((Orden) event.getObject()).getId();
        Long idCli = ((Orden) event.getObject()).getCliente().getId();
        generearListaOrdenesDisponibles(idOrden, idCli);
        setMostrarTabOrden(true);
        setMostrarTabTarea(true);
    }
	
	public void agregarOrdenesPredecesoras(){
		registroOrden.agregarOrdenesPredecesoras(ordenesSeleccionadas, ordenSeleccionada);
		generearListaOrdenesDisponibles(ordenSeleccionada.getId(), ordenSeleccionada.getCliente().getId());
	}
	
	public void quitarOrdenesPredecesoras(){
		registroOrden.quitarOrdenesPredecesoras(ordenesSeleccionadasQuitar, ordenSeleccionada);
		generearListaOrdenesDisponibles(ordenSeleccionada.getId(), ordenSeleccionada.getCliente().getId());
	}
	
	public void generearListaOrdenesDisponibles(Long idOrden, Long idCli){
		List<Orden> ordenesInicial = new ArrayList<Orden>();
		List<Orden> ordenesFinal = new ArrayList<Orden>();
		ordenesInicial = registroOrden.obtenerOrdenesDisponiblesPorCliente(idOrden,idCli);
		for (Orden orden : ordenesInicial) {
			boolean agregar = true;
			System.out.println("Orden actual: " + orden.getId());
			for (Orden ordenPred : orden.getOrdenesPredecesoras()) {
				System.out.println("Predecesora " +  ordenPred.getId() );
				if(ordenSeleccionada.getId() == ordenPred.getId()){
					agregar = false;					
				}
			}
			for(Orden ord : ordenSeleccionada.getOrdenesPredecesoras()){
				if(ord.getId() == orden.getId()){
					System.out.println("Comparo predecesora " +  orden.getId() );
					agregar = false;
				}
			}
			System.out.println("Se agrega la orden  :" + orden.getId() + " - " + agregar);
			if(agregar == true){
				ordenesFinal.add(orden);
			}
		}
        setOrdenesDisponibles(ordenesFinal);
	}
	
	public void agregarTareasOrden(){
		try {
			registroOrden.agregarTareasOrden(ordenSeleccionada.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
