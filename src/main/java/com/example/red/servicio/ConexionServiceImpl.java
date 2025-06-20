package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Conexion;

/**
 * Implementación de la interfaz ConexionService
 */
public class ConexionServiceImpl implements ConexionService {

    /** DAO para las conexiones */
    private GenericDAO<String, Conexion> conexionDAO;

    /** Constructor */
    public ConexionServiceImpl() {
        conexionDAO = Factory.getInstancia("CONEXION");
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
