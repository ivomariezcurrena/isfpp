package com.example.red.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modela el componente 'equipo' de la red
 */
public class Equipo {

	/** Código único, identificador */
	private String codigo;

	/** Descripción del equipo */
	private String descripcion;

	/** Marca del equipo */
	private String marca;

	/** Modelo del equipo */
	private String modelo;

	/** Tipo de equipo */
	private TipoEquipo tipoEquipo;

	/** Ubicación del equipo */
	private Ubicacion ubicacion;

	/** Lista de puertos del equipo */
	private List<Puerto> puertos;

	/** Lista de direcciones IP del equipo */
	private List<String> direccionesIP;

	/** Bandera de actividad */
	private boolean activo;

	/** Constructor sin parámetros */
	public Equipo() {

	}

	/** Constructor con parámetros */
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

	/**
	 * Asignar las direcciones IP
	 * 
	 * @param direccionesIP lista de direcciones IP
	 */
	public void setDireccionesIP(List<String> direccionesIP) {
		this.direccionesIP = direccionesIP;
	}

	/**
	 * Obtener la descripción del equipo
	 * 
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asignar la descripción del equipo
	 * 
	 * @param descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtener la marca del equipo
	 * 
	 * @return marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * Asignar la marca del equipo
	 * 
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * Obtener el modelo del equipo
	 * 
	 * @return modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Asignar el modelo del equipo
	 * 
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Obtener el tipo de equipo del equipo
	 * 
	 * @return tipoEquipo
	 */
	public TipoEquipo getTipoEquipo() {
		return tipoEquipo;
	}

	/**
	 * Asignar el tipo de equipo del equipo
	 * 
	 * @param tipoEquipo
	 */
	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}

	/**
	 * Obtener la ubicación del equipo
	 * 
	 * @return ubicacion
	 */
	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	/**
	 * Asignar la ubicación del equipo
	 * 
	 * @param ubicacion
	 */
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Obtener el código del equipo
	 * 
	 * @return código
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Obtener el estado de actividad del equipo
	 * 
	 * @return true si está activo, false si está inactivo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Asignar el estado de actividad del equipo
	 * 
	 * @param activo true si está activo, false si está inactivo
	 */
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

	/**
	 * Lista los puertos del equipo
	 * 
	 * @return puertos
	 */
	public List<Puerto> getPuertos() {
		return puertos;
	}

	/**
	 * Lista las direcciones IP del equipo
	 * 
	 * @return direcciones ip
	 */
	public List<String> getDireccionesIP() {
		return direccionesIP;
	}

	/**
	 * Lista información de cada puerto, incluyendo el tipo de puerto y la cantidad
	 * 
	 * @return lista de objetos de pares { tipoPuerto, cantidad }
	 */
	public List<Object[]> getPuertosInfo() {
		List<Object[]> puertosInfo = new ArrayList<>();
		for (Puerto p : puertos) {
			puertosInfo.add(new Object[] { p.getTipoPuerto(), p.getCantidad() });
		}
		return puertosInfo;
	}

	/**
	 * Agregar una dirección IP al equipo
	 * 
	 * @param ip
	 */
	public void agregarDireccionIP(String ip) {
		direccionesIP.add(ip);
	}

	/**
	 * Agregar un puerto al equipo
	 * 
	 * @param tipoPuerto el tipo de puerto
	 * @param cantidad   la cantidad de puertos
	 */
	public void agregarPuerto(TipoPuerto tipoPuerto, int cantidad) {
		Puerto nuevoPuerto = new Puerto(tipoPuerto, cantidad);
		puertos.add(nuevoPuerto);
	}

	/**
	 * Obtener la velocidad de un puerto determinado
	 * 
	 * @param numeroPuerto
	 * @return velocidad del puerto, si no tiene retorna -1
	 */
	public int getVelocidadPuerto(int numeroPuerto) {
		// Si un equipo no tiene puerto retorna -1
		if (puertos.isEmpty())
			return -1;

		return puertos.get(numeroPuerto).getTipoPuerto().getVelocidad();
	}

	/**
	 * Modela el componente 'puerto' del equipo
	 */
	private class Puerto {
		/** cantidad de puertos */
		private int cantidad;

		/** tipo de puerto */
		private TipoPuerto tipoPuerto;

		/** Constructor */
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