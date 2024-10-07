package com.example.red.servicio;

import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.dao.secuencial.TipoPuertoSecuencialDAO;
import com.example.red.modelo.TipoPuerto;

public class TipoPuertoServiceImpl implements TipoPuertoService {
    private GenericDAO<String, TipoPuerto> tipoPuertoDAO;

    public TipoPuertoServiceImpl() {
        tipoPuertoDAO = new TipoPuertoSecuencialDAO();
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
