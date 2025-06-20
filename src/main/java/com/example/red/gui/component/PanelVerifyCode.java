package com.example.red.gui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ResourceBundle;

import com.example.red.servicio.IdiomaService;

public class PanelVerifyCode extends javax.swing.JPanel {

        private ResourceBundle idioma;

        public PanelVerifyCode() {
                idioma = IdiomaService.getRb();

                initComponents();
                setOpaque(false);
                setFocusCycleRoot(true);
                super.setVisible(false);
                addMouseListener(new MouseAdapter() {
                });
        }

        @Override
        public void setVisible(boolean bln) {
                super.setVisible(bln);
                if (bln) {
                        txtCode.grabFocus();
                        txtCode.setText("");
                }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                panelRound1 = new com.example.red.gui.swing.PanelRedondeado();
                txtCode = new com.example.red.gui.swing.CampoTexto();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                cmdOK = new com.example.red.gui.swing.BotonContorno();
                cmdCancel = new com.example.red.gui.swing.BotonContorno();

                txtCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);

                jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(63, 63, 63));
                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setText(idioma.getString("label_verificacion_verificarCodigo"));

                jLabel2.setForeground(new java.awt.Color(63, 63, 63));
                jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel2.setText(idioma.getString("label_verificacion_verificarMail"));

                cmdOK.setBackground(new java.awt.Color(18, 138, 62));
                cmdOK.setText(idioma.getString("label_boton_ok"));

                cmdCancel.setBackground(new java.awt.Color(192, 25, 25));
                cmdCancel.setText(idioma.getString("label_cancelar"));
                cmdCancel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cmdCancelActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
                panelRound1.setLayout(panelRound1Layout);
                panelRound1Layout.setHorizontalGroup(
                                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelRound1Layout.createSequentialGroup()
                                                                .addGap(100, 100, 100)
                                                                .addGroup(panelRound1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(panelRound1Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                .addComponent(txtCode,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jLabel1,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jLabel2,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                250,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(panelRound1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(25, 25, 25)
                                                                                                .addComponent(cmdOK,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                94,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(cmdCancel,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                94,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(100, 100, 100)));
                panelRound1Layout.setVerticalGroup(
                                panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout
                                                                .createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(txtCode,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(panelRound1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(cmdOK,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(cmdCancel,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                32,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(20, 20, 20)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(50, Short.MAX_VALUE)
                                                                .addComponent(panelRound1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(50, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap(50, Short.MAX_VALUE)
                                                                .addComponent(panelRound1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(50, Short.MAX_VALUE)));
        }// </editor-fold>//GEN-END:initComponents

        private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmdCancelActionPerformed
                setVisible(false);
        }// GEN-LAST:event_cmdCancelActionPerformed

        @Override
        protected void paintComponent(Graphics grphcs) {
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setColor(new Color(50, 50, 50));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintComponent(grphcs);
        }

        public String getInputCode() {
                return txtCode.getText().trim();
        }

        public void addEventButtonOK(ActionListener event) {
                cmdOK.addActionListener(event);
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private com.example.red.gui.swing.BotonContorno cmdCancel;
        private com.example.red.gui.swing.BotonContorno cmdOK;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private com.example.red.gui.swing.PanelRedondeado panelRound1;
        private com.example.red.gui.swing.CampoTexto txtCode;
        // End of variables declaration//GEN-END:variables
}
/**
 * La clase PanelVerifyCode representa un panel de ingreso de código de
 * verificación.
 * Se utiliza en interfaces de autenticación para que el usuario ingrese un
 * código enviado
 * por correo electrónico u otro medio para completar su verificación.
 *
 * Funcionalidades principales:
 * - **Visualización modal**: El panel se superpone con un fondo
 * semitransparente cuando
 * está visible, destacando su carácter modal.
 * - **Ingreso de código**: Incluye un campo de texto centrado donde el usuario
 * ingresa
 * el código de verificación, con botones de confirmación y cancelación.
 * - **Métodos de interacción**:
 * - `getInputCode()`: Captura el código ingresado.
 * - `addEventButtonOK(ActionListener event)`: Asocia un evento al botón "OK",
 * facilitando la validación de código.
 */