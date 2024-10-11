package com.example.red.modelo;

import java.util.Objects;

public class Conexion {
	private Equipo equipo1;
	private Equipo equipo2;
	private TipoCable tipocable;
	private TipoPuerto tipoPuerto1;
	private TipoPuerto tipoPuerto2;
	

	public Conexion() {

	}

	public Conexion(Equipo equipo1, Equipo equipo2, TipoCable tipocable,TipoPuerto tipoPuerto1, TipoPuerto tipoPuerto2) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.tipocable = tipocable;
		this.tipoPuerto1=tipoPuerto1;
		this.tipoPuerto2=tipoPuerto2;
	}
	
	public TipoPuerto getTipoPuerto1() {
		return tipoPuerto1;
	}

	public void setTipoPuerto1(TipoPuerto tipoPuerto1) {
		this.tipoPuerto1 = tipoPuerto1;
	}

	public TipoPuerto getTipoPuerto2() {
		return tipoPuerto2;
	}

	public void setTipoPuerto2(TipoPuerto tipoPuerto2) {
		this.tipoPuerto2 = tipoPuerto2;
	}

	public TipoCable getTipocable() {
		return tipocable;
	}

	public void setTipocable(TipoCable tipocable) {
		this.tipocable = tipocable;
	}

	public Equipo getEquipo1() {
		return equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

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
