package com.example.red.servicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Servicio que obtiene los textos adecuados para la interfaz, dependiendo del
 * idioma y región indicados en el archivo de configuraciones
 * 
 */
public class IdiomaService {

    /** Objeto para obtener la entrada del archivo de configuraciones */
    private static InputStream input;

    /** Objeto para leer las propiedades del archivo de configuraciones */
    private static Properties prop;

    /** Objeto que contiene los textos */
    private static ResourceBundle rb;

    /** Constructor privado para evitar multiples instancias */
    private IdiomaService() {

    }

    /**
     * Carga los textos correctos según el idioma y región indicados, los retorna en
     * un ResourceBundle
     * 
     * @return referencia al ResourceBundle
     */
    public static ResourceBundle getRb() {
        if (rb == null) {
            try {
                input = new FileInputStream("config.properties");
                prop = new Properties();
                prop.load(input);
                String idioma = prop.getProperty("language");
                String pais = prop.getProperty("country");
                Locale.setDefault(new Locale(idioma, pais));

                rb = ResourceBundle.getBundle("labels");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rb;
    }

}
