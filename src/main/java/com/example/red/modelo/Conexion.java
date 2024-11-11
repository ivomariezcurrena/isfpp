package com.example.red.modelo;

import java.util.Objects;

/**
 * Modela el componente 'conexión' de la red
 */
public class Conexion {

	/** Equipo 1 de la conexión */
	private Equipo equipo1;

	/** Equipo 2 de la conexión */
	private Equipo equipo2;

	/** Tipo de cable usado para la conexión */
	private TipoCable tipocable;

	/** Tipo de puerto 1 de la conexión */
	private TipoPuerto tipoPuerto1;

	/** Tipo de puerto 2 de la conexión */
	private TipoPuerto tipoPuerto2;

	/** Constructor sin parámetros */
	public Conexion() {

	}

	/** Constructor con parámetros */
	public Conexion(Equipo equipo1, Equipo equipo2, TipoCable tipocable, TipoPuerto tipoPuerto1,
			TipoPuerto tipoPuerto2) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.tipocable = tipocable;
		this.tipoPuerto1 = tipoPuerto1;
		this.tipoPuerto2 = tipoPuerto2;
	}

	/**
	 * Obtiene el tipo de puerto 1
	 * 
	 * @return tipoPuerto1
	 */
	public TipoPuerto getTipoPuerto1() {
		return tipoPuerto1;
	}

	/**
	 * Asigna el tipo de puerto 1
	 * 
	 * @param tipoPuerto1
	 */
	public void setTipoPuerto1(TipoPuerto tipoPuerto1) {
		this.tipoPuerto1 = tipoPuerto1;
	}

	/**
	 * Obtiene el tipo de puerto 2
	 * 
	 * @return tipoPuerto2
	 */
	public TipoPuerto getTipoPuerto2() {
		return tipoPuerto2;
	}

	/**
	 * Asigna el tipo de puerto 2
	 * 
	 * @param tipoPuerto2
	 */
	public void setTipoPuerto2(TipoPuerto tipoPuerto2) {
		this.tipoPuerto2 = tipoPuerto2;
	}

	/**
	 * Obtiene el tipo de cable de la conexión
	 * 
	 * @return tipoCable
	 */
	public TipoCable getTipocable() {
		return tipocable;
	}

	/**
	 * Asigna el tipo de cable de la conexión
	 * 
	 * @param tipoCable
	 */
	public void setTipocable(TipoCable tipocable) {
		this.tipocable = tipocable;
	}

	/**
	 * Obtiene el equipo 1
	 * 
	 * @return equipo1
	 */
	public Equipo getEquipo1() {
		return equipo1;
	}

	/**
	 * Obtiene el equipo 2
	 * 
	 * @return equipo2
	 */
	public Equipo getEquipo2() {
		return equipo2;
	}

	/**
	 * Asigna el equipo 1
	 * 
	 * @param equipo1
	 */
	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	/**
	 * Asigna el equipo 2
	 * 
	 * @param equipo2
	 */
	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(equipo1, equipo2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conexion other = (Conexion) obj;
		return Objects.equals(equipo1, other.equipo1) && Objects.equals(equipo2, other.equipo2);
	}

	@Override
	public String toString() {
		return "Conexion [equipo1=" + equipo1 + ", equipo2=" + equipo2 + ", tipocable=" + tipocable + "]";
	}

}