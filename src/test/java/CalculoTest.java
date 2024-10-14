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
import java.util.Map;
import java.util.TreeMap;

public class CalculoTest {
    private Calculo calculo;
    private Map<String, Equipo> equipos;
    private List<Conexion> conexiones;

    @Before
    public void setUp() {
        // Inicializar la instancia de Calculo
        calculo = new Calculo();

        // Crear algunos equipos de ejemplo
        equipos = new TreeMap<>();
        TipoEquipo tipoEquipo = new TipoEquipo("TE1", "Router");
        Ubicacion ubicacion = new Ubicacion("U1", "Sala de servidores");

        Equipo equipo1 = new Equipo("E1", "Router Principal", "Cisco", "RV340", tipoEquipo, ubicacion, true);
        equipo1.getDireccionesIP().add("192.168.0.1");

        Equipo equipo2 = new Equipo("E2", "Router Secundario", "Cisco", "RV340", tipoEquipo, ubicacion, true);
        equipo2.getDireccionesIP().add("192.168.0.2");

        // Agregar equipos a la lista
        equipos.put(equipo1.getCodigo(), equipo1);
        equipos.put(equipo2.getCodigo(), equipo2);

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

        // Verificar que las conexiones se han establecido
        List<String> resultado = calculo.traceRoute("E1", "E2");
        List<String> resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add("E1");
        resultadoEsperado.add("E2");
        assertEquals(resultado, resultadoEsperado);
    }

    @Test
    public void testPingEquipoActivo() {
        calculo.cargarDatos(equipos, conexiones);

        boolean resultado = calculo.ping("E1");
        assertTrue(resultado);
    }

    @Test
    public void testPingEquipoInactivo() {
        // Crear un equipo inactivo
        Ubicacion ubicacion = new Ubicacion("U2", "Oficina");
        Equipo equipoInactivo = new Equipo("E3", "Router Inactivo", "Cisco", "RV340", new TipoEquipo("TE1", "Router"),
                ubicacion, false);
        equipos.put(equipoInactivo.getCodigo(), equipoInactivo);

        calculo.cargarDatos(equipos, conexiones);

        boolean resultado = calculo.ping("E3");
        assertFalse(resultado);
    }

    @Test
    public void testTraceRouteNoExistente() {
        calculo.cargarDatos(equipos, conexiones);

        List<String> resultado = calculo.traceRoute("E1", "E3"); // E3 no existe
        assertNull(resultado);
    }

    @Test
    public void testTraceRouteSinCamino() {
        // Crear un tercer equipo sin conexión
        Ubicacion ubicacion = new Ubicacion("U3", "Sala de conferencias");
        Equipo equipo3 = new Equipo("E3", "Router Aislado", "Cisco", "RV340", new TipoEquipo("TE1", "Router"),
                ubicacion, true);
        equipos.put(equipo3.getCodigo(), equipo3);

        calculo.cargarDatos(equipos, conexiones);

        List<String> resultado = calculo.traceRoute("E1", "E3"); // E3 no tiene conexión con E1
        assertNull(resultado);
    }
}