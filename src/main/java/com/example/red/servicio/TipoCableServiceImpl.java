package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.conexion.Factory;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoCable;

public class TipoCableServiceImpl implements TipoCableService {
    private GenericDAO<String, TipoCable> tipoCableDAO;

    public TipoCableServiceImpl() {
        tipoCableDAO =Factory.getInstancia("TIPOCABLE");
    }

    @Override
    public void insertar(TipoCable tipoCable) {
        tipoCableDAO.insertar(tipoCable);
    }

    @Override
    public void actualizar(TipoCable tipoCable) {
        tipoCableDAO.actualizar(tipoCable);
    }

    @Override
    public void borrar(TipoCable tipoCable) {
        tipoCableDAO.borrar(tipoCable);
    }

    @Override
    public TreeMap<String, TipoCable> buscarTodos() {
        return tipoCableDAO.buscarTodos();
    }

}
