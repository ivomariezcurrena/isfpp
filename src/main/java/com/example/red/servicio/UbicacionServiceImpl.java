package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Ubicacion;

public class UbicacionServiceImpl implements UbicacionService {
    private GenericDAO<String, Ubicacion> UbicacionDAO;

    public UbicacionServiceImpl() {
        UbicacionDAO = Factory.getInstancia("EQUIPO");
    }

    @Override
    public void insertar(Ubicacion ubicacion) {
        UbicacionDAO.insertar(ubicacion);
    }

    @Override
    public void actualizar(Ubicacion ubicacion) {
        UbicacionDAO.actualizar(ubicacion);
    }

    @Override
    public void borrar(Ubicacion ubicacion) {
        UbicacionDAO.borrar(ubicacion);
    }

    @Override
    public TreeMap<String, Ubicacion> buscarTodos() {
        return UbicacionDAO.buscarTodos();
    }

}
