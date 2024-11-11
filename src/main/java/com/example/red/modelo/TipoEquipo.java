package com.example.red.modelo;

import java.util.Objects;

/**
 * Modela el componente 'tipo de equipo' de la red
 */
public class TipoEquipo {

	/** Codigo único del tipo de equipo*/
	private String codigo;

	/** Descripción */
	private String descripcion;

	/** Constructor sin parámetros */
	public TipoEquipo() {

	}

	/** Constructor con parámetros */
	public TipoEquipo(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	/**
	 * Obtener la descripción del tipo de equipo
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asignar la descripción del tipo de equipo
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtener el código del tipo de equipo
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
		TipoEquipo other = (TipoEquipo) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "TipoEquipo [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}

}
