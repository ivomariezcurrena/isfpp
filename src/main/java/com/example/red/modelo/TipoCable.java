package com.example.red.modelo;

import java.util.Objects;

/**
 * Modela el componente 'tipo de cable' de la red. Los tipos de cables se usan
 * para guardar más información sobre las conexiones
 */
public class TipoCable {

	/** Codigo único del tipo de cable */
	private String codigo;

	/** Descripción del tipo de cable */
	private String descripcion;

	/** Velocidad del tipo de cable medido en Mbps */
	private int velocidad;

	/** Constructor sin parámetros */
	public TipoCable() {

	}

	/** Constructor con parámetros */
	public TipoCable(String codigo, String descripcion, int velocidad) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.velocidad = velocidad;
	}

	/**
	 * Obtener la descripción del tipo de cable
	 * 
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asignar la descripción del tipo de cable
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtener la velocidad del tipo de cable
	 * 
	 * @return velocidad en Mbps
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * Asignar la velocidad del tipo de cable
	 * 
	 * @param velocidad en Mbps
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * Obtener el código único del tipo de cable
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
		TipoCable other = (TipoCable) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
