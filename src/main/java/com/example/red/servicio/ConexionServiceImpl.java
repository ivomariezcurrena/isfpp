package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Conexion;

public class ConexionServiceImpl implements ConexionService {
    private GenericDAO<String, Object> conexionDAO;

    public ConexionServiceImpl() {
        conexionDAO = (GenericDAO<String, Object>) Factory.getInstancia("CONEXION");
    }

    @Override
    public void insertar(Conexion conexion) {
        conexionDAO.insertar(conexion);
    }

    @Override
    public void actualizar(Conexion conexion) {
        conexionDAO.actualizar(conexion);
    }

    @Override
    public void borrar(Conexion conexion) {
        conexionDAO.borrar(conexion);
    }

    @Override
    public TreeMap<String, Object> buscarTodos() {
        return conexionDAO.buscarTodos();
    }

}
