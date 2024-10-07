package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.dao.secuencial.ConexionSecuancialDAO;
import com.example.red.modelo.Conexion;

public class ConexionServiceImpl implements ConexionService {
    private GenericDAO<String, Conexion> conexionDAO;

    public ConexionServiceImpl() {
        conexionDAO = new ConexionSecuancialDAO();
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
    public TreeMap<String, Conexion> buscarTodos() {
        return conexionDAO.buscarTodos();
    }

}
