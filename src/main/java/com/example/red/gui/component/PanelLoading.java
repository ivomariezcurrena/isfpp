package com.example.red.gui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

public class PanelLoading extends javax.swing.JPanel {

        public PanelLoading() {
                initComponents();
                setOpaque(false);
                setFocusCycleRoot(true);
                setVisible(false);
                setFocusable(true);
                addMouseListener(new MouseAdapter() {
                });
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabel1 = new javax.swing.JLabel();

                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/example/red/gui/icon/loading.gif"))); // NOI18N

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 512,
                                                                Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 355,
                                                                Short.MAX_VALUE));
        }// </editor-fold>//GEN-END:initComponents

        @Override
        protected void paintComponent(Graphics grphcs) {
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setColor(new Color(255, 255, 255));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintComponent(grphcs);
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        // End of variables declaration//GEN-END:variables
}
/**
 * La clase PanelLoading representa un panel de carga visual para indicar al
 * usuario
 * que una operación está en progreso, bloqueando la interacción con otros
 * componentes
 * y mostrando una animación de "loading".
 *
 * Funcionalidades principales:
 * - **Superposición semitransparente**: Sobrescribe el método paintComponent
 * para
 * añadir una capa blanca translúcida al panel, utilizando 50% de opacidad.
 * Esto ayuda a crear un efecto de "pantalla de bloqueo" que evita la
 * interacción
 * accidental con otros elementos mientras el panel está activo.
 *
 * - **Animación de carga**: Muestra un GIF de carga en el centro del panel
 * (mediante
 * el JLabel `jLabel1`), lo que brinda una indicación visual de que el sistema
 * está
 * ocupado. Este GIF debe estar ubicado en el directorio especificado.
 *
 * - **Evita interacción del usuario**: El panel se configura para ser enfocable
 * y
 * bloquear la interacción sin realizar ninguna acción con eventos de ratón o
 * teclado.
 * 
 * Uso recomendado para indicar operaciones en curso que necesitan bloquear la
 * interfaz
 * temporalmente.
 */