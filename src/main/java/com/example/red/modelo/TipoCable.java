package com.example.red.modelo;

import java.util.Objects;

public class TipoCable {
	private String codigo;
	private String descripcion; 
	private int velocidad;
	
	public TipoCable (String codigo, String descripcion, int velocidad) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.velocidad = velocidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

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
