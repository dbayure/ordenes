package uy.com.workflow.ordenes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "ordenes")
public class Orden implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	private String detalle;
	
	@OneToMany(mappedBy="orden", cascade={CascadeType.ALL})
	private Set<Tarea> tareas;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="estado_id")
	private Estado estado;
	
	private Date fechaInicio;
	
	private Date fechaEntrega;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Opredecesoras",
		joinColumns={@JoinColumn(name="orden_id")},
		inverseJoinColumns={@JoinColumn(name="predecesora_id")})
	private Set<Orden> ordenesPredecesoras = new HashSet<Orden>();

	@ManyToMany(mappedBy="ordenesPredecesoras")
	private Set<Orden> ordenAsignada = new HashSet<Orden>();
	
	public Orden() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Set<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Set<Orden> getOrdenesPredecesoras() {
		return ordenesPredecesoras;
	}

	public void setOrdenesPredecesoras(Set<Orden> ordenesPredecesoras) {
		this.ordenesPredecesoras = ordenesPredecesoras;
	}

	public Set<Orden> getOrdenAsignada() {
		return ordenAsignada;
	}

	public void setOrdenAsignada(Set<Orden> ordenAsignada) {
		this.ordenAsignada = ordenAsignada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((detalle == null) ? 0 : detalle.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaEntrega == null) ? 0 : fechaEntrega.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordenAsignada == null) ? 0 : ordenAsignada.hashCode());
		result = prime * result + ((ordenesPredecesoras == null) ? 0 : ordenesPredecesoras.hashCode());
		result = prime * result + ((tareas == null) ? 0 : tareas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orden other = (Orden) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (detalle == null) {
			if (other.detalle != null)
				return false;
		} else if (!detalle.equals(other.detalle))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaEntrega == null) {
			if (other.fechaEntrega != null)
				return false;
		} else if (!fechaEntrega.equals(other.fechaEntrega))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordenAsignada == null) {
			if (other.ordenAsignada != null)
				return false;
		} else if (!ordenAsignada.equals(other.ordenAsignada))
			return false;
		if (ordenesPredecesoras == null) {
			if (other.ordenesPredecesoras != null)
				return false;
		} else if (!ordenesPredecesoras.equals(other.ordenesPredecesoras))
			return false;
		if (tareas == null) {
			if (other.tareas != null)
				return false;
		} else if (!tareas.equals(other.tareas))
			return false;
		return true;
	}

}
