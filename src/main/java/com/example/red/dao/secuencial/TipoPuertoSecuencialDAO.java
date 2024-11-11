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
import com.example.red.modelo.TipoPuerto;

/**
 * DAO para los tipos de puertos en archivos de texto
 */
public class TipoPuertoSecuencialDAO implements GenericDAO<String, TipoPuerto> {
    
    /** Mapa de tipos de puertos */
    private TreeMap<String, TipoPuerto> map;

    /** Nombre del archivo de texto */
    private String name;

    /** Bandera de actualización */
    private boolean actualizar;

    /** Constructor */
    public TipoPuertoSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        name = rb.getString("tipoPuerto");
        actualizar = true;
    }

    /**
     * Lee los tipos de puertos desde el archivo de texto
     * 
     * @param file nombre del archivo
     * @return mapa de tipos de puertos
     */
    private TreeMap<String, TipoPuerto> readFromFile(String file) {
        TreeMap<String, TipoPuerto> map = new TreeMap<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                String codigo = inFile.next();
                String descripcion = inFile.next();
                int velocidad = inFile.nextInt();
                map.put(codigo, new TipoPuerto(codigo, descripcion, velocidad));
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

    /**
     * Escribe los tipos de puertos en el archivo de texto
     * 
     * @param file nombre del archivo
     * @param map  mapa de tipos de puertos
     */
    private void writeToFile(TreeMap<String, TipoPuerto> map, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(name);
            for (TipoPuerto e : map.values()) {
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
    public void insertar(TipoPuerto tipoPuerto) {
        map.put(tipoPuerto.getCodigo(), tipoPuerto);
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public void actualizar(TipoPuerto tipoPuerto) {
        if (map.containsKey(tipoPuerto.getCodigo())) {
            map.put(tipoPuerto.getCodigo(), tipoPuerto);
            writeToFile(map, name);
            actualizar = true;
        } else {
            System.out.println("Ubicación no encontrada: " + tipoPuerto.getCodigo());
        }
    }

    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        map.remove(tipoPuerto.getCodigo());
        writeToFile(map, name);
        actualizar = true;
    }

    @Override
    public TreeMap<String, TipoPuerto> buscarTodos() {
        if (actualizar) {
            map = readFromFile(name);
            actualizar = false;
        }
        return map;
    }

}