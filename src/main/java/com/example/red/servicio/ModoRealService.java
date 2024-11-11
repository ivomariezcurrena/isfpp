package com.example.red.servicio;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.red.controlador.Constantes;
import com.example.red.modelo.Equipo;

/**
 * Ofrece funcionalidades extendidas para operar en modo real
 */
public class ModoRealService {

    /** Objeto para obtener la entrada del archivo de configuraciones */
    private static InputStream input;

    /** Objeto para leer las propiedades del archivo de configuraciones */
    private static Properties prop;

    /** Cadena que indica el modo actual de la aplicación */
    private static String modo;

    /**
     * Obtiene la propiedad modo del archivo de configuraciones
     * 
     * @return modo
     */
    public static String getModo() {
        if (modo == null) {
            try {
                input = new FileInputStream("config.properties");
                prop = new Properties();
                prop.load(input);
                modo = prop.getProperty("modo").toLowerCase();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return modo;
    }

    /**
     * Dada una lista de equipos, verifica el estado real de los equipos y los
     * actualiza en la misma lista (por referencia)
     * 
     * Utiliza PingWorkers para generar un hilo por cada consulta y obtener
     * resultados en menor tiempo
     * 
     * @param equipos lista de equipos
     * @param todos true para cambiar todos, false para cambiar solo los indicados en los ids
     * @param ids lista de ids de equipos a modificar
     */
    public static void cargarEstadosReales(List<Equipo> equipos, boolean todos, List<String> ids) {
        // executor
        ExecutorService executor = Executors.newCachedThreadPool();

        int i = 1;
        if (todos){
            for (Equipo equipo : equipos) {
                // Se usa un hilo para cada proceso de ping
                executor.execute(new PingWorker("PingWorker " + i, equipo));
                i++;
            }
        } else if (!todos && ids != null) {
            for (String id : ids){
                for (Equipo equipo : equipos) {
                    if (equipo.getCodigo().equals(id)) {
                        executor.execute(new PingWorker("PingWorker " + i, equipo));
                        break;
                    } 
                }
            }
        } else {
            
        }

        // Esperar unos segundos hasta que terminen todos los hilos de executor
        try {
            executor.shutdown();
            executor.awaitTermination(Constantes.PINGWORKER_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Realiza un ping usando la terminal del Sistema Operativo para obtener el
     * estado real de un equipo
     * 
     * @param host la dirección IP
     * @return actividad
     */
    public static boolean pingReal(String host) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Construir el comando de ping
        // Linux: "-c"
        // Windows: "-n"
        processBuilder.command("ping", isWindows ? "-n" : "-c", "4", host);

        int exitCode = -1;
        try {
            Process process = processBuilder.start();
            exitCode = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode == 0;
    }
}
