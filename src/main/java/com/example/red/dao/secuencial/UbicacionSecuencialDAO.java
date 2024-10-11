package com.example.red.dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import com.example.red.dao.GenericDAO;

import com.example.red.modelo.Ubicacion;

public class UbicacionSecuencialDAO implements GenericDAO<String, Ubicacion> {
    private TreeMap<String, Ubicacion> map;
    private String name;
    private boolean actualizar;

    public UbicacionSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("ubicacion");
        actualizar = true;
    }

    private TreeMap<String, Ubicacion> readFromFile(String file) {
        TreeMap<String, Ubicacion> map = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                map.put(codigo, new Ubicacion(codigo, descripcion));
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
        return map;
    }

    private void writeToFile(TreeMap<String, Ubicacion> map, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (Ubicacion e : map.values()) {
                outFile.format("%s;%s;\n", e.getCodigo(), e.getDescripcion());
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
    public void insertar(Ubicacion ubicacion) {
        map.put(ubicacion.getCodigo(), ubicacion);
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public void actualizar(Ubicacion ubicacion) {
        if (map.containsKey(ubicacion.getCodigo())) {
            map.put(ubicacion.getCodigo(), ubicacion);
            writeToFile(map, name);
            actualizar = true;
        } else {
            System.out.println("Ubicación no encontrada: " + ubicacion.getCodigo());
        }
    }

    @Override
    public void borrar(Ubicacion ubicacion) {
        map.remove(ubicacion.getCodigo());
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, Ubicacion> buscarTodos() {
        if (actualizar) {
            map = readFromFile(name);
            actualizar = false;
        }
        return map;
    }

}