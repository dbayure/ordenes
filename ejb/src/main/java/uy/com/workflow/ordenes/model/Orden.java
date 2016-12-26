package uy.com.workflow.ordenes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table(name = "ordenes")
@JsonIgnoreProperties({"tareas,estado,cliente"})
public class Orden implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity=Cliente.class,fetch=FetchType.LAZY)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	private String detalle;
	
	@OneToMany(mappedBy="orden", cascade={CascadeType.ALL})
	private Set<Tarea> tareas;
	
	@ManyToOne(targetEntity=Estado.class,fetch=FetchType.LAZY)
	@JoinColumn(name="estado_id")
	private Estado estado;
	
	private Date fechaInicio;
	
	private Date fechaEntrega;

	@ManyToMany()
	@JoinTable(name="Opredecesoras",
		joinColumns={@JoinColumn(name="orden_id")},
		inverseJoinColumns={@JoinColumn(name="predecesora_id")})
	private List<Orden> ordenesPredecesoras = new ArrayList<Orden>();

	@ManyToMany(mappedBy="ordenesPredecesoras")
	private List<Orden> ordenAsignada = new ArrayList<Orden>();
	
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

	public List<Orden> getOrdenesPredecesoras() {
		return ordenesPredecesoras;
	}

	public void setOrdenesPredecesoras(List<Orden> ordenesPredecesoras) {
		this.ordenesPredecesoras = ordenesPredecesoras;
	}

	public List<Orden> getOrdenAsignada() {
		return ordenAsignada;
	}

	public void setOrdenAsignada(List<Orden> ordenAsignada) {
		this.ordenAsignada = ordenAsignada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detalle == null) ? 0 : detalle.hashCode());
		result = prime * result + ((fechaEntrega == null) ? 0 : fechaEntrega.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (detalle == null) {
			if (other.detalle != null)
				return false;
		} else if (!detalle.equals(other.detalle))
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
		return true;
	}

	
}
