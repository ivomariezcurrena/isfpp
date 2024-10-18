package com.example.red.dao.secuenciabd;

import java.sql.Connection;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoPuerto;

public class EquipoSecuencialSqlDAO implements GenericDAO<String, Equipo> {
    private Connection conexion;
    private TreeMap<String, TipoPuerto> puertos;
    private String[] ips;

    public TipoCableSecuencialSqlDAO(Connection conexion) {
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public void insertar(Equipo equipo) {
        String sql = "INSERT INTO poo2024.Equipo_ivoma()"  
    }
}
