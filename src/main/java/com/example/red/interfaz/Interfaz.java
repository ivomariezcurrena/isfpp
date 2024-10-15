package com.example.red.interfaz;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.red.controlador.Coordinador;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;


public class Interfaz {
    private Coordinador coordinador;
	private RedVisual ventana;
    
	/**
     * La instancia para utilizar la entrada por consola "System.in". Es necesario
     * utilizar solo una, ya que al llamar al método input.close() se cerrará
     * definitivamente el canal "System.in" (verificable viendo el status de
     * System.in.available())
	 */
    private static Scanner input;
    
    public Interfaz() {
        
    }

    public void setCoordinador(Coordinador coordinador){
        this.coordinador = coordinador;
    }

    /**
     * Inicia el canal de entrada por consola
	 */
    public void iniciarConsola() {
		input = new Scanner(System.in);
	}

	/*
	 * Inicia la interfaz grafica
	 */
	public void iniciarInterfaz(){
		ventana.renderizarRed();
		ventana.renderizarBotones();
	}

    /**
	 * Cierra el canal de entrada por consola
	 */
	public void cerrarConsola() {
		input.close();
	}

	/*
	 * Cierra la interfaz gráfica
	 */
	public void cerrarInterfaz(){
		ventana.dispose();
	}

    /**
	 * Permite ingresar comandos por consola 
	 * 
	 * @return arreglo donde el indice [0] es el comando elegido, [1] el
	 *         primer parámetro y [2] el segundo parámetro, si existen
	 */
	public String[] getInstruccion() {
		// Obtiene la entrada del usuario por consola
		String entrada = "";
		System.out.print(">>> ");
		entrada = input.nextLine().trim().replaceAll(" +", " "); // Limpio espacios innecesarios
		// Separa la entrada según los espacios
		String[] instruccion = entrada.split(" ");
		// El comando es case-insensitive
		instruccion[0] = instruccion[0].toLowerCase();
		return instruccion;
	}

	/**
	 * Muestra el menú de comandos por consola
	 */
	public void mostrarMenu() {

		System.out.println("==================================================================\n"
				+ "| BIENVENIDO A LA RED!                                           |\n"
				+ "==================================================================\n"
				+ "| INGRESE UN COMANDO:                                            |\n"
				+ "|----------------------------------------------------------------|\n"
				+ "| ping <IP/ID>                                                   |\n"
				+ "|     Verifica si un equipo está activo dado su IP o ID          |\n"
				+ "|----------------------------------------------------------------|\n"
				+ "| rango <IP (primeros digitos)>                                  |\n"
				+ "|     Devuelve el estado de todas las ip en un rango determinado |\n"
				+ "|----------------------------------------------------------------|\n"
				+ "| traceroute <IP/ID Origen> <IP/ID Destino>                      |\n"
				+ "|     Indica los equipos por donde circulan los paquetes desde   |\n"
				+ "|     el equipo origen hasta el equipo destino                   |\n"
				+ "|----------------------------------------------------------------|\n"
				+ "| help                                                           |\n"
				+ "|     Muestra el menú de comandos disponibles                    |\n"
				+ "|----------------------------------------------------------------|\n"
				+ "| exit                                                           |\n"
				+ "|     Finaliza la ejecución del programa                         |\n"
				+ "==================================================================\n"
				+ "| IMPORTANTE! es case-sensitive para los parámetros de comandos  |\n"
				+ "==================================================================");
	}

	/**
	 * Muestra un mensaje
	 */
    public void mostrar(String mensaje){
        System.out.println(mensaje);
    }

	/**
	 * Muestra un mensaje de error
	 */
	public void mostrarError(String mensaje) {
		System.err.println(mensaje);
	}

	public void cargarDatos(Map<String, Equipo> tablaEquipos, Map<String, Conexion> tablaConexiones){
		ventana = new RedVisual();
		ventana.cargarDatos(tablaEquipos, tablaConexiones);
	}

	public void setEstiloNodo(String id, String tipoEstilo){
		ventana.setEstiloNodo(id, tipoEstilo);
	}

	public void setEstiloArco(String id1, String id2, String tipoEstilo){
		ventana.setEstiloArco(id1, id2, tipoEstilo);
	}

	public void setEstiloNodos(List<String> ids, String tipoEstilo){
		ventana.setEstiloNodos(ids, tipoEstilo);
	}

	public void setEstiloArcos(List<String[]> paresIds, String tipoEstilo){
		ventana.setEstiloArcos(paresIds, tipoEstilo);
	}

	public void setEstiloCaminoArcos(List<String> ids, String tipoEstilo){
		ventana.setEstiloCaminoArcos(ids, tipoEstilo);
	}

	public void setEstiloNodosTodos(String tipoEstilo){
		ventana.setEstiloNodosTodos(tipoEstilo);
	}

	public void setEstiloArcosTodos(String tipoEstilo){
		ventana.setEstiloArcosTodos(tipoEstilo);
	}
}
