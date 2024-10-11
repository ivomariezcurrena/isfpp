package com.example.red.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipo {
	private String codigo;
	private String descripcion;
	private String marca;
	private String modelo;
	private TipoEquipo tipoEquipo;
	private Ubicacion ubicacion;
	private List<Puerto> puertos;
	private List<String> direccionesIP;
	private boolean activo;

	public Equipo() {

	}

	public Equipo(String codigo, String descripcion, String marca, String modelo, TipoEquipo tipoEquipo,
			Ubicacion ubicacion, boolean activo) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.marca = marca;
		this.modelo = modelo;
		direccionesIP = new ArrayList<String>();
		this.tipoEquipo = tipoEquipo;
		this.ubicacion = ubicacion;
		puertos = new ArrayList<Puerto>();
		this.activo = activo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public TipoEquipo getTipoEquipo() {
		return tipoEquipo;
	}

	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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
		Equipo other = (Equipo) obj;
		return Objects.equals(codigo, other.codigo);
	}

	public List<Puerto> getPuertos() {
		return puertos;
	}

	public List<String> getDireccionesIP() {
		return direccionesIP;
	}

	public void agregarPuerto(TipoPuerto tipoPuerto, int cantidad) {
		Puerto nuevoPuerto = new Puerto(tipoPuerto, cantidad);
		puertos.add(nuevoPuerto);
	}

	public int getVelocidadPuerto(int numeroPuerto) {
		// Si un equipo no tiene puerto retorna -1
		if (puertos.isEmpty())
			return -1;

		return puertos.get(numeroPuerto).getTipoPuerto().getVelocidad();
	}

	private class Puerto {
		private int cantidad;
		private TipoPuerto tipoPuerto;

		public Puerto(TipoPuerto tipoPuerto, int cantidad) {
			super();
			this.cantidad = cantidad;
			this.tipoPuerto = tipoPuerto;
		}

		@SuppressWarnings("unused")
		public TipoPuerto getTipoPuerto() {
			return tipoPuerto;
		}

		@SuppressWarnings("unused")
		public void setTipoPuerto(TipoPuerto tipoPuerto) {
			this.tipoPuerto = tipoPuerto;
		}

		@SuppressWarnings("unused")
		public int getCantidad() {
			return cantidad;
		}

	}

}
