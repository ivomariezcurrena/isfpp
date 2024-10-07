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
import com.example.red.modelo.TipoEquipo;

public class TipoEquipoSecuencialDAO implements GenericDAO<String, TipoEquipo> {
    private TreeMap<String, TipoEquipo> map;
    private String name;
    private boolean actualizar;

    public TipoEquipoSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("tipoEquipo");
        actualizar = true;
    }

    private TreeMap<String, TipoEquipo> readFromFile(String file) {
        TreeMap<String, TipoEquipo> map = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                map.put(codigo, new TipoEquipo(codigo, descripcion));
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

    private void writeToFile(TreeMap<String, TipoEquipo> map, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (TipoEquipo e : map.values()) {
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
    public void insertar(TipoEquipo tipoEquipo) {
        map.put(tipoEquipo.getCodigo(), tipoEquipo);
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        if (map.containsKey(tipoEquipo.getCodigo())) {
            map.put(tipoEquipo.getCodigo(), tipoEquipo);
            writeToFile(map, name);
            actualizar = true;
        } else {
            System.out.println("Ubicación no encontrada: " + tipoEquipo.getCodigo());
        }
    }

    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        map.remove(tipoEquipo.getCodigo());
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, TipoEquipo> buscarTodos() {
        if (actualizar) {
            map = readFromFile(name);
            actualizar = false;
        }
        return map;
    }

}
