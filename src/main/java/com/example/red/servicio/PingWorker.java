package com.example.red.servicio;

import java.util.List;

import com.example.red.modelo.Equipo;

/**
 * Se encarga de realizar un ping a una dirección IP u Host y esperar el
 * resultado
 * La dirección IP que se usa, se obtiene del equipo brindado al crear una
 * instancia
 * Es un hilo, implementa la interfaz Runnable por ello se recomienda usarlo en
 * un ExecutorService
 */
public class PingWorker implements Runnable {
    private String nombre;
    private Equipo equipo;

    /**
     * Crea un PingWorker con un nombre personalizado y el equipo cuyo estado se
     * verificará
     * 
     * @param nombre 
     * @param equipo
     */
    public PingWorker(String nombre, Equipo equipo) {
        this.nombre = nombre;
        this.equipo = equipo;
    }

    @Override
    public void run() {
        // Si el equipo no tiene ip, no tenemos forma de verificar su actividad
        // Esto es un problema en caso de los switches
        // Por eso por defecto los tomaremos como activos
        equipo.setActivo(true);

        // Si tiene ip, verificar estado real
        List<String> ips = equipo.getDireccionesIP();
        if (!ips.isEmpty() && ips != null)
            for (String ip : ips) {
                if (ip != null) {
                    System.out.println("haciendo Ping al equipo " + equipo.getCodigo() + " con ip: " + ip);

                    // poner en espera el hilo hasta conseguir el resultado
                    boolean activo = ModoRealService.pingReal(ip);
                    // Escribe su resultado
                    equipo.setActivo(activo);

                    System.out.println("Resultado: equipo " + equipo.getCodigo() + " " + activo);
                    if (activo)
                        break;
                }
            }
    }

    @Override
    public String toString() {
        return nombre;
    }

}
