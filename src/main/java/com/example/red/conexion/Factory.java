package com.example.red.conexion;

import java.util.Hashtable;
import java.util.ResourceBundle;

public class Factory {
	private static Hashtable<String, Object> instancias = new Hashtable<>();

	public static <T> T getInstancia(String objName) {
		try {
			// Verifico si existe un objeto relacionado a objName en la hashtable
			T obj = (T) instancias.get(objName);

			// Si no existe, lo instancio y lo agrego
			if (obj == null) {
				// Obtengo el nombre de la clase desde un archivo de recursos
				ResourceBundle rb = ResourceBundle.getBundle("factory");
				String sClassname = rb.getString(objName);

				// Instancio la clase usando el constructor sin parámetros
				obj = (T) Class.forName(sClassname).getDeclaredConstructor().newInstance();

				// Agrego el objeto a la hashtable
				instancias.put(objName, obj);
			}

			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
