package com.example.red.dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;

public class EquipoSecuencialDAO implements GenericDAO<String, Equipo> {
    private List<Equipo> list;
    private String name;
    private TreeMap<String, TipoPuerto> puertos;
    private TreeMap<String, TipoEquipo> equipos;
    private TreeMap<String, Ubicacion> ubicaciones;
    private boolean actualizar;

    public EquipoSecuencialDAO() {
        puertos = cargarPuertos();
        equipos = cargarEquipos();
        ubicaciones = cargarUbicaciones();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("equipo");
        actualizar = true;
    }

    private List<Equipo> readFromFile(String file) {
        List<Equipo> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                String marca = inFile.next();
                String modelo = inFile.next();
                TipoEquipo tipoEquipo = (equipos.get(inFile.next()));
                Ubicacion ubicacion = (ubicaciones.get(inFile.next()));
                String[] puertoDatos = (inFile.next().split(","));
                String[] direccionesIP = (inFile.next().split(","));
                boolean activo = Boolean.parseBoolean(inFile.next());

                Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, tipoEquipo, ubicacion, activo);

                for (int i = 0; i < puertoDatos.length; i += 2) {
                    TipoPuerto tipoPuerto = puertos.get(puertoDatos[i]);
                    int cantidad = Integer.parseInt(puertoDatos[i + 1]);
                    equipo.agregarPuerto(tipoPuerto, cantidad);
                }

                Collections.addAll(equipo.getDireccionesIP(), direccionesIP);

                list.add(equipo);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file.");
            fileNotFoundException.printStackTrace();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("Error in file record structure");
            noSuchElementException.printStackTrace();
        } catch (IllegalStateException illegalStateException) {
            System.err.println("Error reading from file.");
            illegalStateException.printStackTrace();
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    private void writeToFile(List<Equipo> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (Equipo e : list) {
                outFile.format("%s;%s;\n", e.getCodigo(), e.getDescripcion(), e.getMarca(), e.getModelo(),
                        e.getTipoEquipo(), e.getUbicacion(), e.getPuertos(), e.getDireccionesIP(), e.isActivo());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error creating file.");
        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file.");
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    @Override
    public void insertar(Equipo equipo) {
        list.add(equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void actualizar(Equipo equipo) {
        int pos = list.indexOf(equipo);
        list.set(pos, equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void borrar(Equipo equipo) {
        list.remove(equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, Equipo> buscarTodos() {
        if (actualizar) {
            list = readFromFile(name);
            actualizar = false;
        }
        TreeMap<String, Equipo> map = new TreeMap<>();
        for (Equipo equipo : list) {
            map.put(equipo.getCodigo(), equipo);
        }
        return map;
    }

    private TreeMap<String, TipoPuerto> cargarPuertos() {
        TipoPuertoSecuencialDAO tipoPuertoDAO = new TipoPuertoSecuencialDAO();
        TreeMap<String, TipoPuerto> ds = tipoPuertoDAO.buscarTodos();
        return ds;
    }

    private TreeMap<String, TipoEquipo> cargarEquipos() {
        TipoEquipoSecuencialDAO tipoEquipoDAO = new TipoEquipoSecuencialDAO();
        TreeMap<String, TipoEquipo> ds = tipoEquipoDAO.buscarTodos();
        return ds;
    }

    private TreeMap<String, Ubicacion> cargarUbicaciones() {
        UbicacionSecuencialDAO ubicacionesDAO = new UbicacionSecuencialDAO();
        TreeMap<String, Ubicacion> ds = ubicacionesDAO.buscarTodos();
        return ds;
    }
}
