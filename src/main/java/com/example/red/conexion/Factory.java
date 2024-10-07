package com.example.red.conexion;

import java.util.Hashtable;
import java.util.ResourceBundle;

import com.example.red.dao.GenericDAO;

public class Factory {
	private static Hashtable<String, GenericDAO<String,Object>> instancias = new Hashtable<String, GenericDAO<String,Object>>();

	public static GenericDAO<String,Object> getInstancia(String objName) {
		try {
			// verifico si existe un objeto relacionado a objName
			// en la hashtable
			GenericDAO<String,Object> obj = instancias.get(objName);
			// si no existe entonces lo instancio y lo agrego
			if (obj == null) {
				ResourceBundle rb = ResourceBundle.getBundle("factory");
				String sClassname = rb.getString(objName);
				obj = (GenericDAO<String, Object>) Class.forName(sClassname).newInstance();
				// agrego el objeto a la hashtable
				instancias.put(objName, obj);
			}
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
