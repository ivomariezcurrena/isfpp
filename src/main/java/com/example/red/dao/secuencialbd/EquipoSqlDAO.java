package com.example.red.dao.secuencialbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import com.example.red.conexion.ConexionBD;
import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;

/**
 * DAO para los equipos en base de datos
 */
public class EquipoSqlDAO implements GenericDAO<String, Equipo> {
    /** Conexion con la base de datos */
    private Connection conexion;

    /** Mapa de tipos de puertos */
    private TreeMap<String, TipoPuerto> puertos;

    /** Mapa de tipos de equipos */
    private TreeMap<String, TipoEquipo> tipoEquipos;

    /** Mapa de ubicaciones */
    private TreeMap<String, Ubicacion> ubicaciones;

    /** Constructor */
    public EquipoSqlDAO() {
        puertos = cargarPuertos();
        tipoEquipos = cargarEquipos();
        ubicaciones = cargarUbicaciones();
        this.conexion = ConexionBD.getInstance().getConnection();
    }

    public void insertar(Equipo equipo) {
        String sqlEquipo = "INSERT INTO poo2024.Equipo_ivoma (codigo, descripcion, marca, modelo, tipoequipo_codigo, ubicacion_codigo, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPuerto = "INSERT INTO poo2024.Puerto_ivoma (equipo_codigo, tipopuerto_id, cantidad) VALUES (?, ?, ?)";
        String sqlIP = "INSERT INTO poo2024.DireccionIP_ivoma (equipo_codigo, direccion_ip) VALUES (?, ?)";

        try {
            // Inicia una transacción
            conexion.setAutoCommit(false);

            // Inserta en la tabla Equipo
            try (PreparedStatement pstEquipo = conexion.prepareStatement(sqlEquipo)) {
                pstEquipo.setString(1, equipo.getCodigo());
                pstEquipo.setString(2, equipo.getDescripcion());
                pstEquipo.setString(3, equipo.getMarca());
                pstEquipo.setString(4, equipo.getModelo());
                pstEquipo.setString(5, equipo.getTipoEquipo().getCodigo());
                pstEquipo.setString(6, equipo.getUbicacion().getCodigo());
                pstEquipo.setBoolean(7, equipo.isActivo());
                pstEquipo.executeUpdate();
            }

            // Inserta en la tabla Puerto
            try (PreparedStatement pstPuerto = conexion.prepareStatement(sqlPuerto)) {
                for (Object[] puertoInfo : equipo.getPuertosInfo()) {
                    TipoPuerto tipoPuertoId = (TipoPuerto) puertoInfo[0];
                    int cantidad = (int) puertoInfo[1];
                    pstPuerto.setString(1, equipo.getCodigo());
                    pstPuerto.setString(2, tipoPuertoId.getCodigo());
                    pstPuerto.setInt(3, cantidad);
                    pstPuerto.addBatch();
                }
                pstPuerto.executeBatch(); // Ejecuta el batch insert para puertos
            }

            // Inserta en la tabla Direccion IP
            try (PreparedStatement pstIP = conexion.prepareStatement(sqlIP)) {
                for (String ip : equipo.getDireccionesIP()) {
                    pstIP.setString(1, equipo.getCodigo());
                    pstIP.setString(2, ip);
                    pstIP.addBatch();
                }
                pstIP.executeBatch(); // Ejecuta el batch insert para IPs
            }

            // Commit de la transacción
            conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback en caso de error
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
                throw new RuntimeException("Error al realizar rollback de la transacción", rollbackEx);
            }
            throw new RuntimeException("Error al insertar equipo y sus datos asociados", e);
        } finally {
            try {
                // Restaurar el auto-commit
                conexion.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actualizar(Equipo equipo) {
        String sqlUpdateEquipo = "UPDATE poo2024.Equipo_ivoma SET descripcion = ?, marca = ?, modelo = ?, tipoequipo_codigo = ?, ubicacion_codigo = ?, activo = ? WHERE codigo = ?";

        try (PreparedStatement pstEquipo = conexion.prepareStatement(sqlUpdateEquipo)) {
            // Actualiza la tabla equipo
            pstEquipo.setString(1, equipo.getDescripcion());
            pstEquipo.setString(2, equipo.getMarca());
            pstEquipo.setString(3, equipo.getModelo());
            pstEquipo.setString(4, equipo.getTipoEquipo().getCodigo());
            pstEquipo.setString(5, equipo.getUbicacion().getCodigo());
            pstEquipo.setBoolean(6, equipo.isActivo());
            pstEquipo.setString(7, equipo.getCodigo());
            pstEquipo.executeUpdate();

            // Actualiza los puertos
            String sqlUpdatePuerto = "UPDATE poo2024.Puerto_ivoma SET cantidad = ? WHERE equipo_codigo = ? AND tipopuerto_id = ?";
            String sqlSelectPuerto = "SELECT cantidad FROM poo2024.Puerto_ivoma WHERE equipo_codigo = ? AND tipopuerto_id = ?";

            for (Object[] puertoInfo : equipo.getPuertosInfo()) {
                TipoPuerto tipoPuerto = (TipoPuerto) puertoInfo[0];
                int cantidad = (int) puertoInfo[1]; // Usamos la cantidad del equipo

                // Verificar si el puerto ya existe en la base de datos
                try (PreparedStatement pstSelect = conexion.prepareStatement(sqlSelectPuerto)) {
                    pstSelect.setString(1, equipo.getCodigo());
                    pstSelect.setString(2, tipoPuerto.getCodigo());
                    ResultSet rs = pstSelect.executeQuery();

                    if (rs.next()) {
                        // Actualizar si ya existe
                        try (PreparedStatement pstPuerto = conexion.prepareStatement(sqlUpdatePuerto)) {
                            pstPuerto.setInt(1, cantidad); // Usamos el valor pasado en el equipo
                            pstPuerto.setString(2, equipo.getCodigo());
                            pstPuerto.setString(3, tipoPuerto.getCodigo());
                            pstPuerto.executeUpdate();
                        }
                    } else {
                        // Insertar si no existe
                        String sqlInsertPuerto = "INSERT INTO poo2024.Puerto_ivoma (equipo_codigo, tipopuerto_id, cantidad) VALUES (?, ?, ?)";
                        try (PreparedStatement pstInsertPuerto = conexion.prepareStatement(sqlInsertPuerto)) {
                            pstInsertPuerto.setString(1, equipo.getCodigo());
                            pstInsertPuerto.setString(2, tipoPuerto.getCodigo());
                            pstInsertPuerto.setInt(3, cantidad);
                            pstInsertPuerto.executeUpdate();
                        }
                    }
                }
            }
            // Actualiza las direcciones IP
            String sqlUpdateIP = "UPDATE poo2024.DireccionIP_ivoma SET direccion_ip = ? WHERE equipo_codigo = ?";
            try (PreparedStatement pstmIP = conexion.prepareStatement(sqlUpdateIP)) {
                for (String ip : equipo.getDireccionesIP()) {
                    pstmIP.setString(1, ip);
                    pstmIP.setString(2, equipo.getCodigo());
                    pstmIP.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el equipo y sus datos", e);
        }
    }

    @Override
    public void borrar(Equipo equipo) {
        String sqlDeleteEquipo = "DELETE FROM poo2024.Equipo_ivoma WHERE codigo = ?";
        String sqlDeletePuertos = "DELETE FROM poo2024.Puerto_ivoma WHERE equipo_codigo = ?";
        String sqlDeleteIPs = "DELETE FROM poo2024.DireccionIP_ivoma WHERE equipo_codigo = ?";

        try {
            // Borra los puertos asociados al equipo
            try (PreparedStatement pstPuerto = conexion.prepareStatement(sqlDeletePuertos)) {
                pstPuerto.setString(1, equipo.getCodigo());
                pstPuerto.executeUpdate();
            }

            // Borra las direcciones IP asociadas al equipo
            try (PreparedStatement pstIP = conexion.prepareStatement(sqlDeleteIPs)) {
                pstIP.setString(1, equipo.getCodigo());
                pstIP.executeUpdate();
            }

            // Finalmente borra el equipo
            try (PreparedStatement pstEquipo = conexion.prepareStatement(sqlDeleteEquipo)) {
                pstEquipo.setString(1, equipo.getCodigo());
                pstEquipo.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al borrar el equipo y sus datos", e);
        }
    }

    @Override
    public TreeMap<String, Equipo> buscarTodos() {
        TreeMap<String, Equipo> map = new TreeMap<>();

        String sqlSelectEquipo = "SELECT codigo, descripcion, marca, modelo, tipoequipo_codigo, ubicacion_codigo, activo FROM poo2024.Equipo_ivoma";

        String sqlSelectPuerto = "SELECT tipopuerto_id, cantidad FROM poo2024.Puerto_ivoma WHERE equipo_codigo = ?";

        String sqlSelectIP = "SELECT direccion_ip FROM poo2024.DireccionIP_ivoma WHERE equipo_codigo = ?";

        try (PreparedStatement pstEquipo = conexion.prepareStatement(sqlSelectEquipo)) {
            ResultSet rsEquipo = pstEquipo.executeQuery();

            while (rsEquipo.next()) {
                // Recupera los datos del equipo
                String codigo = rsEquipo.getString("codigo");
                String descripcion = rsEquipo.getString("descripcion");
                String marca = rsEquipo.getString("marca");
                String modelo = rsEquipo.getString("modelo");
                TipoEquipo tipoEquipo = tipoEquipos.get(rsEquipo.getString("tipoequipo_codigo"));
                Ubicacion ubicacion = ubicaciones.get(rsEquipo.getString("ubicacion_codigo"));
                boolean activo = rsEquipo.getBoolean("activo");

                // Crea el objeto Equipo
                Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, activo);

                // Recupera los puertos del equipo
                try (PreparedStatement pstPuerto = conexion.prepareStatement(sqlSelectPuerto)) {
                    pstPuerto.setString(1, codigo);
                    ResultSet rsPuerto = pstPuerto.executeQuery();
                    while (rsPuerto.next()) {
                        TipoPuerto tipoPuerto = puertos.get(rsPuerto.getString("tipopuerto_id"));
                        int cantidad = rsPuerto.getInt("cantidad");
                        equipo.agregarPuerto(tipoPuerto, cantidad);
                    }
                }

                // Recupera las direcciones IP del equipo
                try (PreparedStatement pstIP = conexion.prepareStatement(sqlSelectIP)) {
                    pstIP.setString(1, codigo);
                    ResultSet rsIP = pstIP.executeQuery();
                    while (rsIP.next()) {
                        equipo.agregarDireccionIP(rsIP.getString("direccion_ip"));
                    }
                }

                map.put(codigo, equipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar todos los equipos", e);
        }
        return map;
    }

    /**
     * Carga los tipos de puertos
     * 
     * @return mapa de tipos de puertos
     */
    private TreeMap<String, TipoPuerto> cargarPuertos() {
        TipoPuertoSqlDAO tipoPuertosqlDAO = new TipoPuertoSqlDAO();
        TreeMap<String, TipoPuerto> ds = tipoPuertosqlDAO.buscarTodos();
        return ds;
    }

    /**
     * Carga los tipos de equipos
     * 
     * @return mapa de equipos
     */
    private TreeMap<String, TipoEquipo> cargarEquipos() {
        TipoEquipoSqlDAO tipoEquipoSqlDAO = new TipoEquipoSqlDAO();
        TreeMap<String, TipoEquipo> ds = tipoEquipoSqlDAO.buscarTodos();
        return ds;
    }

    /**
     * Carga las ubicaciones
     * 
     * @return mapa de ubicaciones
     */
    private TreeMap<String, Ubicacion> cargarUbicaciones() {
        UbicacionSqlDAO ubicacionesSqlDAO = new UbicacionSqlDAO();
        TreeMap<String, Ubicacion> ds = ubicacionesSqlDAO.buscarTodos();
        return ds;
    }
}
