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
import java.util.ResourceBundle;

import javax.swing.JLabel;

import com.example.red.gui.swing.BotonContorno;
import com.example.red.servicio.IdiomaService;

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

    // Etiquetas (JLabel) para mostrar el titulo y descripciones
    private JLabel titulo;
    private JLabel descripcion;
    private JLabel descripcion1;

    // Boton personalizado que se usara en el panel
    private BotonContorno boton;

    // Booleano que indica si el panel esta en modo "Login" o "Registro"
    private boolean esLogin;

    // idioma
    private ResourceBundle idioma;

    // Constructor del panel
    public PanelCover() {
        // idioma
        idioma = IdiomaService.getRb();

        initComponents();
        // Hace que el panel sea transparente
        setOpaque(false);

        // Define el diseño con MigLayout, organizando los componentes centrados
        diseño = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(diseño);
        init(); // Inicializa los elementos graficos
    }

    // Metodo para inicializar los componentes del panel
    private void init() {
        // Inicializa y configura el JLabel para el titulo
        titulo = new JLabel(idioma.getString("label_crearCuenta_mensaje1"));
        titulo.setFont(new Font("sansserif", 1, 30)); // Define fuente y tamaño
        titulo.setForeground(new Color(245, 245, 245)); // Color del texto
        add(titulo); // Añade el titulo al panel

        // Añade una primera descripcion
        descripcion = new JLabel(idioma.getString("label_crearCuenta_mensaje2"));
        descripcion.setForeground(new Color(245, 245, 245)); // Color del texto
        add(descripcion);

        // Añade una segunda descripcion
        descripcion1 = new JLabel(idioma.getString("label_crearCuenta_mensaje3"));
        descripcion1.setForeground(new Color(245, 245, 245)); // Color del texto
        add(descripcion1);

        // Crea y configura el boton de registro
        boton = new BotonContorno();
        boton.setBackground(new Color(255, 255, 255)); // Color de fondo del boton
        boton.setForeground(new Color(255, 255, 255)); // Color del texto del boton
        boton.setText(idioma.getString("label_boton_iniciarSesion")); // Texto del boton
        // Añade un listener al boton que ejecuta el evento al hacer clic
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                evento.actionPerformed(ae); // Dispara el evento al hacer clic
            }
        });
        add(boton, "w 60%, h 40"); // Añade el boton al panel con tamaño especifico
    }

    @SuppressWarnings("unchecked")
    // Metodo generado automaticamente para el diseño (generalmente ignorado)
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

    // Sobrescribe el metodo paintComponent para dibujar un fondo degradado
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        // Crea un degradado de color para el fondo del panel
        GradientPaint gradiente = new GradientPaint(0, 0, new Color(0, 133, 132), 0, getHeight(),
                new Color(0, 102, 102));
        g2.setPaint(gradiente);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs); // Llama al metodo padre para dibujar el resto del panel
    }

    // Metodo para agregar un listener de evento desde fuera de la clase
    public void agregarEvento(ActionListener evento) {
        this.evento = evento;
    }

    // Metodos para realizar animaciones (movimiento de componentes)
    // Anima el contenido hacia la izquierda para el registro
    public void registroIzquierda(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(false); // Cambia el panel al modo "Registro"
        // Modifica la posicion de los componentes en el layout
        diseño.setComponentConstraints(titulo, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion1, "pad 0 -" + valor + "% 0 0");
    }

    // Anima el contenido hacia la derecha para el registro
    public void registroDerecha(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(false); // Cambia al modo "Registro"
        diseño.setComponentConstraints(titulo, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion, "pad 0 -" + valor + "% 0 0");
        diseño.setComponentConstraints(descripcion1, "pad 0 -" + valor + "% 0 0");
    }

    // Anima hacia la izquierda para el modo "Login"
    public void loginIzquierda(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(true); // Cambia al modo "Login"
        diseño.setComponentConstraints(titulo, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion1, "pad 0 " + valor + "% 0 " + valor + "%");
    }

    // Anima hacia la derecha para el modo "Login"
    public void loginDerecha(double valor) {
        valor = Double.valueOf(formatoDecimal.format(valor));
        cambiarAModoLogin(true); // Cambia al modo "Login"
        diseño.setComponentConstraints(titulo, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion, "pad 0 " + valor + "% 0 " + valor + "%");
        diseño.setComponentConstraints(descripcion1, "pad 0 " + valor + "% 0 " + valor + "%");
    }

    // Cambia entre los modos "Login" y "Registro" actualizando los textos y el
    // boton
    public void cambiarAModoLogin(boolean login) {
        if (this.esLogin != login) {
            if (login) {
                // Cambia el contenido para el modo "Login"
                titulo.setText(idioma.getString("label_iniciarSesion_mensaje1"));
                descripcion.setText(idioma.getString("label_iniciarSesion_mensaje2"));
                descripcion1.setText(idioma.getString("label_iniciarSesion_mensaje3"));
                boton.setText(idioma.getString("label_boton_registrarse"));
            } else {
                // Cambia el contenido para el modo "Registro"
                titulo.setText(idioma.getString("label_crearCuenta_mensaje1"));
                descripcion.setText(idioma.getString("label_crearCuenta_mensaje2"));
                descripcion1.setText(idioma.getString("label_crearCuenta_mensaje3"));
                boton.setText(idioma.getString("label_boton_iniciarSesion"));
            }
            this.esLogin = login; // Actualiza el estado del modo
        }
    }

    // Declaracion de variables (para el diseño generado automaticamente)
}

/**
 * La clase PanelCover representa un panel de bienvenida y registro en una
 * interfaz gráfica de usuario,
 * permitiendo alternar entre modos "Login" y "Registro" mediante una transición
 * suave y animada.
 *
 * Funcionalidades principales:
 * - **Fondo degradado**: Añade un fondo de color degradado que va de verde
 * oscuro a un tono más claro,
 * ofreciendo una apariencia moderna y visualmente agradable.
 * - **Alternancia entre modos**: Cambia el contenido del panel para adaptarse a
 * los modos de
 * "Login" y "Registro" mediante el método cambiarAModoLogin(boolean login), que
 * actualiza los textos
 * y el botón.
 * - **Animaciones de transición**: Cuatro métodos (`registroIzquierda`,
 * `registroDerecha`,
 * `loginIzquierda`, `loginDerecha`) permiten una animación de desplazamiento,
 * moviendo los elementos
 * hacia los lados según el modo.
 * - **Botón con evento personalizado**: El botón principal permite agregar un
 * ActionListener personalizado
 * mediante el método agregarEvento(ActionListener evento), lo que facilita su
 * integración en aplicaciones.
 *
 * Este panel es ideal para pantallas de bienvenida o registro, adaptándose
 * visual y funcionalmente al
 * contexto de autenticación que la aplicación necesite.
 */
