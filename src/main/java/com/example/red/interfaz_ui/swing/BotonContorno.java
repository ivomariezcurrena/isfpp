package com.example.red.interfaz_ui.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class BotonContorno extends JButton {

    // Constructor de la clase
    public BotonContorno() {
        setContentAreaFilled(false); // Desactiva el area de contenido por defecto
        setBorder(new EmptyBorder(5, 0, 5, 0)); // Establece un borde vacio
        setBackground(Color.WHITE); // Establece el fondo del boton en blanco
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a mano cuando pasa el raton
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        int ancho = getWidth(); // Obtiene el ancho del boton
        int alto = getHeight(); // Obtiene la altura del boton
        Graphics2D g2 = (Graphics2D) graficos; // Convierte el objeto Graphics a Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Activa el anti-aliasing para suavizar bordes
        g2.setColor(getBackground()); // Establece el color de fondo del boton
        g2.drawRoundRect(0, 0, ancho - 1, alto - 1, alto, alto); // Dibuja un rectangulo redondeado en el contorno del boton
        super.paintComponent(graficos); // Llama al metodo padre para pintar el contenido del boton
    }
}