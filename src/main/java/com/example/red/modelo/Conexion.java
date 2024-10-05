package com.example.red.modelo;

import java.util.Objects;

public class Conexion {
	private Equipo equipo1;
	private Equipo equipo2;
	private TipoCable tipocable;

	public Conexion() {

	}

	public Conexion(Equipo equipo1, Equipo equipo2, TipoCable tipocable) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.tipocable = tipocable;
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
