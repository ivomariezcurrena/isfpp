import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.modelo.TipoCable;
import com.example.red.modelo.TipoEquipo;
import com.example.red.modelo.TipoPuerto;
import com.example.red.modelo.Ubicacion;
import com.example.red.negocio.Calculo;

import java.util.ArrayList;
import java.util.List;

public class CalculoTest {
    private Calculo calculo;
    private List<Equipo> equipos;
    private List<Conexion> conexiones;

    @Before
    public void setUp() {
        // Inicializar la instancia de Calculo
        calculo = new Calculo();

        // Crear algunos equipos de ejemplo
        equipos = new ArrayList<>();
        TipoEquipo tipoEquipo = new TipoEquipo("TE1", "Router");
        Ubicacion ubicacion = new Ubicacion("U1", "Sala de servidores");

        Equipo equipo1 = new Equipo("E1", "Router Principal", "Cisco", "RV340", tipoEquipo, ubicacion, true);
        equipo1.getDireccionesIP().add("192.168.0.1");

        Equipo equipo2 = new Equipo("E2", "Router Secundario", "Cisco", "RV340", tipoEquipo, ubicacion, true);
        equipo2.getDireccionesIP().add("192.168.0.2");

        // Agregar equipos a la lista
        equipos.add(equipo1);
        equipos.add(equipo2);

        // Crear algunas conexiones
        conexiones = new ArrayList<>();
        TipoCable tipoCable = new TipoCable("C1", "Cable Ethernet", 100); // 100 Mbps
        TipoPuerto tipoPuerto1 = new TipoPuerto("P1", "Puerto Gigabit", 1000); // 1000 Mbps
        TipoPuerto tipoPuerto2 = new TipoPuerto("P2", "Puerto Gigabit", 1000); // 1000 Mbps
        equipo1.agregarPuerto(tipoPuerto2, 3);
        equipo2.agregarPuerto(tipoPuerto2, 3);

        // Conectar los equipos
        Conexion conexion = new Conexion(equipo1, equipo2, tipoCable, tipoPuerto1, tipoPuerto2);
        conexiones.add(conexion);
    }

    @Test
    public void testCargarDatos() {
        calculo.cargarDatos(equipos, conexiones);

        // Verificar que los equipos se han cargado correctamente
        assertNotNull(calculo.validarEquipo("E1"));
        assertNotNull(calculo.validarEquipo("E2"));

        // Verificar que las conexiones se han establecido
        String resultado = calculo.traceRoute("E1", "E2");
        assertTrue(resultado.contains("Recorrido de E1 a E2"));
    }

    @Test
    public void testValidarEquipoPorId() {
        calculo.cargarDatos(equipos, conexiones);

        Equipo equipo = calculo.validarEquipo("E1");
        assertNotNull(equipo);
        assertEquals("E1", equipo.getCodigo());
    }

    @Test
    public void testValidarEquipoPorIP() {
        calculo.cargarDatos(equipos, conexiones);

        Equipo equipo = calculo.validarEquipo("192.168.0.1");
        assertNotNull(equipo);
        assertEquals("E1", equipo.getCodigo());
    }

    @Test
    public void testPingEquipoActivo() {
        calculo.cargarDatos(equipos, conexiones);

        String resultado = calculo.ping("E1");
        assertTrue(resultado.contains("activo"));
    }

    @Test
    public void testPingEquipoInactivo() {
        // Crear un equipo inactivo
        Ubicacion ubicacion = new Ubicacion("U2", "Oficina");
        Equipo equipoInactivo = new Equipo("E3", "Router Inactivo", "Cisco", "RV340", new TipoEquipo("TE1", "Router"),
                ubicacion, false);
        equipos.add(equipoInactivo);

        calculo.cargarDatos(equipos, conexiones);

        String resultado = calculo.ping("E3");
        assertTrue(resultado.contains("inactivo"));
    }

    @Test
    public void testTraceRouteNoExistente() {
        calculo.cargarDatos(equipos, conexiones);

        String resultado = calculo.traceRoute("E1", "E3"); // E3 no existe
        assertTrue(resultado.contains("Al menos un equipo no se ha encontrado en la red"));
    }

    @Test
    public void testTraceRouteSinCamino() {
        // Crear un tercer equipo sin conexión
        Ubicacion ubicacion = new Ubicacion("U3", "Sala de conferencias");
        Equipo equipo3 = new Equipo("E3", "Router Aislado", "Cisco", "RV340", new TipoEquipo("TE1", "Router"),
                ubicacion, true);
        equipos.add(equipo3);

        calculo.cargarDatos(equipos, conexiones);

        String resultado = calculo.traceRoute("E1", "E3"); // E3 no tiene conexión con E1
        assertTrue(resultado.contains("No se ha encontrado un camino"));
    }
}