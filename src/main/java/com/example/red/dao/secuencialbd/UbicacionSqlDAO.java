package com.example.red.dao.secuencialbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Ubicacion;
/**
 * DAO para las ubicaciones en base de datos
 */
public class UbicacionSqlDAO implements GenericDAO<String, Ubicacion> {

    /** Conexion con la base de datos */
    private Connection conexion;

    /** Constructor */
    public UbicacionSqlDAO() {
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    @Override
    public void insertar(Ubicacion ubicacion) {
        String sql = "INSERT INTO poo2024.Ubicacion_ivoma (codigo, descripcion) VALUES (?, ?)";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, ubicacion.getCodigo());
            pst.setString(2, ubicacion.getDescripcion());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar ubicación: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Ubicacion ubicacion) {
        String sql = "UPDATE poo2024.Ubicacion_ivoma SET descripcion = ? WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, ubicacion.getDescripcion());
            pst.setString(2, ubicacion.getCodigo());
            int filasActualizadas = pst.executeUpdate();
            if (filasActualizadas == 0) {
                System.out.println("Ubicación no encontrada: " + ubicacion.getCodigo());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar ubicación: " + e.getMessage());
        }
    }

    @Override
    public void borrar(Ubicacion ubicacion) {
        String sql = "DELETE FROM poo2024.Ubicacion_ivoma WHERE codigo = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setString(1, ubicacion.getCodigo());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al borrar ubicación: " + e.getMessage());
        }
    }

    @Override
    public TreeMap<String, Ubicacion> buscarTodos() {
        TreeMap<String, Ubicacion> map = new TreeMap<>();
        String sql = "SELECT * FROM poo2024.Ubicacion_ivoma";

        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                map.put(codigo, new Ubicacion(codigo, descripcion));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar todas las ubicaciones: " + e.getMessage());
        }

        return map;
    }
}
