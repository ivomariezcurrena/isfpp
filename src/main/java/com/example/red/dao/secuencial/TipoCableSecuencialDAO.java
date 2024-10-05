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
import com.example.red.modelo.TipoCable;

public class TipoCableSecuencialDAO implements GenericDAO<TipoCable, String> {
    private TreeMap<String, TipoCable> map;
    private String name;
    private boolean actualizar;

    public TipoCableSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("tipoCable");
        actualizar = true;
    }

    private TreeMap<String, TipoCable> readFromFile(String file) {
        TreeMap<String, TipoCable> map = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                int velocidad = inFile.nextInt();
                map.put(codigo, new TipoCable(codigo, descripcion, velocidad));
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

    private void writeToFile(TreeMap<String, TipoCable> map, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (TipoCable e : map.values()) {
                outFile.format("%s;%s;\n", e.getCodigo(), e.getDescripcion(), e.getVelocidad());
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
    public void insertar(TipoCable tipoCable) {
        map.put(tipoCable.getCodigo(), tipoCable);
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public void actualizar(TipoCable tipoCable) {
        if (map.containsKey(tipoCable.getCodigo())) {
            map.put(tipoCable.getCodigo(), tipoCable);
            writeToFile(map, name);
            actualizar = true;
        } else {
            System.out.println("Ubicación no encontrada: " + tipoCable.getCodigo());
        }
    }

    @Override
    public void borrar(TipoCable tipoCable) {
        map.remove(tipoCable.getCodigo());
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, TipoCable> buscarTodos() {
        if (actualizar) {
            map = readFromFile(name);
            actualizar = false;
        }
        return map;
    }

}
