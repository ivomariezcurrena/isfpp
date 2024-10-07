package com.example.red.dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import com.example.red.dao.GenericDAO;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoPuerto;

public class ConexionSecuancialDAO implements GenericDAO<String, Conexion> {
    private List<Conexion> list;
    private String name;
    private TreeMap<String, Equipo> equipos;
    private TreeMap<String, TipoCable> cables;
    private TreeMap<String, TipoPuerto> puertos;
    private boolean actualizar;

    public ConexionSecuancialDAO() {
        equipos = cargarEquipos();
        cables = cargarCables();
        puertos= cargarPuertos();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("conexion");
        actualizar = true;
    }


    private List<Conexion> readFromFile(String file) {
        List<Conexion> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                Conexion e = new Conexion();
                e.setEquipo1(equipos.get(inFile.next()));
                e.setTipoPuerto1(puertos.get(inFile.next()));
                e.setEquipo2(equipos.get(inFile.next()));
                e.setTipoPuerto2(puertos.get(inFile.next()));
                e.setTipocable(cables.get(inFile.next()));    
                list.add(e);
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

    private void writeToFile(List<Conexion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (Conexion e : list) {
                outFile.format("%s;%s;\n", e.getEquipo1(), e.getEquipo2(), e.getTipocable(), e.getTipoPuerto1(),e.getTipoPuerto2());
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
    public void insertar(Conexion equipo) {
        list.add(equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void actualizar(Conexion equipo) {
        int pos = list.indexOf(equipo);
        list.set(pos, equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public void borrar(Conexion equipo) {
        list.remove(equipo);
        writeToFile(list, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, Conexion> buscarTodos() {
        if (actualizar) {
            list = readFromFile(name);
            actualizar = false;
        }
        TreeMap<String, Conexion> map = new TreeMap<>();
        for (Conexion e : list) {
            // Crear una clave
            String key = e.getEquipo1().getCodigo() + "-" + e.getEquipo2().getCodigo();
            map.put(key, e);
        }
        return map;
    }

    private TreeMap<String, Equipo> cargarEquipos() {
        EquipoSecuencialDAO equipoDAO = new EquipoSecuencialDAO();
        TreeMap<String, Equipo> ds = equipoDAO.buscarTodos();
        return ds;
    }

    private TreeMap<String, TipoCable> cargarCables() {
        TipoCableSecuencialDAO cableDAO = new TipoCableSecuencialDAO();
        TreeMap<String, TipoCable> ds = cableDAO.buscarTodos();
        return ds;
    }

    
    private TreeMap<String, TipoPuerto> cargarPuertos() {
        TipoPuertoSecuencialDAO puertoDAO = new TipoPuertoSecuencialDAO();
        TreeMap<String, TipoPuerto> ds = puertoDAO.buscarTodos();
        return ds;
    }

}
