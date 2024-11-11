package com.example.red.servicio;

import java.util.TreeMap;
import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoPuerto;

/**
 * Implementación de la interfaz TipoPuertoService
 */
public class TipoPuertoServiceImpl implements TipoPuertoService {

    /** DAO para los tipos de puertos */
    private GenericDAO<String, TipoPuerto> tipoPuertoDAO;

    /** Constructor */
    public TipoPuertoServiceImpl() {
        tipoPuertoDAO =Factory.getInstancia("TIPOPUERTO");
    }

    @Override
    public void insertar(TipoPuerto tipoPuerto) {
        tipoPuertoDAO.insertar(tipoPuerto);
    }

    @Override
    public void actualizar(TipoPuerto tipoPuerto) {
        tipoPuertoDAO.actualizar(tipoPuerto);
    }

    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        tipoPuertoDAO.borrar(tipoPuerto);
    }

    @Override
    public TreeMap<String, TipoPuerto> buscarTodos() {
        return tipoPuertoDAO.buscarTodos();
    }

}
