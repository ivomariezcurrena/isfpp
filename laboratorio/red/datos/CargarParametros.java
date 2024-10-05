package red.datos;


import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;


public class CargarParametros {
    private static String archivoComputadoras;
    private static String archivoRouters;
    private static String archivoConexiones;

	public static void parametros() throws IOException {
		
		Properties prop = new Properties();		
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			archivoComputadoras = prop.getProperty("Computadoras");		
			archivoRouters = prop.getProperty("Routers");
            archivoConexiones = prop.getProperty("Conexiones");
	}

    public static String getArchivoComputadoras() {
        return archivoComputadoras;
    }

    public static String getArchivoRouters() {
        return archivoRouters;
    }

    public static String getArchivoConexiones() {
        return archivoConexiones;
    }
}
