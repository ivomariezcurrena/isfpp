package com.example.red.gui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class Message extends javax.swing.JPanel {

    // Metodo para obtener el estado de visibilidad del mensaje
    public boolean isShow() {
        return show;
    }

    // Metodo para establecer la visibilidad del mensaje
    public void setShow(boolean show) {
        this.show = show;
    }

    // Define el tipo de mensaje (exito o error) por defecto
    private MessageType messageType = MessageType.SUCCESS;
    private boolean show; // Variable que indica si el mensaje se muestra o no

    // Constructor de la clase Message
    public Message() {
        initComponents(); // Inicializa los componentes del panel
        setOpaque(false); // Hace el panel transparente
        setVisible(false); // El mensaje es invisible al inicio
    }

    /**
     * Metodo para mostrar un mensaje en el panel
     * 
     * @param messageType Tipo de mensaje (SUCCESS o ERROR)
     * @param message     Texto del mensaje a mostrar
     */
    public void showMessage(MessageType messageType, String message) {
        this.messageType = messageType; // Asigna el tipo de mensaje
        lbMessage.setText(message); // Establece el texto del mensaje

        // Verifica el tipo de mensaje y asigna el icono correspondiente
        if (messageType == MessageType.SUCCESS) {
            lbMessage.setIcon(new ImageIcon(getClass().getResource("/com/example/red/gui/icon/success.png")));
        } else {
            lbMessage.setIcon(new ImageIcon(getClass().getResource("/com/example/red/gui/icon/error.png")));
        }
    }

    /**
     * Metodo que inicializa los componentes graficos del panel.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        lbMessage = new javax.swing.JLabel();

        // Configura las propiedades del mensaje
        lbMessage.setFont(new java.awt.Font("sansserif", 0, 14)); // Fuente del texto
        lbMessage.setForeground(new java.awt.Color(248, 248, 248)); // Color del texto
        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // Centrado
        lbMessage.setText("Message"); // Texto predeterminado

        // Configura el layout del panel
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE));
    }

    /**
     * Metodo que dibuja el componente con el fondo dependiendo del tipo de mensaje
     * 
     * @param grphcs El objeto Graphics usado para dibujar el panel
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs; // Convierte el objeto Graphics en Graphics2D

        // Establece el color de fondo dependiendo del tipo de mensaje
        if (messageType == MessageType.SUCCESS) {
            g2.setColor(new Color(15, 174, 37)); // Color verde para mensajes de exito
        } else {
            g2.setColor(new Color(240, 52, 53)); // Color rojo para mensajes de error
        }

        // Establece transparencia del color de fondo
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.fillRect(0, 0, getWidth(), getHeight()); // Dibuja el fondo del panel
        g2.setComposite(AlphaComposite.SrcOver);

        // Dibuja el borde del panel en color gris claro
        g2.setColor(new Color(245, 245, 245));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        super.paintComponent(grphcs); // Llama al metodo original para continuar el dibujado
    }

    // Enumeracion que define los tipos de mensaje (exito o error)
    public static enum MessageType {
        SUCCESS, ERROR
    }

    // Declaracion de variables - No modificar
    private javax.swing.JLabel lbMessage;
}