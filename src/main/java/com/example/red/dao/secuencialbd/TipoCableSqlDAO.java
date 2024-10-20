package com.example.red.dao.secuencialbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.TipoCable;

public class TipoCableSqlDAO implements GenericDAO<String, TipoCable> {
    private Connection conexion;

    public TipoCableSqlDAO() {
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public void insertar(TipoCable tipoCable) {
        String sql = "INSERT INTO poo2024.TipoCable_ivoma (codigo, descripcion, velocidad) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoCable.getCodigo());
            pst.setString(2, tipoCable.getDescripcion());
            pst.setInt(3, tipoCable.getVelocidad());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar tipo de cable: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(TipoCable tipoCable) {
        String sql = "UPDATE poo2024.TipoCable_ivoma SET descripcion = ?, velocidad = ? WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoCable.getDescripcion());
            pst.setInt(2, tipoCable.getVelocidad());
            pst.setString(3, tipoCable.getCodigo());
            int filasActualizadas = pst.executeUpdate();
            if (filasActualizadas == 0) {
                System.out.println("Tipo de cable no encontrada: " + tipoCable.getCodigo());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar tipo de cable: " + e.getMessage());
        }
    }

    @Override
    public void borrar(TipoCable tipoCable) {
        String sql = "DELETE FROM poo2024.TipoCable_ivoma WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, tipoCable.getCodigo());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al borrar tipo de cable: " + e.getMessage());
        }
    }

    public TreeMap<String, TipoCable> buscarTodos() {
        TreeMap<String, TipoCable> map = new TreeMap<>();
        String sql = "SELECT * FROM poo2024.TipoCable_ivoma";

        try (Statement stmt = conexion.createStatement();
                ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                String codigo = res.getString("codigo");
                String descripcion = res.getString("descripcion");
                int velocidad = res.getInt("velocidad");
                map.put(codigo, new TipoCable(codigo, descripcion, velocidad));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar todos los tipos de cable: " + e.getMessage());
        }
        return map;
    }
}
