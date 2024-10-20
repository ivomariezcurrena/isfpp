package com.example.red.dao.secuencialbd;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoPuerto;

public class ConexionSqlDAO implements GenericDAO<String, Conexion> {
    private Connection con;
    private TreeMap<String, Equipo> equipos;
    private TreeMap<String, TipoCable> cables;
    private TreeMap<String, TipoPuerto> puertos;

    public ConexionSqlDAO() {
        this.con = ConexionBD.getInstance().getConnection();
        equipos = cargarEquipos();
        cables = cargarCables();
        puertos = cargarPuertos();
    }

    @Override
    public void insertar(Conexion conexion) {
        String sql = "INSERT INTO poo2024.conexion_ivoma (Equipo1, Equipo2,TipoCable tipoPuerto1, tipoPuerto2) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, conexion.getEquipo1().getCodigo());
            pst.setString(2, conexion.getEquipo2().getCodigo());
            pst.setString(3, conexion.getTipocable().getCodigo());
            pst.setString(4, conexion.getTipoPuerto1().getCodigo());
            pst.setString(5, conexion.getTipoPuerto2().getCodigo());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar conexion: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Conexion conexion) {
        String sql = "UPDATE poo2024.conexion_ivoma SET Equipo2 = ?, tipoPuerto2 = ?, TipoCable = ? WHERE Equipo1 = ? AND tipoPuerto1= ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, conexion.getEquipo2().getCodigo());
            pst.setString(2, conexion.getTipoPuerto2().getCodigo());
            pst.setString(3, conexion.getTipocable().getCodigo());
            pst.setString(4, conexion.getEquipo1().getCodigo());
            pst.setString(5, conexion.getTipoPuerto1().getCodigo());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar conexion: " + e.getMessage());
        }
    }

    @Override
    public void borrar(Conexion conexion) {
        String sql = "DELETE FROM poo2024.conexion_ivoma WHERE Equipo1 = ? AND tipoPuerto1 = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, conexion.getEquipo1().getCodigo());
            pst.setString(2, conexion.getTipoPuerto1().getCodigo());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al borrar conexion: " + e.getMessage());
        }
    }

    @Override
    public TreeMap<String, Conexion> buscarTodos() {
        TreeMap<String, Conexion> map = new TreeMap<>();
        String sql = "SELECT * FROM poo2024.conexion_ivoma";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rsConexion = pst.executeQuery();

            while (rsConexion.next()) {
                String key = rsConexion.getString("Equipo1") + "-" + rsConexion.getString("Equipo2");
                Conexion conexion = new Conexion(equipos.get(rsConexion.getString("Equipo1")),
                        equipos.get(rsConexion.getString("Equipo2")),
                        cables.get(rsConexion.getString("TipoCable")),
                        puertos.get(rsConexion.getString("tipoPuerto1")),
                        puertos.get(rsConexion.getString("tipoPuerto2")));

                map.put(key, conexion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar todos los equipos", e);
        }
        return map;
    }

    private TreeMap<String, Equipo> cargarEquipos() {
        EquipoSqlDAO equipoDAO = new EquipoSqlDAO();
        TreeMap<String, Equipo> ds = equipoDAO.buscarTodos();
        return ds;
    }

    private TreeMap<String, TipoCable> cargarCables() {
        TipoCableSqlDAO cableDAO = new TipoCableSqlDAO();
        TreeMap<String, TipoCable> ds = cableDAO.buscarTodos();
        return ds;
    }

    private TreeMap<String, TipoPuerto> cargarPuertos() {
        TipoPuertoSqlDAO puertoDAO = new TipoPuertoSqlDAO();
        TreeMap<String, TipoPuerto> ds = puertoDAO.buscarTodos();
        return ds;
    }

}
