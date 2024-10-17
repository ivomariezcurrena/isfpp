package com.example.red.interfaz_ui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class PanelVerifyCode extends javax.swing.JPanel {

        // Constructor: Configura el panel y sus componentes
        public PanelVerifyCode() {
                initComponents(); // Inicializa los componentes de la interfaz
                setOpaque(false); // Hace que el fondo sea transparente
                setFocusCycleRoot(true); // Define el ciclo de enfoque en este componente
                super.setVisible(false); // El panel se inicia no visible
                addMouseListener(new MouseAdapter() {
                }); // Escucha eventos de clic, aunque no realiza ninguna acción
        }

        // Sobrescribe el método para hacer visible el panel
        @Override
        public void setVisible(boolean visible) {
                super.setVisible(visible);
                if (visible) {
                        txtCodigo.grabFocus(); // Establece el foco en el campo de texto
                        txtCodigo.setText(""); // Limpia el campo de texto
                }
        }

        @SuppressWarnings("unchecked")
        // Método generado automáticamente para inicializar los componentes
        private void initComponents() {

                panelRedondeado = new com.example.red.interfaz_ui.swing.PanelRedondeado();
                txtCodigo = new com.example.red.interfaz_ui.swing.CampoTexto();
                etiquetaTitulo = new javax.swing.JLabel();
                etiquetaInstrucciones = new javax.swing.JLabel();
                btnAceptar = new com.example.red.interfaz_ui.swing.BotonContorno();
                btnCancelar = new com.example.red.interfaz_ui.swing.BotonContorno();

                // Configuración del campo de texto
                txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

                // Etiqueta de título
                etiquetaTitulo.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
                etiquetaTitulo.setForeground(new java.awt.Color(63, 63, 63));
                etiquetaTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                etiquetaTitulo.setText("Verificar Código");

                // Etiqueta de instrucciones
                etiquetaInstrucciones.setForeground(new java.awt.Color(63, 63, 63));
                etiquetaInstrucciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                etiquetaInstrucciones.setText("Revisa tu correo para obtener el código de verificación");

                // Botón Aceptar
                btnAceptar.setBackground(new java.awt.Color(18, 138, 62));
                btnAceptar.setText("Aceptar");

                // Botón Cancelar
                btnCancelar.setBackground(new java.awt.Color(192, 25, 25));
                btnCancelar.setText("Cancelar");
                btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnCancelarActionPerformed(evt); // Acción al presionar cancelar
                        }
                });

                // Diseño del panel redondeado
                javax.swing.GroupLayout panelRedondeadoLayout = new javax.swing.GroupLayout(panelRedondeado);
                panelRedondeado.setLayout(panelRedondeadoLayout);
                panelRedondeadoLayout.setHorizontalGroup(
                                panelRedondeadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelRedondeadoLayout.createSequentialGroup()
                                                                .addGap(100, 100, 100)
                                                                .addGroup(panelRedondeadoLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtCodigo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(etiquetaTitulo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(etiquetaInstrucciones,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                250,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(panelRedondeadoLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(25, 25, 25)
                                                                                                .addComponent(btnAceptar,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                94,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(btnCancelar,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                94,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(100, 100, 100)));
                panelRedondeadoLayout.setVerticalGroup(
                                panelRedondeadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                panelRedondeadoLayout.createSequentialGroup()
                                                                                .addGap(20, 20, 20)
                                                                                .addComponent(etiquetaTitulo)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(etiquetaInstrucciones)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(txtCodigo,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(panelRedondeadoLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(btnAceptar,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                32,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(btnCancelar,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                32,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(20, 20, 20)));

                // Layout principal del panel
                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(50, Short.MAX_VALUE)
                                                                .addComponent(panelRedondeado,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(50, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(50, Short.MAX_VALUE)
                                                                .addComponent(panelRedondeado,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(50, Short.MAX_VALUE)));
        }

        // Acción para el botón cancelar
        private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false); // Oculta el panel cuando se cancela
        }

        // Método para dibujar el fondo translúcido
        @Override
        protected void paintComponent(Graphics graficos) {
                Graphics2D g2 = (Graphics2D) graficos;
                g2.setColor(new Color(50, 50, 50));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Fondo semitransparente
                g2.fillRect(0, 0, getWidth(), getHeight()); // Rellena el panel
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintComponent(graficos); // Llama al método original de JPanel
        }

        // Devuelve el código introducido en el campo de texto
        public String getCodigoIngresado() {
                return txtCodigo.getText().trim();
        }

        // Permite agregar un evento al botón OK
        public void agregarEventoBotonAceptar(ActionListener evento) {
                btnAceptar.addActionListener(evento);
        }

        // Variables del panel
        private com.example.red.interfaz_ui.swing.BotonContorno btnCancelar;
        private com.example.red.interfaz_ui.swing.BotonContorno btnAceptar;
        private javax.swing.JLabel etiquetaTitulo;
        private javax.swing.JLabel etiquetaInstrucciones;
        private com.example.red.interfaz_ui.swing.PanelRedondeado panelRedondeado;
        private com.example.red.interfaz_ui.swing.CampoTexto txtCodigo;
}