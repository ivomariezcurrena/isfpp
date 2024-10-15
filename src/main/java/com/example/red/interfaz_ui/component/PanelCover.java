package com.example.red.interfaz_ui.component;

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

import com.example.red.interfaz_ui.swing.ButtonOutLine;

import net.miginfocom.swing.MigLayout;

//Componente grafico que se usa en la clase main
public class PanelCover extends javax.swing.JPanel {
    // Un formateador numerico para manejar decimales con tres digitos de precision
    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    // Un listener para manejar los eventos de accion (clics en el boton)
    private ActionListener event;
    // MigLayout organiza los componentes dentro del panel
    private MigLayout layout;

    private JLabel Titulo;// Etiqueta para el titulo
    private JLabel descripcion;// Etiqueta para una descripcion
    private JLabel descripcion1;// Etiqueta para una descripcion
    private ButtonOutLine boton;
    private boolean isLogin;// Un booleano que indica si el panel está en modo "Login" o "Registro".

    public PanelCover() {
        initComponents();
        // hace que el panel sea transparente, permitiendo que se dibuje un fondo
        // personalizado.
        setOpaque(false);

        // organiza los componentes en el panel
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");//
        setLayout(layout);
        init();// para configurar los elementos gráficos.

    }

    private void init() {
        // Inicializa el JLabel para el título y lo añade al panel
        Titulo = new JLabel("Bienvenido de nuevo!");
        Titulo.setFont(new Font("sansserif", 1, 30));
        Titulo.setForeground(new Color(245, 245, 245));
        add(Titulo);
        // Añade una primera descripción
        descripcion = new JLabel("Para conectarte porfavor");
        descripcion.setForeground(new Color(245, 245, 245));
        add(descripcion);

        // Añade una segunda descripción
        descripcion1 = new JLabel("registrate con tu informacion personal");
        descripcion1.setForeground(new Color(245, 245, 245));
        add(descripcion1);

        // Crea un botón con el texto "REGISTRARME", le añade un listener para manejar
        // los clics, y lo agrega al panel con un tamaño específico (w 60%, h 40)
        boton = new ButtonOutLine();
        boton.setBackground(new Color(255, 255, 255));
        boton.setForeground(new Color(255, 255, 255));
        boton.setText("REGISTRARME");
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.actionPerformed(ae);
            }
        });
        add(boton, "w 60%, h 40");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // Sobrescribe el metodo paintComponent para dibujar un fondo degradado en el
    // panel. El degradado va de un color mas claro (0, 133, 132) a uno mas oscuro
    // (0, 102, 102)
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        GradientPaint gra = new GradientPaint(0, 0, new Color(0, 133, 132), 0, getHeight(), new Color(0, 102, 102));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Permite asignar un ActionListener externo que manejará los eventos del botón
    // (cuando se haga clic).
    public void addEvent(ActionListener event) {
        this.event = event;
    }

    // Ajusta la posicion del titulo y las descripciones hacia la izquierda, en
    // funcion del valor v, simulando una animacion de transicion. Establece el modo
    // en "Registro" (login(false))
    public void registerLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(Titulo, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(descripcion, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(descripcion1, "pad 0 -" + v + "% 0 0");
    }

    // Similar al método anterior, pero animando hacia la derecha.
    public void registerRight(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(Titulo, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(descripcion, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(descripcion1, "pad 0 -" + v + "% 0 0");
    }

    // Anima los componentes hacia la izquierda en el modo "Login"
    public void loginLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(Titulo, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(descripcion, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(descripcion1, "pad 0 " + v + "% 0 " + v + "%");
    }

    // Anima los componentes hacia la derecha en el modo "Login"
    public void loginRight(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(Titulo, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(descripcion, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(descripcion1, "pad 0 " + v + "% 0 " + v + "%");
    }

    // Este método actualiza los textos del panel dependiendo de si está en el modo
    // "Login" o "Registro"
    public void login(boolean login) {
        if (this.isLogin != login) {
            if (login) {
                Titulo.setText("¡Hola Amigo!");
                descripcion.setText("Ingresa tus datos en 5 min");
                descripcion1.setText("para poder empezar a usar el sistema");
                boton.setText("REGISTRARME");
            } else {
                Titulo.setText("¡Bienvenido de nuevo!");
                descripcion.setText("Inicia sesion ahora con tu informacion");
                descripcion1.setText("para usar el sistema");
                boton.setText("INICIAR SESION");
            }
            this.isLogin = login;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
