package com.example.red.interfaz_ui.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
public class Boton extends JButton {

    // Obtiene el color del efecto visual
    public Color getColorEfecto() {
        return colorEfecto;
    }

    // Establece el color del efecto visual
    public void setColorEfecto(Color colorEfecto) {
        this.colorEfecto = colorEfecto;
    }

    private Animator animador;  // Animador que controla las transiciones de animacion
    private int tamanoObjetivo; // Tamaño del efecto animado
    private float tamanoAnimado; // Tamaño actual durante la animacion
    private Point puntoPresionado; // Punto donde se presiono el boton
    private float transparencia; // Nivel de transparencia del efecto
    private Color colorEfecto = new Color(255, 255, 255); // Color del efecto visual

    public Boton() {
        setContentAreaFilled(false); // Desactiva el area de contenido por defecto
        setBorder(new EmptyBorder(5, 0, 5, 0)); // Establece un borde vacio para el boton
        setBackground(Color.WHITE); // Establece el fondo del boton en blanco
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a mano al pasar por encima
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                tamanoObjetivo = Math.max(getWidth(), getHeight()) * 2; // Calcula el tamaño del efecto animado
                tamanoAnimado = 0; // Inicializa el tamaño animado a 0
                puntoPresionado = me.getPoint(); // Guarda el punto donde se presiono
                transparencia = 0.5f; // Define el nivel de transparencia inicial
                if (animador.isRunning()) {
                    animador.stop(); // Detiene la animacion si ya esta corriendo
                }
                animador.start(); // Inicia la animacion
            }
        });

        // Define el comportamiento del animador durante la animacion
        TimingTarget objetivo = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraccion) {
                if (fraccion > 0.5f) {
                    transparencia = 1 - fraccion; // Ajusta la transparencia segun el progreso de la animacion
                }
                tamanoAnimado = fraccion * tamanoObjetivo; // Ajusta el tamaño del efecto segun la fraccion de la animacion
                repaint(); // Redibuja el boton
            }
        };

        animador = new Animator(700, objetivo); // Crea el animador con una duracion de 700ms
        animador.setAcceleration(0.5f); // Configura la aceleracion del animador
        animador.setDeceleration(0.5f); // Configura la desaceleracion del animador
        animador.setResolution(0); // Ajusta la resolucion del animador para animaciones suaves
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int ancho = getWidth(); // Obtiene el ancho del boton
        int alto = getHeight(); // Obtiene la altura del boton
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB); // Crea una imagen en memoria
        Graphics2D g2 = imagen.createGraphics(); // Obtiene el objeto para dibujar en la imagen
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Activa el anti-aliasing para suavizar bordes
        g2.setColor(getBackground()); // Establece el color de fondo
        g2.fillRoundRect(0, 0, ancho, alto, alto, alto); // Dibuja un rectangulo redondeado con las dimensiones del boton
        if (puntoPresionado != null) {
            g2.setColor(colorEfecto); // Establece el color del efecto visual
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, transparencia)); // Aplica la transparencia
            g2.fillOval((int) (puntoPresionado.x - tamanoAnimado / 2), (int) (puntoPresionado.y - tamanoAnimado / 2),
                    (int) tamanoAnimado, (int) tamanoAnimado); // Dibuja el efecto de onda circular en el punto de presion
        }
        g2.dispose(); // Libera los recursos del objeto Graphics2D
        grphcs.drawImage(imagen, 0, 0, null); // Dibuja la imagen final en el boton
        super.paintComponent(grphcs); // Llama al metodo padre para dibujar el resto del boton
    }
}