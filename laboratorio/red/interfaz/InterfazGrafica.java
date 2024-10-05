package red.interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import red.modelo.*;
import red.logica.Logica;
import red.datos.*;

public class InterfazGrafica extends JFrame {
    private Logica logica;
    private TreeMap<String, Equipo> equipos;
    private List<Conexion> conexiones;
    private JTextArea outputArea;
    private JComboBox<String> pingComboBox;
    private JComboBox<String> tracerouteComboBox1;
    private JComboBox<String> tracerouteComboBox2;

    public InterfazGrafica() {
        // Configuracion basica de la ventana
        setTitle("Gestión de Red");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear y configurar el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tabbedPane, BorderLayout.CENTER);

        // Panel de carga de datos
        JPanel loadPanel = crearPanel("Cargar Datos", new Color(230, 230, 250));
        JButton loadButton = crearBoton("Cargar Datos", new Color(0, 123, 255), e -> cargarDatos());
        loadPanel.add(loadButton);
        tabbedPane.addTab("Cargar Datos", loadPanel);

        // Panel de ping
        JPanel pingPanel = crearPanel("Ping", new Color(240, 255, 240));
        pingComboBox = new JComboBox<>();
        pingPanel.add(crearEtiqueta("Seleccione una IP:"));
        pingPanel.add(pingComboBox);
        JButton pingButton = crearBoton("Realizar Ping", new Color(60, 179, 113), e -> realizarPing((String) pingComboBox.getSelectedItem()));
        pingPanel.add(pingButton);
        tabbedPane.addTab("Ping", pingPanel);

        // Panel de traceroute
        JPanel traceroutePanel = crearPanel("Traceroute", new Color(255, 240, 245));
        tracerouteComboBox1 = new JComboBox<>();
        tracerouteComboBox2 = new JComboBox<>();
        traceroutePanel.add(crearEtiqueta("IP Origen:"));
        traceroutePanel.add(tracerouteComboBox1);
        traceroutePanel.add(crearEtiqueta("IP Destino:"));
        traceroutePanel.add(tracerouteComboBox2);
        JButton tracerouteButton = crearBoton("Realizar Traceroute", new Color(255, 105, 180), e -> realizarTraceroute((String) tracerouteComboBox1.getSelectedItem(), (String) tracerouteComboBox2.getSelectedItem()));
        traceroutePanel.add(tracerouteButton);
        tabbedPane.addTab("Traceroute", traceroutePanel);

        // Panel de MST (Arbol de Expansion Minima)
        JPanel mstPanel = crearPanel("Árbol de Expansión Mínima", new Color(245, 245, 220));
        JButton mstButton = crearBoton("Mostrar MST", new Color(255, 165, 0), e -> mostrarMST());
        mstPanel.add(mstButton);
        tabbedPane.addTab("Árbol de Expansión Mínima", mstPanel);

        // Area de salida
        outputArea = new JTextArea(20, 80);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.GRAY)));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Metodo para crear paneles con un título y color de fondo
    private JPanel crearPanel(String titulo, Color colorFondo) {
        JPanel panel = new JPanel();
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        return panel;
    }

    // Metodo para crear etiquetas
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    // Metodo para crear botones
    private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(colorFondo);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addActionListener(listener);
        return button;
    }

    // Metodo para cargar datos de archivos y llenar los ComboBox con las IPs de los equipos
    private void cargarDatos() {
        try {
            CargarParametros.parametros();
            equipos = Dato.cargarEquipos(CargarParametros.getArchivoComputadoras(), CargarParametros.getArchivoRouters());
            conexiones = Dato.cargarConexiones(CargarParametros.getArchivoConexiones(), equipos);
            logica = new Logica(equipos, conexiones);
            outputArea.setText("Datos cargados exitosamente.\n");

            // Rellenar los ComboBox con las direcciones IP de los equipos cargados
            pingComboBox.removeAllItems();
            tracerouteComboBox1.removeAllItems();
            tracerouteComboBox2.removeAllItems();

            // Añadir IPs de los equipos a los ComboBox
            for (Map.Entry<String, Equipo> entry : equipos.entrySet()) {
                String id = entry.getKey();
                String ip = entry.getValue().getIpAddress();
                String displayText = id + ": " + ip;
                pingComboBox.addItem(displayText);
                tracerouteComboBox1.addItem(displayText);
                tracerouteComboBox2.addItem(displayText);
            }
        } catch (Exception e) {
            outputArea.setText("Error al cargar los datos: " + e.getMessage() + "\n");
        }
    }

    // Metodo para realizar el ping a una IP seleccionada
    private void realizarPing(String displayText) {
        if (logica == null) {
            outputArea.setText("Primero cargue los datos.\n");
            return;
        }

        String ipAddress = displayText.split(": ")[1]; // Extraer IP del texto seleccionado
        boolean activo = logica.ping(ipAddress);
        outputArea.append("Ping a " + displayText + ": " + (activo ? "Activo" : "Inactivo") + "\n");
    }

    // Metodo para realizar traceroute entre dos IPs seleccionadas
    private void realizarTraceroute(String displayText1, String displayText2) {
        if (logica == null) {
            outputArea.setText("Primero cargue los datos.\n");
            return;
        }

        String ipOrigen = displayText1.split(": ")[1]; // Extraer IP origen del texto seleccionado
        String ipDestino = displayText2.split(": ")[1]; // Extraer IP destino del texto seleccionado
        List<Conexion> camino = logica.traceroute(ipOrigen, ipDestino);

        if (camino == null || camino.isEmpty()) {
            outputArea.append("No se encontró una ruta entre " + displayText1 + " y " + displayText2 + "\n");
        } else {
            outputArea.append("\nTraceroute desde " + displayText1 + " hasta " + displayText2 + ":\n");
            for (Conexion conexion : camino) {
                outputArea.append(conexion.getSource().getId() + ": " + conexion.getSource().getIpAddress() + " -- " +
                        conexion.getTarget().getId() + ": " + conexion.getTarget().getIpAddress() + " : " +
                        conexion.getBandwidth() + " ms\n");
            }
        }
    }

    // Metodo para mostrar el Arbol de Expansion Minima (MST)
    private void mostrarMST() {
        if (logica == null) {
            outputArea.setText("\nPrimero cargue los datos.\n");
            return;
        }

        List<Conexion> mst = logica.calcularMST();
        outputArea.append("\nÁrbol de Expansión Mínima:\n");
        for (Conexion conexion : mst) {
            outputArea.append(conexion.getSource().getId() + ": " + conexion.getSource().getIpAddress() + " -> " +
                    conexion.getTarget().getId() + ": " + conexion.getTarget().getIpAddress() + " : " +
                    conexion.getBandwidth() + " Mbps\n");
        }
    }
}
