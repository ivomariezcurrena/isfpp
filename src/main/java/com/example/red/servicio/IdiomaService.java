package com.example.red.servicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class IdiomaService {
    private static InputStream input;
    private static Properties prop;
    private static ResourceBundle rb;

    private IdiomaService(){

    }

    public static ResourceBundle getRb(){
        if (rb == null){
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
