/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.red.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.red.conexion.ConexionBD;
import com.example.red.controlador.Constantes;
import com.example.red.gui.app.RedVisual;
import com.example.red.modelo.Conexion;
import com.example.red.modelo.Equipo;
import com.example.red.negocio.Calculo;
import com.example.red.negocio.Red;
import com.example.red.servicio.IdiomaService;
import com.example.red.servicio.ModoRealService;
import com.example.red.servicio.PingWorker;
import com.example.red.servicio.ServiceUsuario;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

/**
 *
 * @author Lautaro
 */
public class InterfazUI extends javax.swing.JFrame {
    // idioma
    private ResourceBundle idioma;

    private Red red;
    private Calculo calculo;
    private RedVisual ventana;

    /**
     * Creates new form interfazUI
     */
    public InterfazUI() {
        // Idioma
        idioma = IdiomaService.getRb();

        initComponents();
        // Configurar que la ventana no se pueda redimensionar
        this.setResizable(false);
        // Centrar la ventana en la pantalla
        this.setLocationRelativeTo(null);

        // Copiar las referencias de red y calculo
        red = Red.getRed();
        calculo = new Calculo();
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());

        RangoBox1.addItem("192.168.16");
        PingBoton.setBackground(new java.awt.Color(0, 102, 102));
        TraceBoton.setBackground(new java.awt.Color(0, 102, 102));
        RangoBoton.setBackground(new java.awt.Color(0, 102, 102));

        for (Equipo equipo : red.getEquipos()) {
            String displayText = equipo.getCodigo();

            PingBox.addItem(displayText);
            TraceBox1.addItem(displayText);
            TraceBox2.addItem(displayText);

        }

        cargarGrafo(red.getTablaEquipos(), red.getTablaConexiones());
    }

    public void cargarGrafo(Map<String, Equipo> tablaEquipos, Map<String, Conexion> tablaConexiones) {
        ventana = new RedVisual(idioma.getString("label_grafo"));
        ventana.cargarDatos(tablaEquipos, tablaConexiones);
    }

    public void mostrarGrafo() {
        // Si ya existe, cierra la ventana
        if (ventana != null || ventana.isDisplayable()) {
            ventana.dispose();
        }

        // Nueva ventana
        if (ventana == null || !ventana.isDisplayable()) {
            cargarGrafo(red.getTablaEquipos(), red.getTablaConexiones());
            ventana.renderizarRed();
            ventana.renderizarBotones();
        }
    }

    public void actualizarEstadosReales(boolean todos, List<String> ids) {
        red.actualizarEstadosReales(todos, ids);

        // cargar de vuelta para tenelos actualizados
        calculo.cargarDatos(red.getTablaEquipos(), red.getConexiones());
        cargarGrafo(red.getTablaEquipos(), red.getTablaConexiones());

        // Refrescar la ventana del grafo solo si ya existia
        if (ventana != null && ventana.isDisplayable()) {
            mostrarGrafo();
        }
    }

    public void mostrarPing() {
        // Ventana del grafo visual
        ventana.setEstiloNodosTodos("default");
        ventana.setEstiloArcosTodos("default");
        HelperLabel.setText(idioma.getString("label_espera"));

        String id = red.validarEquipo((String) PingBox.getSelectedItem());
        if (id != null) {
            if (Constantes.MODO_REAL.equals(ModoRealService.getModo())) {
                // actualizar red con los estados reales
                jTextArea1.setText("");
                List<String> ids = new ArrayList<>();
                ids.add(id);
                actualizarEstadosReales(false, ids);
            }

            String mensaje = idioma.getString("label_equipo") + " '" + id + "' "
                    + (calculo.ping(id) ? idioma.getString("label_activo") : idioma.getString("label_inactivo"));
            jTextArea1.setText(mensaje);
            HelperLabel.setText(idioma.getString("label_ping_log_titulo"));
            ventana.setEstiloNodo(id, "highlight");

        } else {
            HelperLabel.setText(idioma.getString("label_ping_log_titulo"));
            String mensaje = idioma.getString("label_equipo") + " '" + (String) PingBox.getSelectedItem() + "' "
                    + idioma.getString("label_noEncontrado");
            jTextArea1.setText(mensaje);
        }
    }

    public void mostrarRangoPing() {
        // Ventana del grafo visual
        ventana.setEstiloNodosTodos("default");
        ventana.setEstiloArcosTodos("default");

        List<String> equiposConEsaIP = red.rangoEquiposIP((String) RangoBox1.getSelectedItem());
        String msj = "";
        int count = 0;

        HelperLabel.setText(idioma.getString("label_espera"));
        if (equiposConEsaIP.isEmpty()) {
            jTextArea1.setText(idioma.getString("label_rango_log_resultado_EquiposNoEncontrados"));
            HelperLabel.setText(idioma.getString("label_rango_log_titulo_EquiposNoEncontrados"));
        } else {
            if (Constantes.MODO_REAL.equals(ModoRealService.getModo())) {
                // actualizar red con los estados reales
                jTextArea1.setText("");
                actualizarEstadosReales(false, equiposConEsaIP);
            }
            Map<String, Boolean> resultado = calculo.rangoPing(equiposConEsaIP);
            HelperLabel.setText(idioma.getString("label_rango_log_titulo_EquiposEncontrados"));
            for (Map.Entry<String, Boolean> entry : resultado.entrySet()) {
                String id = entry.getKey();
                boolean isActivo = entry.getValue();
                msj = msj + ("'" + id + "' "
                        + (isActivo ? idioma.getString("label_activo") : idioma.getString("label_inactivo")) + "\n");
                count++;

                // Ventana del grafo visual
                ventana.setEstiloNodo(id, "highlight");
            }
            jTextArea1.setText(msj);
        }
    }

    public void mostrarTraceRoute() {
        // Ventana del grafo visual
        ventana.setEstiloNodosTodos("default");
        ventana.setEstiloArcosTodos("default");

        String id1 = (String) TraceBox1.getSelectedItem();
        String id2 = (String) TraceBox2.getSelectedItem();
        List<String> resultado = null;
        String msj = "";

        HelperLabel.setText(idioma.getString("label_espera"));
        if (id1 != null && id2 != null) {
            if (Constantes.MODO_REAL.equals(ModoRealService.getModo())) {
                // actualizar red con los estados reales
                jTextArea1.setText("");
                actualizarEstadosReales(true, null);
            }
            resultado = calculo.traceRoute(id1, id2);
        }
        if (resultado == null) {
            jTextArea1.setText(idioma.getString("label_traceroute_log_resultado_unEquipoNoEncontrado"));
            HelperLabel.setText(idioma.getString("label_traceroute_log_titulo_caminoNoEncontrado"));
        } else if (resultado.isEmpty()) {
            jTextArea1.setText(idioma.getString("label_traceroute_log_resultado_caminoNoEncontrado"));
            HelperLabel.setText(idioma.getString("label_traceroute_log_titulo_caminoNoEncontrado"));
        }

        else {
            HelperLabel.setText(idioma.getString("label_traceroute_log_titulo_caminoEncontrado"));
            for (int i = 0; i < resultado.size() - 1; i++) {

                id1 = resultado.get(i);
                id2 = resultado.get(i + 1);
                msj = msj + ("- " + id1 + " -> " + id2) + "\n";
            }
            jTextArea1.setText(msj);

            // Ventana del grafo visual
            ventana.setEstiloNodos(resultado, "highlight");
            ventana.setEstiloCaminoArcos(resultado, "highlight");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        HelperLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PingPanel = new javax.swing.JPanel();
        PingBox = new javax.swing.JComboBox<>();
        PingBoton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TracePanel = new javax.swing.JPanel();
        TraceBox1 = new javax.swing.JComboBox<>();
        TraceBox2 = new javax.swing.JComboBox<>();
        TraceBoton = new javax.swing.JButton();
        DecorLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        RangoPanel = new javax.swing.JPanel();
        RangoBox1 = new javax.swing.JComboBox<>();
        RangoBoton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuConect = new javax.swing.JMenu();
        EquiposMenu = new javax.swing.JMenuItem();
        ConexMenu = new javax.swing.JMenuItem();
        UbicMenu = new javax.swing.JMenuItem();
        TEquipoMenu = new javax.swing.JMenuItem();
        TCableMenu = new javax.swing.JMenuItem();
        TPuertoMenu = new javax.swing.JMenuItem();
        MenuGrafo = new javax.swing.JMenu();
        VerGrafoItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HelperLabel.setBackground(new java.awt.Color(0, 102, 102));
        HelperLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        HelperLabel.setForeground(new java.awt.Color(255, 255, 255));
        HelperLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HelperLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel4.add(HelperLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 380, 21));

        jTextArea1.setBackground(new java.awt.Color(0, 102, 102));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 380, 530));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 380, 570));

        jTabbedPane1.setBackground(new java.awt.Color(245, 245, 245));
        jTabbedPane1.setForeground(new java.awt.Color(0, 133, 132));
        jTabbedPane1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseEntered(evt);
            }
        });

        PingPanel.setBackground(new java.awt.Color(245, 245, 245));

        PingBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PingBoxMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PingBoxMouseExited(evt);
            }
        });
        PingBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PingBoxActionPerformed(evt);
            }
        });

        PingBoton.setBackground(new java.awt.Color(0, 102, 102));
        PingBoton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        PingBoton.setForeground(new java.awt.Color(255, 255, 255));
        PingBoton.setText(idioma.getString("label_ping_boton_titulo"));
        PingBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PingBotonMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PingBotonMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                PingBotonMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText(idioma.getString("label_ping_descripcion_parrafo_1"));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText(idioma.getString("label_ping_descripcion_titulo"));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText(idioma.getString("label_ping_descripcion_parrafo_2"));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText(idioma.getString("label_ping_descripcion_ayuda_3"));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("_____________________________________________");

        javax.swing.GroupLayout PingPanelLayout = new javax.swing.GroupLayout(PingPanel);
        PingPanel.setLayout(PingPanelLayout);
        PingPanelLayout.setHorizontalGroup(
                PingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PingPanelLayout.createSequentialGroup()
                                .addContainerGap(318, Short.MAX_VALUE)
                                .addGroup(PingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addGap(137, 137, 137))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addGap(166, 166, 166)
                                                        .addComponent(jLabel5)
                                                        .addGap(75, 75, 75))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel6)
                                                        .addGap(114, 114, 114))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addGap(98, 98, 98))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addGap(52, 52, 52))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(PingBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(221, 221, 221))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                PingPanelLayout.createSequentialGroup()
                                                        .addComponent(PingBoton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(235, 235, 235)))));
        PingPanelLayout.setVerticalGroup(
                PingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PingPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jLabel2)
                                .addGroup(PingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(PingPanelLayout.createSequentialGroup()
                                                .addGap(98, 98, 98)
                                                .addComponent(jLabel4))
                                        .addGroup(PingPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel6)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)))
                                .addGap(96, 96, 96)
                                .addComponent(PingBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122,
                                        Short.MAX_VALUE)
                                .addComponent(PingBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)));

        jTabbedPane1.addTab("Ping", PingPanel);

        TracePanel.setBackground(new java.awt.Color(245, 245, 245));

        TraceBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TraceBox1MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                TraceBox1MouseExited(evt);
            }
        });
        TraceBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraceBox1ActionPerformed(evt);
            }
        });

        TraceBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TraceBox2MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                TraceBox2MouseExited(evt);
            }
        });

        TraceBoton.setBackground(new java.awt.Color(0, 102, 102));
        TraceBoton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        TraceBoton.setForeground(new java.awt.Color(255, 255, 255));
        TraceBoton.setText(idioma.getString("label_traceroute_boton_titulo"));
        TraceBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TraceBotonMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TraceBotonMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                TraceBotonMouseExited(evt);
            }
        });
        TraceBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraceBotonActionPerformed(evt);
            }
        });

        DecorLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        DecorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DecorLabel.setText(" - - - - - - - - - - - - - - >");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText(idioma.getString("label_traceroute_descripcion_titulo"));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("________________________________________________________");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText(idioma.getString("label_traceroute_descripcion_parrafo_1"));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText(idioma.getString("label_traceroute_descripcion_parrafo_2"));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText(idioma.getString("label_traceroute_descripcion_parrafo_3"));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 102));
        jLabel12.setText(idioma.getString("label_traceroute_descripcion_parrafo_4"));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText(idioma.getString("label_traceroute_descripcion_parrafo_5"));

        javax.swing.GroupLayout TracePanelLayout = new javax.swing.GroupLayout(TracePanel);
        TracePanel.setLayout(TracePanelLayout);
        TracePanelLayout.setHorizontalGroup(
                TracePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(TracePanelLayout.createSequentialGroup()
                                .addContainerGap(454, Short.MAX_VALUE)
                                .addGroup(TracePanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TracePanelLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(TracePanelLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                TracePanelLayout.createSequentialGroup()
                                                                        .addComponent(TraceBox1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                150,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(DecorLabel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                156,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(TraceBox2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                150,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(50, 50, 50))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                TracePanelLayout.createSequentialGroup()
                                                                        .addComponent(TraceBoton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                120,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(234, 234, 234))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                TracePanelLayout.createSequentialGroup()
                                                                        .addGroup(TracePanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(jLabel8))
                                                                        .addGap(59, 59, 59)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                        TracePanelLayout.createSequentialGroup()
                                                                .addComponent(jLabel10)
                                                                .addGap(100, 100, 100))
                                                .addGroup(TracePanelLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel11)
                                                        .addComponent(jLabel9)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                TracePanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel12)
                                                        .addGap(73, 73, 73))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                TracePanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel13)
                                                        .addGap(98, 98, 98)))));
        TracePanelLayout.setVerticalGroup(
                TracePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(TracePanelLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(
                                        TracePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addGap(70, 70, 70)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56,
                                        Short.MAX_VALUE)
                                .addGroup(
                                        TracePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(TraceBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(DecorLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(TraceBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67)
                                .addComponent(TraceBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)));

        jTabbedPane1.addTab(idioma.getString("label_traceroute_tab_titulo"), TracePanel);

        RangoPanel.setBackground(new java.awt.Color(245, 245, 245));

        RangoBox1.setEditable(true);
        RangoBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RangoBox1MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                RangoBox1MouseExited(evt);
            }
        });

        RangoBoton.setBackground(new java.awt.Color(0, 102, 102));
        RangoBoton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        RangoBoton.setForeground(new java.awt.Color(255, 255, 255));
        RangoBoton.setText(idioma.getString("label_rango_boton_titulo"));
        RangoBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RangoBotonMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RangoBotonMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                RangoBotonMouseExited(evt);
            }
        });
        RangoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RangoBotonActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText(idioma.getString("label_rango_descripcion_titulo"));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 102));
        jLabel15.setText("__________________________________________________");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 102));
        jLabel16.setText(idioma.getString("label_rango_descripcion_parrafo_1"));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 102));
        jLabel17.setText(idioma.getString("label_rango_descripcion_parrafo_2"));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 102));
        jLabel18.setText(idioma.getString("label_rango_descripcion_parrafo_3"));

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 102));
        jLabel19.setText(idioma.getString("label_rango_descripcion_parrafo_4"));

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 102));
        jLabel20.setText(idioma.getString("label_rango_descripcion_parrafo_5"));

        javax.swing.GroupLayout RangoPanelLayout = new javax.swing.GroupLayout(RangoPanel);
        RangoPanel.setLayout(RangoPanelLayout);
        RangoPanelLayout.setHorizontalGroup(
                RangoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RangoPanelLayout.createSequentialGroup()
                                .addContainerGap(448, Short.MAX_VALUE)
                                .addGroup(RangoPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RangoPanelLayout
                                                .createSequentialGroup()
                                                .addGroup(RangoPanelLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel15)
                                                        .addComponent(jLabel14))
                                                .addGap(93, 93, 93))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                RangoPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel17)
                                                        .addGap(96, 96, 96))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RangoPanelLayout
                                                .createSequentialGroup()
                                                .addGroup(RangoPanelLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel18)
                                                        .addComponent(jLabel16))
                                                .addGap(51, 51, 51))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                RangoPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel19)
                                                        .addGap(87, 87, 87))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RangoPanelLayout
                                                .createSequentialGroup()
                                                .addGroup(RangoPanelLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(RangoBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel20))
                                                .addGap(219, 219, 219))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RangoPanelLayout
                                                .createSequentialGroup()
                                                .addComponent(RangoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(232, 232, 232)))));
        RangoPanelLayout.setVerticalGroup(
                RangoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RangoPanelLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82,
                                        Short.MAX_VALUE)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addGap(40, 40, 40)
                                .addComponent(RangoBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(RangoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)));

        jTabbedPane1.addTab(idioma.getString("label_rango_tab_titulo"), RangoPanel);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 630));

        MenuConect.setText(idioma.getString("label_menubar_botonEditar"));

        EquiposMenu.setText(idioma.getString("label_menubar_editarEquipos"));
        EquiposMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquiposMenuActionPerformed(evt);
            }
        });
        MenuConect.add(EquiposMenu);

        ConexMenu.setText(idioma.getString("label_menubar_editarConexiones"));
        ConexMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConexMenuActionPerformed(evt);
            }
        });
        MenuConect.add(ConexMenu);

        UbicMenu.setText(idioma.getString("label_menubar_editarUbicaciones"));
        UbicMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbicMenuActionPerformed(evt);
            }
        });
        MenuConect.add(UbicMenu);

        TEquipoMenu.setText(idioma.getString("label_menubar_editarTipoEquipo"));
        TEquipoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEquipoMenuActionPerformed(evt);
            }
        });
        MenuConect.add(TEquipoMenu);

        TCableMenu.setText(idioma.getString("label_menubar_editarTipoCable"));
        TCableMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TCableMenuMouseClicked(evt);
            }
        });
        TCableMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCableMenuActionPerformed(evt);
            }
        });
        MenuConect.add(TCableMenu);

        TPuertoMenu.setText(idioma.getString("label_menubar_editarTipoPuerto"));
        TPuertoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPuertoMenuActionPerformed(evt);
            }
        });
        MenuConect.add(TPuertoMenu);

        jMenuBar1.add(MenuConect);

        MenuGrafo.setText(idioma.getString("label_grafo"));

        VerGrafoItem.setText(idioma.getString("label_menubar_botonVerGrafo"));
        VerGrafoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerGrafoItemActionPerformed(evt);
            }
        });
        MenuGrafo.add(VerGrafoItem);

        jMenuBar1.add(MenuGrafo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCableMenuMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TCableMenuMouseClicked
        TCableList.main(null);
    }// GEN-LAST:event_TCableMenuMouseClicked

    private void TCableMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_TCableMenuActionPerformed
        TCableList.main(null);
    }// GEN-LAST:event_TCableMenuActionPerformed

    private void ConexMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ConexMenuActionPerformed
        ConexionList.main(null);
    }// GEN-LAST:event_ConexMenuActionPerformed

    private void EquiposMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EquiposMenuActionPerformed
        EquipoList.main(null);
    }// GEN-LAST:event_EquiposMenuActionPerformed

    private void TEquipoMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_TEquipoMenuActionPerformed
        TEquipoList.main(null);
    }// GEN-LAST:event_TEquipoMenuActionPerformed

    private void TPuertoMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_TPuertoMenuActionPerformed
        TPuertoList.main(null);
    }// GEN-LAST:event_TPuertoMenuActionPerformed

    private void UbicMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UbicMenuActionPerformed
        UbicacionList.main(null);
    }// GEN-LAST:event_UbicMenuActionPerformed

    private void VerGrafoItemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_VerGrafoItemActionPerformed
        mostrarGrafo();
    }// GEN-LAST:event_VerGrafoItemActionPerformed

    private void MenuEquipMouseClicked(java.awt.event.MouseEvent evt) {
        EquipoList.main(null);
    }

    private void PingBotonMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_PingBotonMouseEntered
        PingBoton.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_PingBotonMouseEntered

    private void PingBotonMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_PingBotonMouseExited
        PingBoton.setBackground(new java.awt.Color(0, 102, 102));
    }// GEN-LAST:event_PingBotonMouseExited

    private void TraceBotonMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBotonMouseEntered
        TraceBoton.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_TraceBotonMouseEntered

    private void TraceBotonMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBotonMouseExited
        TraceBoton.setBackground(new java.awt.Color(0, 102, 102));
    }// GEN-LAST:event_TraceBotonMouseExited

    private void PingBotonMouseClicked(java.awt.event.MouseEvent evt) {
        mostrarPing();
    }

    private void PingBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PingBoxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_PingBoxActionPerformed

    private void TraceBotonMouseClicked(java.awt.event.MouseEvent evt) {
        mostrarTraceRoute();
    }

    private void PingBoxMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_PingBoxMouseEntered
        PingBox.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_PingBoxMouseEntered

    private void PingBoxMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_PingBoxMouseExited
        PingBox.setBackground(new java.awt.Color(225, 225, 225));
    }// GEN-LAST:event_PingBoxMouseExited

    private void RangoBotonMouseClicked(java.awt.event.MouseEvent evt) {
        mostrarRangoPing();
    }

    private void TraceBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void TraceBox1MouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBox1MouseEntered
        TraceBox1.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_TraceBox1MouseEntered

    private void TraceBox1MouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBox1MouseExited
        TraceBox1.setBackground(new java.awt.Color(225, 225, 225));
    }// GEN-LAST:event_TraceBox1MouseExited

    private void jTabbedPane1MouseEntered(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void TraceBox2MouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBox2MouseEntered
        TraceBox2.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_TraceBox2MouseEntered

    private void TraceBox2MouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TraceBox2MouseExited
        TraceBox2.setBackground(new java.awt.Color(225, 225, 225));
    }// GEN-LAST:event_TraceBox2MouseExited

    private void RangoBotonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RangoBotonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_RangoBotonActionPerformed

    private void RangoBotonMouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_RangoBotonMouseExited
        RangoBoton.setBackground(new java.awt.Color(0, 102, 102));
    }// GEN-LAST:event_RangoBotonMouseExited

    private void RangoBotonMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_RangoBotonMouseEntered
        RangoBoton.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_RangoBotonMouseEntered

    private void RangoBox1MouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_RangoBox1MouseEntered
        RangoBox1.setBackground(new java.awt.Color(0, 133, 132));
    }// GEN-LAST:event_RangoBox1MouseEntered

    private void RangoBox1MouseExited(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_RangoBox1MouseExited
        RangoBox1.setBackground(new java.awt.Color(225, 225, 225));
    }// GEN-LAST:event_RangoBox1MouseExited

    private void TraceBotonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Configurar FlatLaf como Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        // </editor-fold>
        try {
            ConexionBD.getInstance().connectToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ConexMenu;
    private javax.swing.JLabel DecorLabel;
    private javax.swing.JMenuItem EquiposMenu;
    private javax.swing.JLabel HelperLabel;
    private javax.swing.JMenu MenuConect;
    private javax.swing.JMenu MenuGrafo;
    private javax.swing.JButton PingBoton;
    private javax.swing.JComboBox<String> PingBox;
    private javax.swing.JPanel PingPanel;
    private javax.swing.JButton RangoBoton;
    private javax.swing.JComboBox<String> RangoBox1;
    private javax.swing.JPanel RangoPanel;
    private javax.swing.JMenuItem TCableMenu;
    private javax.swing.JMenuItem TEquipoMenu;
    private javax.swing.JMenuItem TPuertoMenu;
    private javax.swing.JButton TraceBoton;
    private javax.swing.JComboBox<String> TraceBox1;
    private javax.swing.JComboBox<String> TraceBox2;
    private javax.swing.JPanel TracePanel;
    private javax.swing.JMenuItem UbicMenu;
    private javax.swing.JMenuItem VerGrafoItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
