package com.example.red.dao.secuencial;

import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;

public class ConexionSecuancialDAO implements GenericDAO<Conexion, String> {
    private TreeMap<String, Conexion> map;
    private String name;
    private Hashtable<String, Equipo> equipos;
    private boolean actualizar;

    public ConexionSecuancialDAO() {
        equipos = cargarEquipos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("tramo");
        actualizar = true;
    }

    private Hashtable<String, Equipo> cargarEquipos() {
        Hashtable<String, Equipo> equipos = new Hashtable<String, Equipo>();
        GenericDAO estacionDAO = new EquipoSecuencialDAO();
        List<Equipo> ds = estacionDAO.buscarTodos();
        for (Equipo d : ds)
            equipos.put(d.getCodigo(), d);
        return equipos;
    }
}
