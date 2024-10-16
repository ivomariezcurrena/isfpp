package com.example.red.interfaz_ui.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class CampoTexto extends JTextField {

    // Obtener el texto de sugerencia
    public String obtenerSugerencia() {
        return sugerencia;
    }

    // Establecer el texto de sugerencia
    public void establecerSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    // Obtener el icono de prefijo
    public Icon obtenerIconoPrefijo() {
        return iconoPrefijo;
    }

    // Establecer el icono de prefijo
    public void establecerIconoPrefijo(Icon iconoPrefijo) {
        this.iconoPrefijo = iconoPrefijo;
        inicializarBorde();
    }

    // Obtener el icono de sufijo
    public Icon obtenerIconoSufijo() {
        return iconoSufijo;
    }

    // Establecer el icono de sufijo
    public void establecerIconoSufijo(Icon iconoSufijo) {
        this.iconoSufijo = iconoSufijo;
        inicializarBorde();
    }

    private Icon iconoPrefijo;
    private Icon iconoSufijo;
    private String sugerencia = "";

    // Constructor
    public CampoTexto() {
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Borde vacío
        setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
        setForeground(Color.decode("#7A8C8D")); // Color del texto
        setFont(new java.awt.Font("sansserif", 0, 13)); // Fuente
        setSelectionColor(new Color(75, 175, 152)); // Color de selección de texto
    }

    @Override
    protected void paintComponent(Graphics graficos) {
        Graphics2D g2 = (Graphics2D) graficos;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suavizado de bordes
        g2.setColor(new Color(230, 245, 241)); // Color de fondo del campo de texto
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5); // Fondo redondeado
        pintarIconos(graficos); // Pintar los iconos si existen
        super.paintComponent(graficos);
    }

    @Override
    public void paint(Graphics graficos) {
        super.paint(graficos);
        if (getText().length() == 0) { // Si no hay texto ingresado
            int altura = getHeight();
            ((Graphics2D) graficos).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Suavizado de texto
            Insets margenes = getInsets();
            FontMetrics fm = graficos.getFontMetrics();
            graficos.setColor(new Color(200, 200, 200)); // Color del texto de sugerencia
            graficos.drawString(sugerencia, margenes.left, altura / 2 + fm.getAscent() / 2 - 2); // Dibuja la sugerencia
        }
    }

    // Metodo para pintar los iconos de prefijo y sufijo
    private void pintarIconos(Graphics graficos) {
        Graphics2D g2 = (Graphics2D) graficos;
        if (iconoPrefijo != null) { // Si hay un icono de prefijo
            Image prefijo = ((ImageIcon) iconoPrefijo).getImage();
            int y = (getHeight() - iconoPrefijo.getIconHeight()) / 2;
            g2.drawImage(prefijo, 10, y, this);
        }
        if (iconoSufijo != null) { // Si hay un icono de sufijo
            Image sufijo = ((ImageIcon) iconoSufijo).getImage();
            int y = (getHeight() - iconoSufijo.getIconHeight()) / 2;
            g2.drawImage(sufijo, getWidth() - iconoSufijo.getIconWidth() - 10, y, this);
        }
    }

    // Metodo para inicializar los bordes segun los iconos
    private void inicializarBorde() {
        int izquierda = 15;
        int derecha = 15;
        // 5 es el valor por defecto
        if (iconoPrefijo != null) {
            izquierda = iconoPrefijo.getIconWidth() + 15; // Ajusta el margen izquierdo si hay icono de prefijo
        }
        if (iconoSufijo != null) {
            derecha = iconoSufijo.getIconWidth() + 15; // Ajusta el margen derecho si hay icono de sufijo
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, izquierda, 10, derecha)); // Ajuste del borde
    }
}
