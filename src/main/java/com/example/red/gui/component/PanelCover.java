package com.example.red.gui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JLabel;

import com.example.red.gui.swing.BotonContorno;

import net.miginfocom.swing.MigLayout;

// Componente grafico que se usa en la clase principal
public class PanelCover extends javax.swing.JPanel {
    // Formateador numerico para manejar decimales con tres digitos de precision
    private final DecimalFormat formatoDecimal = new DecimalFormat("##0.###",
            DecimalFormatSymbols.getInstance(Locale.US));
    // Listener para manejar los eventos de accion (clics en el boton)
    private ActionListener evento;
    // MigLayout organiza los componentes dentro del panel
    private MigLayout diseño;

    // Etiquetas para el titulo y descripciones
    private JLabel titulo;
    private JLabel descripcion;
    private JLabel descripcion1;
    private BotonContorno boton;
    // Booleano que indica si el panel esta en modo "Login" o "Registro"
    private boolean esLogin;

    // Constructor del panel
    public PanelCover() {
        initComponents();
        // Hace que el panel sea transparente para poder dibujar un fondo personalizado
        setOpaque(false);

        // Organiza los componentes dentro del panel
        diseño = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(diseño);
        init(); // Inicializa los elementos graficos
    }

    // Inicializa los componentes visuales del panel
    private void init() {
        // Inicializa el JLabel para el titulo y lo añade al panel
        titulo = new JLabel("¡Bienvenido de nuevo!");
        titulo.setFont(new Font("sansserif", 1, 30)); // Fuente y tamaño del titulo
        titulo.setForeground(new Color(245, 245, 245)); // Color del texto
        add(titulo); // Añade el titulo al panel

        // Añade una primera descripcion
        descripcion = new JLabel("Para conectarte, por favor");
        descripcion.setForeground(new Color(245, 245, 245)); // Color del texto
        add(descripcion);

        // Añade una segunda descripcion
        descripcion1 = new JLabel("registrate con tu informacion personal");
        descripcion1.setForeground(new Color(245, 245, 245)); // Color del texto
        add(descripcion1);

        // Crea un boton con el texto "REGISTRARME" y le añade un listener para manejar
        // los clics
        boton = new BotonContorno();
        boton.setBackground(new Color(255, 255, 255)); // Color del boton
        boton.setForeground(new Color(255, 255, 255)); // Color del texto del boton
        boton.setText("REGISTRARME"); // Texto del boton
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                evento.actionPerformed(ae); // Dispara el evento cuando se hace clic
            }
        });
        add(boton, "w 60%, h 40"); // Añade el boton con tamaño especifico
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Codigo generado">
    private void initComponents() {

        javax.swing.GroupLayout diseño = new javax.swing.GroupLayout(this);
        this.setLayout(diseño);
        diseño.setHorizontalGroup(
                diseño.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE));
        diseño.setVerticalGroup(
                diseño.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));
    }
    // </editor-fold>

    // Sobrescribe el metodo paintComponent para dibujar un fondo degradado en el
    // panel
    // El degradado va de un color mas claro (0, 133, 132) a uno mas oscuro (0, 102,
    // 102)
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gradiente = new GradientPaint(0, 0, new Color(0, 133, 132), 0, getHeight(),
                new Color(0, 102, 102));
        g2.setPaint(gradiente);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Permite asignar un ActionListener externo para manejar los eventos del boton
    public void agregarEvento(ActionListener evento) {
        this.evento = evento;
    }

    // Ajusta la posicion del titulo y las descripciones hacia la izquierda,
    // simulando una animacion
    public void registroIzquierda(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(false); // Establece el modo en "Registro"
        diseño.setComponentConstraints(titulo, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion1, "pad 0 -" + valor + "% 0 0");
    }

    // Similar al metodo anterior, pero animando hacia la derecha
    public void registroDerecha(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(false); // Establece el modo en "Registro"
        diseño.setComponentConstraints(titulo, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion1, "pad 0 -" + valor + "% 0 0");
    }

    // Anima los componentes hacia la izquierda en el modo "Login"
    public void loginIzquierda(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(true); // Establece el modo en "Login"
        diseño.setComponentConstraints(titulo, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion1, "pad 0 " + valor + "% 0 " + valor + "%");
    }

    // Anima los componentes hacia la derecha en el modo "Login"
    public void loginDerecha(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(true); // Establece el modo en "Login"
        diseño.setComponentConstraints(titulo, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion1, "pad 0 " + valor + "% 0 " + valor + "%");
    }

    // Este metodo actualiza los textos del panel dependiendo del modo "Login" o
    // "Registro"
    public void cambiarAModoLogin(boolean login) {
        if (this.esLogin != login) {
            if (login) {
                titulo.setText("¡Hola Amigo!");
                descripcion.setText("Ingresa tus datos en 5 min");
                descripcion1.setText("para poder empezar a usar el sistema");
                boton.setText("REGISTRARME");
            } else {
                titulo.setText("¡Bienvenido de nuevo!");
                descripcion.setText("Inicia sesion ahora con tu informacion");
                descripcion1.setText("para usar el sistema");
                boton.setText("INICIAR SESION");
            }
            this.esLogin = login; // Actualiza el estado del panel
        }
    }

    // Variables declaration - do not modify
    // End of variables declaration
}
