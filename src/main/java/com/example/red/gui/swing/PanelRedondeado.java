package com.example.red.gui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelRedondeado extends JPanel {

    // Constructor: Configura la opacidad del panel
    public PanelRedondeado() {
        setOpaque(false); // Hace que el panel sea transparente
    }

    // Método para dibujar el panel con bordes redondeados
    @Override
    protected void paintComponent(Graphics graficos) {
        Graphics2D g2 = (Graphics2D) graficos;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suavizado de bordes
        g2.setColor(new Color(255, 255, 255)); // Color de fondo (blanco)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Dibuja el panel con bordes redondeados (radio 20)
        super.paintComponent(graficos); // Llama al método original de JPanel para que funcione el resto
    }
}