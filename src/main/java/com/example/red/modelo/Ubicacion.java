package com.example.red.modelo;

import java.util.Objects;

/**
 * Modela 'ubicaciones' de la red. Los equipos están localizados en
 * esas ubicaciones
 */
public class Ubicacion {

	/** Código único de la ubicación */
	private String codigo;

	/** Descripción de la ubicación */
	private String descripcion;

	/** Constructor sin parámetros */
	public Ubicacion() {

	}

	/** Constructor con parámetros */
	public Ubicacion(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	/**
	 * Obtener la descripción de la ubicación
	 * 
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asignar la descripción de la ubicación
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtener el código de la ubicación
	 * 
	 * @return código
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Asignar el código de la ubicación
	 * 
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		Ubicacion other = (Ubicacion) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Ubicacion [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}

}
