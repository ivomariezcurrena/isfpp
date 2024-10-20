package com.example.red.dao.secuencialbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoEquipo;

public class TipoEquipoSqlDAO implements GenericDAO<String, TipoEquipo> {
    private Connection conexion;

    public TipoEquipoSqlDAO() {
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public void insertar(TipoEquipo tipoEquipo) {
        String sql = "INSERT INTO poo2024.TipoEquipo_ivoma (codigo, descripcion) VALUES (?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoEquipo.getCodigo());
            pst.setString(2, tipoEquipo.getDescripcion());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar tipo de equipo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        String sql = "UPDATE poo2024.TipoEquipo_ivoma SET descripcion = ? WHERE codigo = ?";
        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoEquipo.getDescripcion());
            pst.setString(2, tipoEquipo.getCodigo());
            int filasActualizadas = pst.executeUpdate();
            if (filasActualizadas == 0) {
                System.out.println("Tipo de equipo no encontrada: " + tipoEquipo.getCodigo());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar tipo de equipo: " + e.getMessage());
        }
    }

    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        String sql = "DELETE FROM poo2024.TipoEquipo_ivoma WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoEquipo.getCodigo());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al borrar ubicación: " + e.getMessage());
        }
    }

    public TreeMap<String, TipoEquipo> buscarTodos() {
        TreeMap<String, TipoEquipo> map = new TreeMap<>();
        String sql = "SELECT * FROM poo2024.TipoEquipo_ivoma";

        try (Statement stmt = conexion.createStatement();
                ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                String codigo = res.getString("codigo");
                String descripcion = res.getString("descripcion");
                map.put(codigo, new TipoEquipo(codigo, descripcion));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar todos los tipos de equipo: " + e.getMessage());
        }
        return map;
    }
}