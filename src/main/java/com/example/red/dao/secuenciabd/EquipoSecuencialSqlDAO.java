package com.example.red.dao.secuenciabd;

import java.sql.Connection;

import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Equipo;

public class EquipoSecuencialSqlDAO implements GenericDAO<String, Equipo> {
    private Connection conexion;

    public TipoCableSecuencialSqlDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Equipo equipo) {
        String sql = "INSERT INTO poo2024.Equipo_ivoma()"
    }
}
