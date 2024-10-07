package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.dao.secuencial.TipoEquipoSecuencialDAO;
import com.example.red.modelo.TipoEquipo;

public class TipoEquipoServiceImpl implements TipoEquipoService {
    private GenericDAO<String, TipoEquipo> tipoEquipoDAO;

    public TipoEquipoServiceImpl() {
        tipoEquipoDAO = new TipoEquipoSecuencialDAO();
    }

    @Override
    public void insertar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.insertar(tipoEquipo);
    }

    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.actualizar(tipoEquipo);
    }

    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        tipoEquipoDAO.borrar(tipoEquipo);
    }

    @Override
    public TreeMap<String, TipoEquipo> buscarTodos() {
        return tipoEquipoDAO.buscarTodos();
    }

}
