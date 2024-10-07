package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoPuerto;

public class TipoPuertoServiceImpl implements TipoPuertoService {
    private GenericDAO<String, Object> tipoPuertoDAO;

    public TipoPuertoServiceImpl() {
        tipoPuertoDAO = (GenericDAO<String, Object>) Factory.getInstancia("TIPOPUERTO");
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
    public TreeMap<String, Object> buscarTodos() {
        return tipoPuertoDAO.buscarTodos();
    }

}
