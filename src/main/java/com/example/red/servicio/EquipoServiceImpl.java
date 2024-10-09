package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Equipo;

public class EquipoServiceImpl implements EquipoService {
    private GenericDAO<String, Equipo> equipoDAO;

    public EquipoServiceImpl() {
        equipoDAO = Factory.getInstancia("EQUIPO");
    }

    @Override
    public void insertar(Equipo equipo) {
        equipoDAO.insertar(equipo);
    }

    @Override
    public void actualizar(Equipo equipo) {
        equipoDAO.actualizar(equipo);
    }

    @Override
    public void borrar(Equipo equipo) {
        equipoDAO.borrar(equipo);
    }

    @Override
    public TreeMap<String, Equipo> buscarTodos() {
        return equipoDAO.buscarTodos();
    }

}
