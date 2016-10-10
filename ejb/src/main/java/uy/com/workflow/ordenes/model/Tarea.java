package uy.com.workflow.ordenes.model;

import java.io.Serializable;
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
@Table(name = "tareas")
public class Tarea implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String descripcion;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="orden_id")
	private Orden orden;
		
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="estado_id")
	private Estado estado;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="puesto_id")
	private Puesto puesto;
	
	@OneToMany(mappedBy="tarea", cascade={CascadeType.ALL})
	private Set<Notificacion> notificaciones;
	
	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinTable(name="Tpredecesoras",
		joinColumns={@JoinColumn(name="tarea_id")},
		inverseJoinColumns={@JoinColumn(name="predecesora_id")})
	private Set<Tarea> tareasPredecesoras = new HashSet<Tarea>();

	@ManyToMany(mappedBy="tareasPredecesoras")
	private Set<Tarea> tareaAsignada = new HashSet<Tarea>();

	public Tarea() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Set<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(Set<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public Set<Tarea> getTareasPredecesoras() {
		return tareasPredecesoras;
	}

	public void setTareasPredecesoras(Set<Tarea> tareasPredecesoras) {
		this.tareasPredecesoras = tareasPredecesoras;
	}

	public Set<Tarea> getTareaAsignada() {
		return tareaAsignada;
	}

	public void setTareaAsignada(Set<Tarea> tareaAsignada) {
		this.tareaAsignada = tareaAsignada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notificaciones == null) ? 0 : notificaciones.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((puesto == null) ? 0 : puesto.hashCode());
		result = prime * result + ((tareaAsignada == null) ? 0 : tareaAsignada.hashCode());
		result = prime * result + ((tareasPredecesoras == null) ? 0 : tareasPredecesoras.hashCode());
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
		Tarea other = (Tarea) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notificaciones == null) {
			if (other.notificaciones != null)
				return false;
		} else if (!notificaciones.equals(other.notificaciones))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (puesto == null) {
			if (other.puesto != null)
				return false;
		} else if (!puesto.equals(other.puesto))
			return false;
		if (tareaAsignada == null) {
			if (other.tareaAsignada != null)
				return false;
		} else if (!tareaAsignada.equals(other.tareaAsignada))
			return false;
		if (tareasPredecesoras == null) {
			if (other.tareasPredecesoras != null)
				return false;
		} else if (!tareasPredecesoras.equals(other.tareasPredecesoras))
			return false;
		return true;
	}

}
