package com.example.red.modelo;

import java.util.Objects;

/**
 * Modela el componente 'tipo de puerto' de la red. Los tipos de puertos
 * almacenan características comunes de puertos de equipos
 */
public class TipoPuerto {

	/** Codigo único del tipo de puerto */
	private String codigo;

	/** Descripción del tipo de puerto */
	private String descripcion;

	/** Velocidad del tipo de puerto, medido en Mbps */
	private int velocidad;

	/** Constructor sin parámetros */
	public TipoPuerto() {

	}

	/** Constructor con parámetros */
	public TipoPuerto(String codigo, String descripcion, int velocidad) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.velocidad = velocidad;
	}

	/**
	 * Obtener la descripción del tipo de puerto
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asignar la descripción del tipo de puerto
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtener la velocidad del tipo de puerto
	 * 
	 * @return velocidad en Mbps
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * Asignar la velocidad del tipo de puerto
	 * 
	 * @param velocidad en Mbps
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * Obtener el código del tipo de puerto
	 * 
	 * @return código
	 */
	public String getCodigo() {
		return codigo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoPuerto other = (TipoPuerto) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "TipoPuerto [codigo=" + codigo + ", descripcion=" + descripcion + ", velocidad=" + velocidad + "]";
	}

}
