package com.example.red.dao.secuenciabd;

import java.sql.Connection;
import java.util.TreeMap;
import java.sql.*;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoPuerto;

public class TipoPuertoSecuencialSqlDAO implements GenericDAO<String, TipoPuerto> {
    private Connection conexion;

    public TipoPuertoSecuencialSqlDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(TipoPuerto tipoPuerto) {
        String sql = "INSERT INTO poo2024.TipoPuerto_ivoma (codigo, descripcion, velocidad) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoPuerto.getCodigo());
            pst.setString(2, tipoPuerto.getDescripcion());
            pst.setInt(3, tipoPuerto.getVelocidad());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar tipo de puerto: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(TipoPuerto tipoPuerto) {
        String sql = "UPDATE poo2024.TipoPuerto_ivoma SET descripcion = ?, velocidad = ? WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoPuerto.getDescripcion());
            pst.setInt(2, tipoPuerto.getVelocidad());
            pst.setString(3, tipoPuerto.getCodigo());
            int filasActualizadas = pst.executeUpdate();
            if (filasActualizadas == 0) {
                System.out.println("Tipo de puerto no encontrada: " + tipoPuerto.getCodigo());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar tipo de puerto: " + e.getMessage());
        }
    }

    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        String sql = "DELETE FROM poo2024.TipoPuerto_ivoma WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoPuerto.getCodigo());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al borrar tipo de puerto: " + e.getMessage());
        }
    }

    @Override
    public TreeMap<String, TipoPuerto> buscarTodos() {
        TreeMap<String, TipoPuerto> map = new TreeMap<>();
        String sql = "SELECT * FROM poo2024.TipoPuerto_ivoma";

        try (Statement stmt = conexion.createStatement();
                ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                String codigo = res.getString("codigo");
                String descripcion = res.getString("descripcion");
                int velocidad = res.getInt("velocidad");
                map.put(codigo, new TipoPuerto(codigo, descripcion, velocidad));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar todos los tipos de puerto: " + e.getMessage());
        }
        return map;
    }

}
